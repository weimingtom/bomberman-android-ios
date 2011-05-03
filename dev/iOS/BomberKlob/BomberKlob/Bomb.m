//
//  Bomb.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Bomb.h"
#import "AnimationSequence.h"
#import "RessourceManager.h"
#import "Position.h"


@implementation Bomb
@synthesize power, explode, type;

- (id) init{
	self = [super init];
	if (self) {
		currentFrame = 0;
		power = 3;
		waitDelay = 5;
		delay = 0;
		explode = NO;
		type = @"normal";
	}
	return self;
}

- (id) initWithImageName:(NSString *)anImageName position:(Position *)aPosition{
	self = [super init];
	if (self) {
		currentFrame = 0;
		power = 3;
		waitDelay = 5;
		delay = 0;
		explode = NO;
		position = aPosition;
		imageName = anImageName;
	}
	return self;
}

- (void)dealloc {
    [type release];
    [super dealloc];
}


- (void) update{
	
	if (!destroyable) {
		if ([((AnimationSequence *)[animations objectForKey:imageName]).sequences count] > 2) {
			if (delay == ((AnimationSequence *)[animations objectForKey:imageName]).delayNextFrame) {
				delay = 0;
				if (currentFrame == [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]-1 ) {
					currentFrame = 0;
					destroyable= YES;
				}
				else
					currentFrame++;
			}
			else{
				delay++;
			}
		}
	}
	else {
        
		if ([((AnimationSequence *)[destroyAnimations objectForKey:imageName]).sequences count] > 2) {
			if (delay == 10) {
				delay = 0;
				if (currentFrame == [((AnimationSequence *)[destroyAnimations objectForKey:imageName]).sequences count]-1) {
					if (!((AnimationSequence *)[destroyAnimations objectForKey:imageName]).canLoop) {
						animationFinished = YES;
					}
					else {
						currentFrame = 0;
					}
				}
			}
			else{
				delay++;
			}
		}
	}

}

- (BOOL) hasAnimationFinished{
	return destroyable;
}

- (void) destroyable{
	destroyable = YES;
	currentFrame = 0;
}

- (Bomb *)copy{
	Bomb * bombCopy = [[Bomb alloc] init];
	bombCopy.ressource = ressource;
    bombCopy.imageName = [[NSString alloc] initWithString:imageName];
	bombCopy.hit = hit;
	bombCopy.level = level;
	bombCopy.fireWall = fireWall;
	bombCopy.damage = damage;
	bombCopy.position = [[Position alloc] initWithPosition:position];
	
	bombCopy.animations = [[NSMutableDictionary alloc] initWithDictionary:animations];
	bombCopy.destroyAnimations = [[NSMutableDictionary alloc] initWithDictionary:destroyAnimations];
	bombCopy.idle = idle;
	
	bombCopy.currentAnimation = [[NSString alloc] initWithString:currentAnimation];
	bombCopy.currentFrame = currentFrame;
	bombCopy.waitDelay = waitDelay;
	bombCopy.delay = delay;
	
	bombCopy.power = power;
	bombCopy.type = [[NSMutableString alloc] initWithString:type];
	bombCopy.explode = explode;
	
	return bombCopy;
}

@end
