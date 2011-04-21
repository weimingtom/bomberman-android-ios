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


@interface Player : Destructible {
	NSMutableArray * bombsPlanted;
	NSMutableArray * bombsTypes;
	NSMutableString * color;
	NSInteger lifeNumber;
	NSInteger powerExplosion;
	NSInteger timeExplosion;
	NSInteger shield;
	NSInteger speed;
	NSInteger bombNumber;

}

@property (nonatomic) NSInteger speed;
@property (nonatomic) NSInteger lifeNumber;
@property (nonatomic, retain) NSMutableArray * bombsPlanted;

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

- (void) plantingBomb;
- (void) hurt;
- (void) draw:(CGContextRef)context;
- (void) draw:(CGContextRef)context alpha:(CGFloat)alpha;


@end
