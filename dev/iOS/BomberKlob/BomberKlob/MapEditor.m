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


@implementation MapEditor

@synthesize map;


- (id)init {
    self = [super init];
    
    if (self) {
        map = [[Map alloc] init];
    }

    return self;
}


- (void)dealloc {
    [map release];
    [super dealloc];
}


- (void)addBlock:(Object *)block position:(Position *)position {
    [map addBlock:block position:position];
}


- (void)deleteGround:(Object *)ground position:(Position *)position {
    
}


- (void)deleteBlockAtPosition:(Position *)position {
    [map deleteBlockAtPosition:position];
}

@end
