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
#import "Undestructible.h"
#import "DataBase.h"
#import "DBUser.h"
#import "DBMap.h"
#import "Object.h"

@implementation Map

@synthesize name, width, height, grounds, blocks, players;


- (id)init {
    self = [super init];
    
    if (self) {
        [self initBasicMap];
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
    NSMutableArray *groundsTmp;
    NSMutableArray *blocksTmp;
    Object *groundTmp;
    Object *blockTmp;
    Position *positionTmp;
    
    width = WIDTH;
    height = HEIGHT;
    
    grounds = [[NSMutableArray alloc] initWithCapacity:height];
    blocks = [[NSMutableArray alloc] initWithCapacity:height];
    players = [[NSMutableArray alloc] init];
	
	RessourceManager * resource = [RessourceManager sharedRessource];
    
    for (int i = 0; i < width; i++){
        groundsTmp = [[NSMutableArray alloc] initWithCapacity:width];
        blocksTmp = [[NSMutableArray alloc] initWithCapacity:width];
        [grounds addObject:groundsTmp];
        [blocks addObject:blocksTmp];
        
        for (int j = 0; j < height; j++) {
            positionTmp = [[Position alloc] initWithX:i*resource.tileSize y:j*resource.tileSize];
			NSMutableDictionary * animationsTmp = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsInanimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"grass2", nil]]];
			
            groundTmp = [[Object alloc] initWithImageName:@"grass2" position:positionTmp animations:animationsTmp];
            [[grounds objectAtIndex:i] addObject: groundTmp];
            
            if (i == 0 || j == 0 || i == (width - 1) || j == (height - 1)) {
				animationsTmp = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsInanimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"bloc", nil]]];
                blockTmp = [[Object alloc] initWithImageName:@"bloc" position:positionTmp animations:animationsTmp];
                [[blocks objectAtIndex:i] addObject: blockTmp];
                
                [blockTmp release];
            }
            else {
                [[blocks objectAtIndex:i] addObject:@"empty"];
            }
            
            [groundTmp release];
            [positionTmp release];
        }
    }
	for (int i=0; i < 4; i++) {
		[players addObject:[[Position alloc] initWithX:2 y:2+i]];
	}
    
    [groundsTmp release];
    [blocksTmp release];
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
    
    NSString *mapPath = [[NSString alloc] initWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], name];
    NSKeyedArchiver *map;
    BOOL result;
    
    map = [[NSKeyedArchiver alloc] initForWritingWithMutableData:data];
    [map encodeObject:self forKey:@"map"];
    [map finishEncoding];
    result = [data writeToFile:mapPath atomically:YES];
    [self makePreviewWithView];
    
    DBMap *dbMap = [[DBMap alloc] initWithName:name owner:owner.identifier official:0];
    [[DataBase sharedDataBase] createOrUpdateMap:dbMap];
    
    [map release];
    [mapPath release];
    [dbMap release];
}


- (void)load:(NSString*)mapName {
    Map *myMap;
    NSData *data;
    NSKeyedUnarchiver *unarchiver;
    NSString *mapPath = [[NSString alloc] initWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], mapName];
    
    data = [[NSData alloc] initWithContentsOfFile:mapPath];
    
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
    }
    else {
        self.name = mapName;
        [self initBasicMap];
    }
    
    [mapPath release];
    [data release];
}


- (void)makePreviewWithView {
    CGSize size = CGSizeMake([RessourceManager sharedRessource].tileSize * height, [RessourceManager sharedRessource].tileSize * width);
    
    UIGraphicsBeginImageContext(CGSizeMake(size.height, size.width));
    
    [self draw:UIGraphicsGetCurrentContext()];
    [self drawPlayers:UIGraphicsGetCurrentContext()];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    
    NSData *img = UIImagePNGRepresentation(image);
    [img writeToFile:[NSString stringWithFormat:@"%@/Maps/%@.png",[[NSBundle mainBundle] bundlePath], name] atomically:YES];
    
    UIGraphicsEndImageContext();
}


- (void)addGround:(Object *)ground position:(Position *)position {
	
}


- (void)addBlock:(Object *)block position:(Position *)position {
    
	if(position.x < width && position.x >=0 && position.y < height && position.y >= 0)
        [[blocks objectAtIndex:position.x] replaceObjectAtIndex:position.y withObject:block];
}


- (void)addPlayer:(Position *)position {
    
    if(position.x < width && position.x >=0 && position.y < height && position.y >= 0) {
        [players addObject:position];
    }
}


- (void)deleteBlockAtPosition:(Position *)position {
	[[blocks objectAtIndex:position.x] replaceObjectAtIndex:position.y withObject:@"empty"];
}


- (void)destroyBlock:(Animated *)block position:(Position *)position {
	
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
    
    [colorsPlayers release];
}


- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha {
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            [((Object *) [[grounds objectAtIndex:i] objectAtIndex:j]) draw:context];
            
            if (![[[blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"]) {
                [((Object *) [[blocks objectAtIndex:i] objectAtIndex:j]) draw:context alpha:alpha];
            }
        }
    }
}


- (void)drawPlayers:(CGContextRef)context alpha:(CGFloat)alpha {
    Position *position;
    Player *player;
    NSArray *colorsPlayers = [[NSArray alloc] initWithObjects:@"white", @"blue", @"red", @"black", nil];
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    
    for (int i = 0; i < [players count]; i++) {
        position = [[Position alloc] initWithX:(((Position *) [players objectAtIndex:i]).x * tileSize) y:(((Position *) [players objectAtIndex:i]).y * tileSize)];
        player = [[Player alloc] initWithColor:[colorsPlayers objectAtIndex:i] position:position];
        
        [player draw:context alpha:alpha];
        
        [position release];
        [player release];
    }
    
    [colorsPlayers release];
}


- (void)drawMapAndPlayers:(CGContextRef)context alpha:(CGFloat)alpha {
    [self draw:context alpha:alpha];
    [self drawPlayers:context alpha:alpha];
}


- (void)drawGrid:(CGContextRef)context {
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            CGContextFillRect(context, CGRectMake(i * [RessourceManager sharedRessource].tileSize, 0,2 , 15 * [RessourceManager sharedRessource].tileSize));
            CGContextFillRect(context, CGRectMake(0, j * [RessourceManager sharedRessource].tileSize, 21 * [RessourceManager sharedRessource].tileSize , 2));
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
					if (![((Undestructible *) object) hasAnimationFinished]) {
						[((Undestructible *) object) update]; 
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
