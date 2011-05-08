//
//  MapEditor.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Editor.h"
#import "EditorMap.h"
#import "Position.h"
#import "DBUser.h"
#import "RessourceManager.h"
#import "Objects.h"


@implementation Editor

@synthesize map;


- (id)initWithMapName:(NSString *)mapName {
    self = [super init];
    
    if (self) {
        map = [[EditorMap alloc] initWithMapName:mapName];
    }

    return self;
}


- (void)dealloc {
    [map release];
    [super dealloc];
}


- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha {
    [map drawMapAndPlayers:context alpha:alpha];
}


- (void)addGround:(Objects *)ground position:(Position *)position {
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    Position *p = [[Position alloc] initWithX:(position.x * tileSize) y:(position.y * tileSize)];
    ground.position = p;
    [map addGround:ground position:position];
    [p release];
}


- (void)addBlock:(Objects *)block position:(Position *)position {
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    Position *p = [[Position alloc] initWithX:(position.x * tileSize) y:(position.y * tileSize)];
    block.position = p;
    [map addBlock:block position:position];
    [p release];
}


- (void)addPlayer:(Position *)position color:(NSString *)color {
    [map addPlayer:position color:color];
}


- (void)deleteObjectAtPosition:(Position *)position {
    
    if ([map thereIsBlock:position]) {
        [map deleteBlockAtPosition:position];
    }
    else if ([map thereIsPlayer:position]) {
        [map deletePlayerAtPosition:position];
    }
}


- (void)saveMapWithOwner:(DBUser *)owner {
    [map save:owner];
}

@end
