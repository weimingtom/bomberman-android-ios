//
//  Player.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Position.h"
#import "Destructible.h"

#define INVINCIBILITY_TIME  80
#define INVINCIBILITY_TIME_REFRESH  5

@class Bomb;
@interface Player : Destructible {
	NSMutableDictionary * bombsTypes;
	NSMutableDictionary * png;
	NSString * color;
	NSInteger lifeNumber;
	NSInteger powerExplosion;
	NSInteger timeExplosion;
	NSInteger shield;
	NSInteger speed;
	NSInteger bombNumber;
	NSInteger timeInvincible;
	BOOL istouched;
	BOOL isKilled;
	BOOL bombPosed;
	BOOL isInvincible;
}

@property (nonatomic,retain)NSMutableDictionary * bombsTypes;
@property (nonatomic,retain)NSMutableDictionary * png;
@property (nonatomic,retain) NSString * color;
@property (nonatomic) NSInteger powerExplosion;
@property (nonatomic) NSInteger timeExplosion;
@property (nonatomic) NSInteger shield;
@property (nonatomic) NSInteger bombNumber;
@property (nonatomic) NSInteger speed;
@property (nonatomic) NSInteger lifeNumber;
@property (nonatomic) BOOL bombPosed;
@property (nonatomic) BOOL istouched;
@property (nonatomic) BOOL isKilled;
@property (nonatomic) BOOL isInvincible;



- (id) init;
- (id) initWithColor:(NSString *)colorValue position:(Position *) positionValue;
- (void) live;
- (void) relive;
- (void) die;
- (void) moveTop;
- (void) moveDown;
- (void) moveLeft; 
- (void) moveRight;

- (void) moveLeftTop;
- (void) moveLeftDown;
- (void) moveRightDown;
- (void) moveRightTop;

- (void) stopTop;
- (void) stopDown;
- (void) stopLeft;
- (void) stopRight;

- (void) stopLeftTop;
- (void) stopRightTop;
- (void) stopLeftDown;
- (void) stopRightDown;

- (void) plantingBomb:(Bomb *) aBomb;
- (void) hurt;
- (void) draw:(CGContextRef)context;
- (void) draw:(CGContextRef)context alpha:(CGFloat)alpha;


@end
