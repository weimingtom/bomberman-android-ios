//
//  Map.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#define WIDTH  21
#define HEIGHT 14

@class RessourceManager, Position, Object, Animated, DBUser, DBMap;


@interface Map : NSObject <NSCoding> {
    
	NSString *name;
    NSInteger width;
    NSInteger height;
	NSMutableArray *grounds;
	NSMutableArray *blocks;
    NSMutableArray *players;
}

@property (nonatomic, retain) NSString *name;
@property (nonatomic) NSInteger width;
@property (nonatomic) NSInteger height;
@property (nonatomic, retain) NSMutableArray * grounds;
@property (nonatomic, retain) NSMutableArray * blocks;
@property (nonatomic, retain) NSMutableArray *players;

- (id)init;
- (id)initWithMapName:(NSString *)mapName;
- (void)dealloc;

- (void)initBasicMap;
- (void)saveWithOwner:(DBUser *)owner;
- (void)load:(NSString*)mapName;
- (void)makePreviewWithView;

- (void)addGround:(Object *)ground position:(Position *)position;
- (void)addBlock:(Object *)block position:(Position *)position;
- (void)deleteBlockAtPosition:(Position *)position;
- (void)destroyBlock:(Animated *)block position:(Position *)position;

- (void)draw:(CGContextRef)context;
- (void)drawPlayers:(CGContextRef)context;
- (void)drawCase:(CGContextRef)context;
- (void)draw:(CGContextRef)context:(CGFloat)x: (CGFloat)y;


- (void)makePreviewWithView;


@end
