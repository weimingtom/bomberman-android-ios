//
//  Player.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Player.h"
#import "RessourceManager.h"
#import "AnimationSequence.h"


@implementation Player

- (id) init{
	self = [super init];
	if (self){
		NSMutableString * color = @"blue";
		NSInteger lifeNumber = 0;
		NSInteger powerExplosion = 1;
		NSInteger timeExplosion = 3;
		NSInteger shield = 1;
		NSInteger speed = 1;
		NSInteger bombNumber = 1;
		animations = ressource.bitmapsPlayer;
		position.x = 0;
		position.y = 0;
	}
	return self;
}

- (void) live{
	
}


- (void) relive{
	
}


- (void) die{
	
}


- (void) moveTop{
	NSLog(@"Move Top");
	if (currentAnimation != @"up") {
		currentAnimation = @"up";
		currentFrame = 0;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.y++;
	}
	else {
		currentFrame = 0;
	//	currentFrame++;
		position.y++;

	}
	
}


- (void) moveDown{
		NSLog(@"Move Down");
	if (currentAnimation != @"down") {
		currentAnimation = @"down";
		currentFrame = 0;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.y--;
	}
	else {
		currentFrame = 0;
		//currentFrame++;
		position.y--;

	}
}


- (void) moveLeft{
		NSLog(@"Move Left");
	if (currentAnimation != @"left") {
		currentAnimation = @"left";
		currentFrame = 0;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.x--;
	}
	else {
		currentFrame = 0;
		//currentFrame++;
		position.x--;
	}
}


- (void) moveRight{
		NSLog(@"Move Right");
	if (currentAnimation != @"right") {
		currentAnimation = @"right";
		currentFrame = 0;
		position.x++;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.x++;
	}
	else {
		currentFrame = 0;
		//currentFrame++;
		position.x++;
	}
}


- (void) plantingBomb{
	
}


- (void) hurt{
	
}


- (void) draw:(CGContextRef)context{

	CGImageRef image = [((AnimationSequence *)[animations valueForKey:currentAnimation]).sequences objectAtIndex:currentFrame];
	CGAffineTransform transform = CGAffineTransformIdentity;
	transform = CGAffineTransformMakeTranslation(0.0, ressource.tileHeight);
	transform = CGAffineTransformScale(transform, 1.0, -1.0);
	CGContextConcatCTM(context, transform);
	
	NSLog(@"Position du personnage : X : %d, Y : %d  Current Animation : %@ , currentFrame : %d, image : %@ ",position.x, position.y, currentAnimation, currentFrame, image);
	
	CGContextDrawImage(context, CGRectMake(position.x, position.y, ressource.tileWidth , ressource.tileHeight ), image);
}



@end
