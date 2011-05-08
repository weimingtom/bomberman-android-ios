//
//  MapEditor.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class EditorMap, Position, Objects, DBUser;


@interface Editor : NSObject {
    
    EditorMap *map;
}

@property (nonatomic, retain) EditorMap *map;

- (id)initWithMapName:(NSString *)mapName;

- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha;

- (void)addGround:(Objects *)ground position:(Position *)position;
- (void)addBlock:(Objects *)block position:(Position *)position;
- (void)addPlayer:(Position *)position color:(NSString *)color;
- (void)deleteObjectAtPosition:(Position *)position;
- (void)saveMapWithOwner:(DBUser *)owner;

@end
