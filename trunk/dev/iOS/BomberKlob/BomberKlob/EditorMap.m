#import "EditorMap.h"
#import "Player.h"
#import "RessourceManager.h"
#import "Position.h"
#import "DBMap.h"
#import "Undestructible.h"
#import "DataBase.h"
#import "DBUser.h"


@implementation EditorMap

@synthesize grounds, blocks;


- (id)initWithMapName:(NSString *)mapName {
    self = [super init];
    
    if (self) {  
        self.name = mapName;
        [self load];
    }
    
    return self;
}


- (void)dealloc {
    [grounds release];
    [blocks release];
    [super dealloc];
}


- (void)initBasicMap {
    NSMutableArray *groundsTmp;
    NSMutableArray *blocksTmp;
    Objects *groundTmp;
    Objects *blockTmp;
    Position *positionTmp;
    
    width = WIDTH;
    height = HEIGHT;
    
    grounds = [[NSMutableArray alloc] initWithCapacity:height];
    blocks = [[NSMutableArray alloc] initWithCapacity:height];
    players = [[NSMutableDictionary alloc] init];
	
	RessourceManager * resource = [RessourceManager sharedRessource];
    
    for (int i = 0; i < width; i++){
        groundsTmp = [[NSMutableArray alloc] initWithCapacity:width];
        blocksTmp = [[NSMutableArray alloc] initWithCapacity:width];
        [grounds addObject:groundsTmp];
        [blocks addObject:blocksTmp];
        
        for (int j = 0; j < height; j++) {
            positionTmp = [[Position alloc] initWithX:(i * resource.tileSize) y:(j * resource.tileSize)];
			
			groundTmp = [(Objects *)[resource.bitmapsAnimates objectForKey:@"grass2"] copy];
			groundTmp.position = positionTmp;
            [[grounds objectAtIndex:i] addObject:groundTmp];
            
            if (i == 0 || j == 0 || i == (width - 1) || j == (height - 1)) {
				blockTmp = [(Undestructible *)[resource.bitmapsAnimates objectForKey:@"block1"] copy];
				blockTmp.position = positionTmp;
				[[blocks objectAtIndex:i] addObject:blockTmp];
                [blockTmp release];
            }
            else {
                [[blocks objectAtIndex:i] addObject:@"empty"];
            }
			
            [groundTmp release];
            [positionTmp release];
        }
        
        [groundsTmp release];
        [blocksTmp release];
    }
}


- (void)save:(DBUser *)owner {
    NSMutableData *data = [NSMutableData data];
    
    NSString *mapPath = [[NSString alloc] initWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], name];
    NSKeyedArchiver *map;
    BOOL result;
    
    map = [[NSKeyedArchiver alloc] initForWritingWithMutableData:data];
    [map encodeObject:self forKey:@"map"];
    [map finishEncoding];
    result = [data writeToFile:mapPath atomically:YES];
    [self makePreview];
    
    DBMap *dbMap = [[DBMap alloc] initWithName:name owner:owner.identifier official:0];
    [[DataBase sharedDataBase] createOrUpdateMap:dbMap];
    
    [map release];
    [mapPath release];
    [dbMap release];
}


- (void)load {
    EditorMap *myMap;
    NSData *data;
    NSKeyedUnarchiver *unarchiver;
    NSString *mapPath = [[NSString alloc] initWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], name];
    
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
        [self initBasicMap];
    }
    
    [mapPath release];
    [data release];
}


- (void)makePreview {
    CGSize size = CGSizeMake([RessourceManager sharedRessource].tileSize * height, [RessourceManager sharedRessource].tileSize * width);
    
    UIGraphicsBeginImageContext(CGSizeMake(size.height, size.width));
    
    [self draw:UIGraphicsGetCurrentContext()];
    [self drawPlayers:UIGraphicsGetCurrentContext()];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    
    NSData *img = UIImagePNGRepresentation(image);
    [img writeToFile:[NSString stringWithFormat:@"%@/Maps/%@.png",[[NSBundle mainBundle] bundlePath], name] atomically:YES];
    
    UIGraphicsEndImageContext();
}



#pragma mark - Managing Map

- (void)addGround:(Objects *)ground position:(Position *)position {
	if(position.x < width && position.x >= 0 && position.y < height && position.y >= 0)
        [[grounds objectAtIndex:position.x] replaceObjectAtIndex:position.y withObject:ground];
}


- (void)addBlock:(Objects *)block position:(Position *)position {
	if(position.x < width && position.x >= 0 && position.y < height && position.y >= 0 && [self isEmpty:position]){
		[[blocks objectAtIndex:position.x] replaceObjectAtIndex:position.y withObject:block];
	}
}


- (void)addPlayer:(Position *)position color:(NSString *)color {
    if(position.x < width && position.x >= 0 && position.y < height && position.y >= 0 && [self isEmpty:position]) {
        [players setValue:position forKey:color];
    }
}


- (void)deleteBlockAtPosition:(Position *)position {
	[[blocks objectAtIndex:position.x] replaceObjectAtIndex:position.y withObject:@"empty"];
}


- (void)deletePlayerAtPosition:(Position *)position {
    id playerRemoved = nil;
    
    for (id key in players) {
        if ([[players objectForKey:key] isEqual:position]) {
            playerRemoved = key;
        }
    }
    
    [players removeObjectForKey:playerRemoved];
}

#pragma -



#pragma mark - Drawing Methods

- (void)draw:(CGContextRef)context {
    
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            [((Objects *) [[grounds objectAtIndex:i] objectAtIndex:j]) draw:context];
            
            if (![[[blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"]) {
                [((Objects *) [[blocks objectAtIndex:i] objectAtIndex:j]) draw:context];
            }
        }
    }
}


- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha {
    
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            [((Objects *) [[grounds objectAtIndex:i] objectAtIndex:j]) draw:context];
            
            if (![[[blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"]) {
                [((Objects *) [[blocks objectAtIndex:i] objectAtIndex:j]) draw:context alpha:alpha];
            }
        }
    }
}


- (void)drawPlayers:(CGContextRef)context {
    Position *position;
    Player *player;
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    
    for (id key in players) {
        position = [[Position alloc] initWithX:(((Position *) [players objectForKey:key]).x * tileSize) y:(((Position *) [players objectForKey:key]).y * tileSize)];
        player = [(Player *)[[RessourceManager sharedRessource].bitmapsPlayer objectForKey:key] copy];
        player.position = position;
        
        [player draw:context];
        
        [position release];
        [player release];
    }
}


- (void)drawPlayers:(CGContextRef)context alpha:(CGFloat)alpha {
    Position *position;
    Player *player;
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    
    for (id key in players) {
        position = [[Position alloc] initWithX:(((Position *) [players objectForKey:key]).x * tileSize) y:(((Position *) [players objectForKey:key]).y * tileSize)];
        player = [(Player *)[[RessourceManager sharedRessource].bitmapsPlayer objectForKey:key] copy];
        player.position = position;
        
        [player draw:context alpha:alpha];
        
        [position release];
        [player release];
    }
}


- (void)drawMapAndPlayers:(CGContextRef)context alpha:(CGFloat)alpha {
    [self draw:context alpha:alpha];
    [self drawPlayers:context alpha:alpha];
}

#pragma -



- (BOOL)isEmpty:(Position *)position {
    return ![self thereIsBlock:position] && ![self thereIsPlayer:position];
}


- (BOOL)thereIsBlock:(Position *)position {
    return ![[[blocks objectAtIndex:position.x] objectAtIndex:position.y] isEqual:@"empty"];
}


- (BOOL)thereIsPlayer:(Position *)position {
    
    for (NSString *key in players) {
        if ([[players objectForKey:key] isEqual:position]) {
            return YES;
        }
    }
    
    return NO;
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
