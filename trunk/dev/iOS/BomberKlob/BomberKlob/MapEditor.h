//
//  MapEditor.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Map, Position, Object, DBUser;


@interface MapEditor : NSObject {
    
    Map *map;
}

@property (nonatomic, retain) Map *map;

- (id)initWithMapName:(NSString *)mapName;

- (void)addBlock:(Object *)block position:(Position *)position;
- (void)addPlayer:(Position *)position;
- (void)deleteBlockAtPosition:(Position *)position;
- (void)saveMapWithOwner:(DBUser *)owner;

@end
