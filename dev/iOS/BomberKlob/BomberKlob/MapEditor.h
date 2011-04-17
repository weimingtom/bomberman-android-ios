//
//  MapEditor.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Map, Position, Object;


@interface MapEditor : NSObject {
    
    Map *map;
}

@property (nonatomic, retain) Map *map;

- (id)init;
- (void)dealloc;

- (void)addBlock:(Object *)block position:(Position *)position;
- (void)deleteBlockAtPosition:(Position *)position;

@end
