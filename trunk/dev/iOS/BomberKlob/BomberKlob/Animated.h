//
//  Animated.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Object.h"

@interface Animated : Object {
    NSDictionary * animations;
	NSString * currentAnimation;
	NSInteger currentFrame;
	NSInteger waitDelay;
	NSInteger delay;
}

@property (nonatomic, retain) NSDictionary * animations ;
@property (nonatomic, retain) NSString * currentAnimation;
@property (nonatomic) NSInteger currentFrame;
@property (nonatomic) NSInteger waitDelay;

- (id) init;
- (id)initWithImageName:(NSString *)anImageName position:(Position *)aPosition animations:(NSDictionary *)anAnimations;
- (void) update;
- (BOOL) hasAnimationFinished;

@end
