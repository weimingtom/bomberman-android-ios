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
        Position *position;
        width = WIDTH;
        height = HEIGHT;
        
        name = @"Default";
        
        grounds = [[NSMutableArray alloc] initWithCapacity:height];
        blocks = [[NSMutableArray alloc] initWithCapacity:height];
        players = [[NSMutableArray alloc] init];
        
        for (int i = 0; i < width; i++){
            [grounds addObject:[[NSMutableArray alloc] initWithCapacity:width]];
            [blocks addObject:[[NSMutableArray alloc] initWithCapacity:width]];
            
            for (int j = 0; j < height; j++) {
                [[grounds objectAtIndex:i] addObject: [[Object alloc] initWithImageName:@"grass2" position:[[Position alloc] initWithX:i y:j]]];
                
                if (i == 0 || j == 0 || i == (width - 1) || j == (height - 1)) {
                    [[blocks objectAtIndex:i] addObject: [[Object alloc] initWithImageName:@"bloc" position:[[Position alloc] initWithX:i y:j]]];
                }
                else {
                    [[blocks objectAtIndex:i] addObject:@"empty"];
                }
            }
        }
        
        position = [[Position alloc] initWithX:1 y:0];
        [players addObject:position];
        [position release];
        position = [[Position alloc] initWithX:19 y:0];
        [players addObject:position];
        [position release];
        position = [[Position alloc] initWithX:1 y:11];
        [players addObject:position];
        [position release];
        position = [[Position alloc] initWithX:19 y:11];
        [players addObject:position];
        [position release];
        
        [self save];
    }
    
    return self;
}


- (id)initWithNameMap:(NSString *)nameMap {
    self = [super init];
    
    if (self) {       
        [self load:nameMap];
    }
    
    return self;
}


- (void)dealloc {
    [name release];
    [grounds release];
    [blocks release];
    [players release];
    [super dealloc];
}


- (void)save {
    NSMutableData *data = [NSMutableData data];

    NSString *mapPath = [NSString stringWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], name];
    NSKeyedArchiver *map;
    BOOL result;
    
    map = [[NSKeyedArchiver alloc] initForWritingWithMutableData:data];
    [map encodeObject:self forKey:@"map"];
    [map finishEncoding];
    result = [data writeToFile:mapPath atomically:YES];
    [self makePreviewWithView];
    
    [map release];
}


- (void)load:(NSString*)mapName {
    Map *myMap;
    NSData *data;
    NSKeyedUnarchiver *unarchiver;
    NSString *mapPath = [NSString stringWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], mapName];
    
    data = [NSData dataWithContentsOfFile:mapPath];
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


- (void)makePreviewWithView {
    CGSize size = [[UIScreen mainScreen] bounds].size;
    
    UIGraphicsBeginImageContext(CGSizeMake(size.height, size.width));
    
    [self draw:UIGraphicsGetCurrentContext()];
    [self drawPlayers:UIGraphicsGetCurrentContext()];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    
    NSData *img;
    img = UIImagePNGRepresentation(image);
    [img writeToFile:[NSString stringWithFormat:@"%@/Maps/%@.png",[[NSBundle mainBundle] bundlePath], name] atomically:YES];
    
    UIGraphicsEndImageContext();
}


- (void)addGround:(NSInteger)ground {
	
}


- (void)addBlock:(NSInteger)block {
	
}


- (void)deleteGround:(NSInteger)ground {
	
}


- (void)deleteBlock:(NSInteger)block {
	
}


- (void)destroyBlock:(NSInteger)block {
	
}


- (void)threadDraw:(CGContextRef)context {
//	NSThread * movementThread = [[[NSThread alloc] initWithTarget:self selector:@selector(draw:) object:context]autorelease];[movementThread start]; 
}


- (void)draw:(CGContextRef)context:(CGFloat)x:(CGFloat)y{
	//if (floor(x) != ceil(x)) {
	//	if ((floor(y) != ceil(y)) {
			
			
			[((Object *) [[grounds objectAtIndex:floor(x)] objectAtIndex:floor(y)]) draw:context];
            
            if (![[[blocks objectAtIndex:floor(x)] objectAtIndex:floor(y)] isEqual:@"empty"])
                [((Object *) [[blocks objectAtIndex:floor(x)] objectAtIndex:floor(y)]) draw:context];
			
			
			[((Object *) [[grounds objectAtIndex:floor(x)] objectAtIndex:ceil(y)]) draw:context];
            
            if (![[[blocks objectAtIndex:floor(x)] objectAtIndex:ceil(y)] isEqual:@"empty"])
                [((Object *) [[blocks objectAtIndex:floor(x)] objectAtIndex:ceil(y)]) draw:context];
	
	
	
			
			[((Object *) [[grounds objectAtIndex:ceil(x)] objectAtIndex:floor(y)]) draw:context];
            
            if (![[[blocks objectAtIndex:ceil(x)] objectAtIndex:floor(y)] isEqual:@"empty"])
                [((Object *) [[blocks objectAtIndex:ceil(x)] objectAtIndex:floor(y)]) draw:context];
	
	
			
			[((Object *) [[grounds objectAtIndex:ceil(x)] objectAtIndex:ceil(y)]) draw:context];
            
            if (![[[blocks objectAtIndex:ceil(x)] objectAtIndex:ceil(y)] isEqual:@"empty"])
                [((Object *) [[blocks objectAtIndex:ceil(x)] objectAtIndex:ceil(y)]) draw:context];
	//	}
	//}
}


- (void)draw:(CGContextRef)context {
    NSLog(@"Dessine...");
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            [((Object *) [[grounds objectAtIndex:i] objectAtIndex:j]) draw:context];
            
            if (![[[blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"])
                [((Object *) [[blocks objectAtIndex:i] objectAtIndex:j]) draw:context];
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

#pragma mark -

@end
