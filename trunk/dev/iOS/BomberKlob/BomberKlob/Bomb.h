//
//  Bomb.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Destructible.h"

@class Player;
@interface Bomb : Destructible {
    NSInteger power;
	NSString * type;
	BOOL explode;
	Player * owner;
	NSInteger time;
}

@property (nonatomic) NSInteger power;
@property (nonatomic) NSInteger time;
@property (nonatomic) BOOL explode;
@property (nonatomic,retain) NSString * type;
@property (nonatomic,retain) Player * owner;


- (id) initWithImageName:(NSString *)anImageName position:(Position *)aPosition owner:(Player *) aPlayer;
- (void) draw:(CGContextRef) context;
- (void) update;
- (BOOL) hasAnimationFinished;
- (void) destroyable;

@end
