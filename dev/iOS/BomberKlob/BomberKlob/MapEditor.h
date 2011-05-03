//
//  MapEditor.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Map, Position, Objects, DBUser;


@interface MapEditor : NSObject {
    
    Map *map;
}

@property (nonatomic, retain) Map *map;

- (id)initWithMapName:(NSString *)mapName;

- (void)addGround:(Objects *)ground position:(Position *)position;
- (void)addBlock:(Objects *)block position:(Position *)position;
- (void)addPlayer:(Position *)position color:(NSString *)color;
- (void)deleteObjectAtPosition:(Position *)position;
- (void)saveMapWithOwner:(DBUser *)owner;

@end
