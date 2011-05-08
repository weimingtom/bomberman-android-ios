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
#import "Player.h"
#import <AVFoundation/AVFoundation.h>


@implementation Bomb
@synthesize power, explode, type, owner, time;

- (id) init{
	self = [super init];
	if (self) {
		currentFrame = 0;
		power = 3;
		waitDelay = 5;
		delay = 0;
		explode = NO;
		type = @"normal";
		time = 0;
		[(AnimationSequence *)[animations objectForKey:imageName] playSound];
	}
	return self;
}

- (id) initWithImageName:(NSString *)anImageName position:(Position *)aPosition owner:(Player *) aPlayer{
	self = [super init];
	if (self) {
		currentFrame = 0;
		power = 3;
		waitDelay = 5;
		delay = 0;
		explode = NO;
		position = aPosition;
		imageName = anImageName;
		owner = aPlayer;
	}
	return self;
}

- (void)dealloc {
    [type release];
    [super dealloc];
}


- (void) update{
	if (time < owner.timeExplosion) {
		time++;
	}
	else {
		currentFrame = 0;
		destroyable = YES;
		[(AnimationSequence *)[destroyAnimations objectForKey:imageName] playSound];
	}
	
	if (!destroyable ) {
		time++;
		if ([((AnimationSequence *)[animations objectForKey:imageName]).sequences count] > 2) {
			if (delay == ((AnimationSequence *)[animations objectForKey:imageName]).delayNextFrame) {
				delay = 0;
				if (currentFrame == [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]-1 ) {
					currentFrame = 0;
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
	if (time >= owner.timeExplosion) {
		return true;
	}
	else
		return false;
}

- (void) destroy{
	destroyable = YES;
	currentFrame = 0;
	time = owner.timeExplosion;
	[(AnimationSequence *)[destroyAnimations objectForKey:imageName] playSound];
	AnimationSequence * s = [destroyAnimations objectForKey:imageName];
}

- (id)copyWithZone:(NSZone *)zone {
    Bomb *copy = [super copyWithZone:zone];
    
    NSString *typeCopy = [[NSString alloc] initWithString:type];
    
    copy.power = power;
	copy.type = typeCopy;
	copy.explode = explode;
    
    [typeCopy release];
    
    return copy;
}

@end
