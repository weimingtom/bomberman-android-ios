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

- (void) draw:(CGContextRef) context{
		AnimationSequence * a = ((AnimationSequence *)[animations valueForKey:imageName]);
		UIImage * image = [((AnimationSequence *)[animations valueForKey:imageName]).sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize , ressource.tileSize)];
}

- (void) update{
	
	if (delay == waitDelay) {
		delay = 0;
		if (currentFrame >= 3) {
			currentFrame = 0;
			explode = YES;
		}
		else
			currentFrame++;
	}
	else{
		delay++;
	}
}

- (BOOL) hasAnimationFinished{
	if (explode == YES)
		return true;
	else
		return false;
}

- (void) destroy{
	explode = YES;
}

- (Bomb *)copy{
	Bomb * bombCopy = [[Bomb alloc] init];
	bombCopy.ressource = ressource;
    bombCopy.imageName = [[NSString alloc] initWithString:imageName];
	bombCopy.hit = hit;
	bombCopy.level = level;
	bombCopy.fireWall = fireWall;
	bombCopy.damages = damages;
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
