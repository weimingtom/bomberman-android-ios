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

@implementation Map

@synthesize name;
@synthesize width;
@synthesize height;
@synthesize grounds;
@synthesize blocks;


- (id) init {
    self = [super init];
    
    if (self) {
        width = WIDTH;
        height = HEIGHT;
        
        name = @"InitMap";
        
        grounds = [[NSMutableArray alloc] initWithCapacity:height];
        blocks = [[NSMutableArray alloc] initWithCapacity:height];
        
        for (int i = 0; i < width; i++){
            [grounds addObject:[[NSMutableArray alloc] initWithCapacity:width]];
            [blocks addObject:[[NSMutableArray alloc] initWithCapacity:width]];
            
            for (int j = 0; j < height; j++) {
                [[grounds objectAtIndex:i] addObject: [[Object alloc] initWithImageName:@"grass2" position:[[Position alloc] initWithXAndY:i :j]]];
                
                if (i == 4 && j != 5 && j != 10) {
                    [[blocks objectAtIndex:i] addObject: [[Object alloc] initWithImageName:@"stone2" position:[[Position alloc] initWithXAndY:i :j]]];
                }
                else {
                    [[blocks objectAtIndex:i] addObject:@"empty"];
                }
            }
        }
    }
    
    [self save];
    
    return self;
}


- (id)initWithPath {
    self = [super init];
    
    if (self) {
        NSLog(@"Loading map...");
        [self load:@""];
    }
    
    return self;
}


- (void) save{
//	NSString *path = [NSString stringWithFormat:@"%@.klob", name];
//    NSLog(@"Path: %@", path);
//    NSMutableDictionary *rootObject = [NSMutableDictionary dictionary];
//    
//    [rootObject setValue:self forKey:@"map"];
//    [NSKeyedArchiver archiveRootObject:rootObject toFile:path];
//    
//    NSData* artistData = [NSKeyedArchiver archivedDataWithRootObject:artistCollection];
//    [artistData writeToFile: @"/Users/benjamintardieu/Desktop/test.klob" atomically:YES];
    
    

    NSMutableData *data;
//    NSString *archivePath = [NSTemporaryDirectory() stringByAppendingPathComponent:@"Map.archive"];
    NSString *archivePath = @"/Users/benjamintardieu/Desktop/Map.archive";
    NSKeyedArchiver *archiver;
    BOOL result;
    
    data = [NSMutableData data];
    archiver = [[NSKeyedArchiver alloc] initForWritingWithMutableData:data];
    // Customize archiver here
    [archiver encodeObject:self forKey:@"map"];
    [archiver finishEncoding];
    result = [data writeToFile:archivePath atomically:YES];
    [archiver release];
    
    
}


- (void) load:(NSString*)mapName {
//    NSString *path = [NSString stringWithFormat:@"%@.klob", mapName];
//    NSDictionary *rootObject;
//    
//    rootObject = [NSKeyedUnarchiver unarchiveObjectWithFile:path];    
//    self = [rootObject valueForKey:@"map"];
    
    Map *myMap;
    NSData *data;
    NSKeyedUnarchiver *unarchiver;
//    NSString *archivePath = [NSTemporaryDirectory() stringByAppendingPathComponent:@"Map.archive"];
    NSString *archivePath = @"/Users/benjamintardieu/Desktop/Map.archive";
    
    data = [NSData dataWithContentsOfFile:archivePath];
    unarchiver = [[NSKeyedUnarchiver alloc] initForReadingWithData:data];
    // Customize unarchiver here
    myMap = [unarchiver decodeObjectForKey:@"map"];
    [unarchiver finishDecoding];
    [unarchiver release];
    
    self.name = myMap.name;
    self.width = myMap.width;
    self.height = myMap.height;
    self.grounds = myMap.grounds;
    self.blocks = myMap.blocks;
//    NSLog(@"myMap: %@", myMap.name);
//    NSLog(@"%d", myMap.width);
//    NSLog(@"%d", myMap.height);
//    NSLog(@"%@", myMap.grounds);
//    NSLog(@"%@", myMap.blocks);
}


- (void) addGround:(NSInteger) ground {
	
}


- (void) addBlock:(NSInteger) block {
	
}


- (void) deleteGround: (NSInteger) ground {
	
}


- (void) deleteBlock: (NSInteger)block {
	
}


- (void) destroyBlock: (NSInteger)block {
	
}


- (void)draw:(CGContextRef)context {
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            [((Object *) [[grounds objectAtIndex:i] objectAtIndex:j]) draw:context];
            
            if (![[[blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"])
                [((Object *) [[blocks objectAtIndex:i] objectAtIndex:j]) draw:context];
        }
    }
}


- (id)initWithCoder:(NSCoder *)aDecoder {
    self = [super init];
    
    if (self) {        
        self.name  = [aDecoder decodeObjectForKey:@"name"];
        self.width = [aDecoder decodeIntegerForKey:@"width"];
        self.height = [aDecoder decodeIntegerForKey:@"height"];
        self.grounds = [aDecoder decodeObjectForKey:@"grounds"];
        self.blocks = [aDecoder decodeObjectForKey:@"blocks"];
    }
    
    return self;
}


- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:name forKey:@"name"];
    [aCoder encodeInteger:width forKey:@"width"];
    [aCoder encodeInteger:height forKey:@"height"];
    [aCoder encodeObject:grounds forKey:@"grounds"];
    [aCoder encodeObject:blocks forKey:@"blocks"];
}
@end
