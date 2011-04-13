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
@synthesize speed;

- (id) init{
	self = [super init];
	if (self){
		color = @"white";
		lifeNumber = 0;
		powerExplosion = 1;
		timeExplosion = 3;
		shield = 1;
		speed = 3;
		bombNumber = 1;
		ressource = [RessourceManager sharedRessource];
		animations = [ressource.bitmapsPlayer objectForKey:color];
		position.x = 0;
		position.y = 0;
	}
	return self;
}

- (id) initWithColor:(NSString *)colorValue position:(Position *) positionValue{
	self = [super init];
	if (self){
		color = colorValue;
		lifeNumber = 0;
		powerExplosion = 1;
		timeExplosion = 3;
		shield = 1;
		speed = 3;
		bombNumber = 1;
		ressource = [RessourceManager sharedRessource];
		animations = [ressource.bitmapsPlayer objectForKey:color];
		position.x = positionValue.x;
		position.y = positionValue.y;
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
	if (currentAnimation != @"up") {
		currentAnimation = @"up";
		currentFrame = 0;
		position.y -= speed;

	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.y -= speed;
	}
	else {
		currentFrame = 0;
		position.y-= speed;

	}
	
}


- (void) moveDown{
	if (currentAnimation != @"down") {
		currentAnimation = @"down";
		currentFrame = 0;
		position.y+=speed;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.y+=speed;
		NSLog(@"moveDown + position %@ and speed : %d",position, speed);
	}
	else {
		currentFrame = 0;
		position.y+= speed;

	}
}


- (void) moveLeft{
	if (currentAnimation != @"left") {
		currentAnimation = @"left";
		currentFrame = 0;
		position.x-=speed;

	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.x-=speed;
	}
	else {
		currentFrame = 0;
		position.x-=speed;
	}
}


- (void) moveRight{
	if (currentAnimation != @"right") {
		currentAnimation = @"right";
		currentFrame = 0;
		position.x+=speed;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.x+=speed;
	}
	else {
		currentFrame = 0;
		position.x+=speed;
	}
}

- (void) moveLeftTop{
	if (currentAnimation != @"upLeft") {
		currentAnimation = @"upLeft";
		currentFrame = 0;
		position.x-=speed;
		position.y-=speed;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.x-=speed;
		position.y-=speed;
	}
	else {
		currentFrame = 0;
		position.x-=speed;
		position.y-=speed;
	}
}

- (void) moveLeftDown{
	if (currentAnimation != @"downLeft") {
		currentAnimation = @"downLeft";
		currentFrame = 0;
		position.x-=speed;
		position.y+=speed;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.x-=speed;
		position.y+=speed;
	}
	else {
		currentFrame = 0;
		position.x-=speed;
		position.y+=speed;
	}
}

- (void) moveRightDown{
	if (currentAnimation != @"downRight") {
		currentAnimation = @"downRight";
		currentFrame = 0;
		position.x+=speed;
		position.y+=speed;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.x+=speed;
		position.y+=speed;
	}
	else {
		currentFrame = 0;
		position.x+=speed;
		position.y+=speed;
	}
}

- (void) moveRightTop{
	if (currentAnimation != @"upRight") {
		currentAnimation = @"upRight";
		currentFrame = 0;
		position.x+=speed;
		position.y-=speed;
	}
	else if (currentFrame < 3) {
		currentFrame++;
		position.x+=speed;
		position.y-=speed;
	}
	else {
		currentFrame = 0;
		position.x+=speed;
		position.y-=speed;
	}
}


- (void) plantingBomb{
	
}


- (void) hurt{
	
}

- (void) threadDraw:(CGContextRef) context{
	NSThread * movementThread = [[[NSThread alloc] initWithTarget:self selector:@selector(draw:) object:context]autorelease];[movementThread start]; 
}


- (void) draw:(CGContextRef)context{
	//NSLog(@"Position : %@", position);
		UIImage * image = [((AnimationSequence *)[animations valueForKey:currentAnimation]).sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize , ressource.tileSize*2)];
	

}



@end
