//
//  Player.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Position.h"


@interface Player : NSObject {
	Position * position;
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

- (void) live;
- (void) relive;
- (void) die;
- (void) moveTop;
- (void) moveDown;
- (void) moveLeft; 
- (void) moveRight;
- (void) plantingBomb;
- (void) hurt;
- (void) draw;

@end
