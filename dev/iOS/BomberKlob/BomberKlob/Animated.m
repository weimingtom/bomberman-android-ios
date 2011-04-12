//
//  Animated.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Animated.h"
#import "AnimationSequence.h"
#import "Object.h"

@implementation Animated


@synthesize waitDelay, animations, currentAnimation, currentFrame;

- (id) init{
	self = [super init];
	if (self){
		currentAnimation = @"idle";
	}
	return self;
}

@end
