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

@class RessourceManager;


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
- (id)initWithNameMap:(NSString *)nameMap;
- (void)dealloc;

- (void)save;
- (void)load:(NSString*)mapName;

- (void)addGround:(NSInteger)ground;
- (void)addBlock:(NSInteger)block;
- (void)deleteGround:(NSInteger)ground;
- (void)deleteBlock:(NSInteger)block;
- (void)destroyBlock:(NSInteger)block;

- (void)draw:(CGContextRef)context;
- (void)drawPlayers:(CGContextRef)context;
- (void)drawCase:(CGContextRef)context;
- (void)draw:(CGContextRef)context:(CGFloat)x: (CGFloat)y;


- (void)makePreviewWithView;


@end
