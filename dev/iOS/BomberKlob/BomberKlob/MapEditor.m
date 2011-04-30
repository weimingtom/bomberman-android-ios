//
//  MapEditor.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "MapEditor.h"
#import "Map.h"
#import "Position.h"
#import "DBUser.h"
#import "RessourceManager.h"
#import "Objects.h"


@implementation MapEditor

@synthesize map;


- (id)initWithMapName:(NSString *)mapName {
    self = [super init];
    
    if (self) {
        map = [[Map alloc] initWithMapName:mapName];
    }

    return self;
}


- (void)dealloc {
    [map release];
    [super dealloc];
}


- (void)addBlock:(Objects *)block position:(Position *)position {
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    Position *p = [[Position alloc] initWithX:(position.x * tileSize) y:(position.y * tileSize)];
    block.position = p;
    [map addBlock:block position:position];
    [p release];
}


- (void)addPlayer:(Position *)position {
    [map addPlayer:position];
}


- (void)deleteGround:(Objects *)ground position:(Position *)position {
    
}


- (void)deleteBlockAtPosition:(Position *)position {
    [map deleteBlockAtPosition:position];
}


- (void)saveMapWithOwner:(DBUser *)owner {
    [map saveWithOwner:owner];
}

@end
