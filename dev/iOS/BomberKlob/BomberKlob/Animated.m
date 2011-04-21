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
#import "Position.h"
#import "RessourceManager.h"

@implementation Animated


@synthesize waitDelay, animations, currentAnimation, currentFrame,delay;

- (id) init{
	self = [super init];
	if (self){
		self.currentAnimation = @"idle";
		self.delay = 0;
	}
	return self;
}

- (id)initWithImageName:(NSString *)anImageName position:(Position *)aPosition animations:(NSDictionary *)anAnimations {
    self = [super initWithImageName:anImageName position:aPosition];
    if (self) {
        self.animations = anAnimations;
		self.currentFrame = 0;
    }
    return self;
}

- (void)draw:(CGContextRef)context{
	if (currentFrame < [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]){
		UIImage * image = [((AnimationSequence*)[animations objectForKey:imageName]).sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize , ressource.tileSize)];
	}

}

- (void) update{
	if (delay == waitDelay) {
		delay = 0;
		if (currentFrame == [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]-1) {
			currentFrame = -1;
		}
		else
			currentFrame++;
	}
	else{
		delay++;
	}

}

- (BOOL) hasAnimationFinished{

	if (currentFrame < 0) {
		return true;
	}
	else
		return false;
}

@end
