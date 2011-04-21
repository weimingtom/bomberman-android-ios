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
@synthesize power;

- (id) initWithImageName:(NSString *)anImageName position:(Position *)aPosition{
	self = [super initWithImageName:anImageName position:aPosition];
	if (self) {
		currentFrame = 0;
		power = 3;
		waitDelay = 1;
		delay = 0;
		animations = ressource.bitmapsBombs;
		explode = NO;
		NSArray * explosionType = [[NSArray alloc] initWithObjects:@"firedown",@"fireup",@"fireleft",@"fireright",@"firevertical",@"firehorizontal",@"firecenter", nil];
		animationExplosed = [[NSMutableDictionary alloc] initWithDictionary:[ressource.bitmapsAnimates dictionaryWithValuesForKeys:explosionType]];
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
		if (currentFrame >= 4) {
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

@end
