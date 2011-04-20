//
//  Map.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Map.h"
#import "RessourceManager.h"
#import "Object.h"
#import "Position.h"
#import "Player.h"
#import "Inanimated.h"
#import "Undestructible.h"
#import "Animated.h"
#import "DataBase.h"
#import "DBUser.h"
#import "DBMap.h"

@implementation Map

@synthesize name;
@synthesize width;
@synthesize height;
@synthesize grounds;
@synthesize blocks;
@synthesize players;


- (id)init {
    self = [super init];
    
    if (self) {
//        Position *position;
        [self initBasicMap];
//        
//        position = [[Position alloc] initWithX:5 y:2];
//        [players addObject:position];
//        [position release];
//        position = [[Position alloc] initWithX:15 y:2];
//        [players addObject:position];
//        [position release];
//        position = [[Position alloc] initWithX:15 y:9];
//        [players addObject:position];
//        [position release];
//        position = [[Position alloc] initWithX:5 y:9];
//        [players addObject:position];
//        [position release];
//        
//        [self save];
    }
    
    return self;
}


- (id)initWithMapName:(NSString *)mapName {
    self = [super init];
    
    if (self) {       
        [self load:mapName];
    }
    
    return self;
}


- (void)initBasicMap {
    width = WIDTH;
    height = HEIGHT;
    
    grounds = [[NSMutableArray alloc] initWithCapacity:height];
    blocks = [[NSMutableArray alloc] initWithCapacity:height];
    players = [[NSMutableArray alloc] init];
    
    for (int i = 0; i < width; i++){
        [grounds addObject:[[NSMutableArray alloc] initWithCapacity:width]];
        [blocks addObject:[[NSMutableArray alloc] initWithCapacity:width]];
        
        for (int j = 0; j < height; j++) {
            [[grounds objectAtIndex:i] addObject: [[Inanimated alloc] initWithImageName:@"grass2" position:[[Position alloc] initWithX:i y:j]]];
            
            if (i == 0 || j == 0 || i == (width - 1) || j == (height - 1)) {
                [[blocks objectAtIndex:i] addObject: [[Undestructible alloc] initWithImageName:@"bloc" position:[[Position alloc] initWithX:i y:j]]];
            }
            else {
                [[blocks objectAtIndex:i] addObject:@"empty"];
            }
        }
    }
}


- (void)dealloc {
    [name release];
    [grounds release];
    [blocks release];
    [players release];
    [super dealloc];
}


- (void)saveWithOwner:(DBUser *)owner {
    NSMutableData *data = [NSMutableData data];
    
    NSString *mapPath = [NSString stringWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], name];
    NSKeyedArchiver *map;
    BOOL result;
    
    map = [[NSKeyedArchiver alloc] initForWritingWithMutableData:data];
    [map encodeObject:self forKey:@"map"];
    [map finishEncoding];
    result = [data writeToFile:mapPath atomically:YES];
    [self makePreviewWithView];
    
    DBMap *dbMap = [[DBMap alloc] initWithName:name owner:owner.identifier official:0];
    [[DataBase instance] createOrUpdateMap:dbMap];
    
    [map release];
}


- (void)load:(NSString*)mapName {
    Map *myMap;
    NSData *data;
    NSKeyedUnarchiver *unarchiver;
    NSString *mapPath = [NSString stringWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], mapName];
    
    data = [NSData dataWithContentsOfFile:mapPath];
    
    if (data != nil) {
        unarchiver = [[NSKeyedUnarchiver alloc] initForReadingWithData:data];
        
        myMap = [unarchiver decodeObjectForKey:@"map"];
        [unarchiver finishDecoding];
        [unarchiver release];
        
        self.name = myMap.name;
        self.width = myMap.width;
        self.height = myMap.height;
        self.grounds = myMap.grounds;
        self.blocks = myMap.blocks;
        self.players = myMap.players;
        
        [myMap release];
    }
    else {
        self.name = mapName;
        [self initBasicMap];
    }
}


- (void)makePreviewWithView {
    CGSize size = CGSizeMake([RessourceManager sharedRessource].tileSize * height, [RessourceManager sharedRessource].tileSize * width);
    
    UIGraphicsBeginImageContext(CGSizeMake(size.height, size.width));
    
    [self draw:UIGraphicsGetCurrentContext()];
    [self drawPlayers:UIGraphicsGetCurrentContext()];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    
    NSData *img;
    img = UIImagePNGRepresentation(image);
    [img writeToFile:[NSString stringWithFormat:@"%@/Maps/%@.png",[[NSBundle mainBundle] bundlePath], name] atomically:YES];
    
    UIGraphicsEndImageContext();
}


- (void)addGround:(Object *)ground position:(Position *)position {
	
}


- (void)addBlock:(Object *)block position:(Position *)position {
	if(position.x < width && position.x >=0 && position.y < height && position.y >= 0)
    [[blocks objectAtIndex:position.x] replaceObjectAtIndex:position.y withObject:block];
}


- (void)deleteBlockAtPosition:(Position *)position {
	[[blocks objectAtIndex:position.x] replaceObjectAtIndex:position.y withObject:@"empty"];
}


- (void)destroyBlock:(Animated *)block position:(Position *)position {
	
}


- (void) threadDraw:(CGContextRef) context{
	[self performSelectorOnMainThread:@selector(draw:) withObject:context waitUntilDone:YES];
	NSThread * threadDraw = [[[NSThread alloc] initWithTarget:self selector:@selector(draw:) object:context]autorelease];
	[threadDraw start];
}


- (void)draw:(CGContextRef)context {
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            [((Object *) [[grounds objectAtIndex:i] objectAtIndex:j]) draw:context];

            if (![[[blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"]) {
                [((Object *) [[blocks objectAtIndex:i] objectAtIndex:j]) draw:context];
            }
        }
    }
}


- (void) drawPlayers:(CGContextRef)context {
    Position *position;
    Player *player;
    NSArray *colorsPlayers = [[NSArray alloc] initWithObjects:@"white", @"blue", @"red", @"black", nil];
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    
    for (int i = 0; i < [players count]; i++) {
        position = [[Position alloc] initWithX:(((Position *) [players objectAtIndex:i]).x * tileSize) y:(((Position *) [players objectAtIndex:i]).y * tileSize)];
        player = [[Player alloc] initWithColor:[colorsPlayers objectAtIndex:i] position:position];
        
        [player draw:context];
        
        [position release];
        [player release];
    }
}


- (void)drawCase:(CGContextRef)context {
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            CGContextFillRect(context, CGRectMake(i*[RessourceManager sharedRessource].tileSize, 0,2 , 15*[RessourceManager sharedRessource].tileSize));
            CGContextFillRect(context, CGRectMake(0, j*[RessourceManager sharedRessource].tileSize,21*[RessourceManager sharedRessource].tileSize , 2));
        }
    }
    
}


#pragma mark - NSCoding

- (id)initWithCoder:(NSCoder *)aDecoder {
    self = [super init];
    
    if (self) {        
        self.name  = [aDecoder decodeObjectForKey:@"name"];
        self.width = [aDecoder decodeIntegerForKey:@"width"];
        self.height = [aDecoder decodeIntegerForKey:@"height"];
        self.grounds = [aDecoder decodeObjectForKey:@"grounds"];
        self.blocks = [aDecoder decodeObjectForKey:@"blocks"];
        self.players = [aDecoder decodeObjectForKey:@"players"];
    }
    
    return self;
}


- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:name forKey:@"name"];
    [aCoder encodeInteger:width forKey:@"width"];
    [aCoder encodeInteger:height forKey:@"height"];
    [aCoder encodeObject:grounds forKey:@"grounds"];
    [aCoder encodeObject:blocks forKey:@"blocks"];
    [aCoder encodeObject:players forKey:@"players"];
}

- (void) update{
	for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {	
			Object * object = [[blocks objectAtIndex:i] objectAtIndex:j];
            if (![object isEqual:@"empty"]) {
				if ([[[object class] description] isEqualToString:@"Undestructible"]) {
					if (![((Animated *) object) hasAnimationFinished]) {
						[((Animated *) object) update]; 
					}
					else {
						[[blocks objectAtIndex:i] replaceObjectAtIndex:j withObject:@"empty"];
					}
					
				}
            }
        }
    }
}

#pragma mark -

@end
