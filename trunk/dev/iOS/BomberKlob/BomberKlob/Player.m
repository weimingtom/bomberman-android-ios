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
#import "Bomb.h"


@implementation Player
@synthesize speed, lifeNumber, bombNumber,bombsTypes,powerExplosion,shield,timeExplosion, color, bombPosed;

- (id) init{
	self = [super init];
	if (self){
		color = @"white";
		lifeNumber = 0;
		powerExplosion = 1;
		timeExplosion = 10;
		shield = 1;
		speed = 1;
		bombNumber = 1;
		position.x = 0;
		position.y = 0;
		currentAnimation = @"idle";
		bombsTypes = [[NSMutableArray alloc] init];

	}
	return self;
}

- (id) initWithColor:(NSString *)colorValue position:(Position *) positionValue{
	self = [super init];
	if (self){
		color = colorValue;
		lifeNumber = 0;
		powerExplosion = 1;
		shield = 1;
		speed = 1;
		bombNumber = 1;
		ressource = [RessourceManager sharedRessource];
		position.x = positionValue.x;
		position.y = positionValue.y;
		waitDelay = 4;
		currentAnimation =@"idle";
	}
	return self;
}


- (void)dealloc {
    [bombsTypes release];
    [color release];
    [super dealloc];
}


- (void) live{
	
}


- (void) relive{
	
}


- (void) die{
	
}


- (void) moveTop{
	if (!istouched) {
		if (currentAnimation != @"up") {
			currentAnimation = @"up";
			currentFrame = 0;
			position.y -= speed;
			
		}
		else if (currentFrame < 3) {
			position.y -= speed;
		}
		else {
			position.y-= speed;
		}
	}
}


- (void) moveDown{
	if (!istouched) {
		if (currentAnimation != @"down") {
			currentAnimation = @"down";
			currentFrame = 0;
			position.y+=speed;
		}
		else if (currentFrame < 3) {
			position.y+=speed;
		}
		else {
			position.y+= speed;
		}
	}
}


- (void) moveLeft{
	if (!istouched) {
		if (currentAnimation != @"left") {
			currentAnimation = @"left";
			currentFrame = 0;
			position.x-=speed;
			
		}
		else if (currentFrame < 3) {
			position.x-=speed;
		}
		else {
			position.x-=speed;
		}
	}
}


- (void) moveRight{
	if (!istouched) {
		if (currentAnimation != @"right") {
			currentAnimation = @"right";
			currentFrame = 0;
			position.x+=speed;
		}
		else if (currentFrame < 3) {
			position.x+=speed;
		}
		else {
			position.x+=speed;
		}
	}
}

- (void) moveLeftTop{
	if (!istouched) {
		if (currentAnimation != @"up_left") {
			currentAnimation = @"up_left";
			currentFrame = 0;
			position.x-=speed;
			position.y-=speed;
		}
		else if (currentFrame < 3) {
			position.x-=speed;
			position.y-=speed;
		}
		else {
			position.x-=speed;
			position.y-=speed;
		}
	}
}

- (void) moveLeftDown{
	if (!istouched) {
		if (currentAnimation != @"down_left") {
			currentAnimation = @"down_left";
			currentFrame = 0;
			position.x-=speed;
			position.y+=speed;
		}
		else if (currentFrame < 3) {
			position.x-=speed;
			position.y+=speed;
		}
		else {
			position.x-=speed;
			position.y+=speed;
		}
	}
}

- (void) moveRightDown{
	if (!istouched) {
		if (currentAnimation != @"down_right") {
			currentAnimation = @"down_right";
			currentFrame = 0;
			position.x+=speed;
			position.y+=speed;
		}
		else if (currentFrame < 3) {
			position.x+=speed;
			position.y+=speed;
		}
		else {
			position.x+=speed;
			position.y+=speed;
		}
	}
}

- (void) moveRightTop{
	if (!istouched) {
		if (currentAnimation != @"up_right") {
			currentAnimation = @"up_right";
			currentFrame = 0;
			position.x+=speed;
			position.y-=speed;
		}
		else if (currentFrame < 3) {
			position.x+=speed;
			position.y-=speed;
		}
		else {
			position.x+=speed;
			position.y-=speed;
		}
	}
}

- (void) stopTop{
	if (!istouched) {
		currentAnimation = @"stop_up";
		currentFrame = 0;
	}
}

- (void) stopDown{
	if (!istouched) {
		currentAnimation = @"stop_down";
		currentFrame = 0;
	}
}
- (void) stopLeft{
	if (!istouched) {
		currentAnimation = @"stop_left";
		currentFrame = 0;
	}
}

- (void) stopRight{
	if (!istouched) {
		currentAnimation = @"stop_right";
		currentFrame = 0;
	}
}

- (void) stopLeftTop{
	if (!istouched) {
		currentAnimation = @"stop_up_left";
		currentFrame = 0;
	}
}

- (void) stopRightTop{
	if (!istouched) {
		currentAnimation = @"stop_up_right";
		currentFrame = 0;
	}
}
- (void) stopLeftDown{
	if (!istouched) {
		currentAnimation = @"stop_down_left";
		currentFrame = 0;
	}
}
- (void) stopRightDown{
	if (!istouched) {
		currentAnimation = @"stop_down_right";
		currentFrame = 0;
	}
}



- (void) plantingBomb:(Bomb *) aBomb{
	bombPosed = YES;
}



- (void) draw:(CGContextRef)context{
    
	NSMutableArray * sequences = ((AnimationSequence *)[animations valueForKey:currentAnimation]).sequences;
	
	if (currentFrame < [sequences count]){
		UIImage * image = [sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y-(ressource.tileSize/2), ressource.tileSize , ressource.tileSize*1.5)];
	}
	else{
		if ([sequences count] == 1) {
			currentFrame = 0;
			UIImage * image = [sequences objectAtIndex:currentFrame];
			[image drawInRect:CGRectMake(position.x, position.y-(ressource.tileSize/2), ressource.tileSize , ressource.tileSize*1.5)];
		}
	}
}


- (void) draw:(CGContextRef)context alpha:(CGFloat)alpha {
    NSMutableArray * sequences = ((AnimationSequence *)[animations valueForKey:currentAnimation]).sequences;

	if (currentFrame < [sequences count]){
		UIImage * image = [sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y-(ressource.tileSize/2), ressource.tileSize , ressource.tileSize*1.5) blendMode:kCGBlendModeNormal alpha:alpha];
	}
	else {
		if ([sequences count] == 1) {
            [idle drawInRect:CGRectMake(position.x, position.y-(ressource.tileSize/2), ressource.tileSize , ressource.tileSize*1.5) blendMode:kCGBlendModeNormal alpha:alpha];
		}
	}
}


- (void)update{
	if (delay >= ((AnimationSequence *)[animations objectForKey:currentAnimation]).delayNextFrame) {
		delay = 0;
		if (currentFrame >= [((AnimationSequence *)[animations objectForKey:currentAnimation]).sequences count]-1) {
			currentFrame = 0;
			if (istouched) {
				istouched = NO;
			}
		}
		else
			currentFrame++;
	}
	else{
		delay++;
	}
}

- (Player *)copy {
	Player * playerTmp = [[Player alloc] init];
	playerTmp.ressource = ressource;
    playerTmp.imageName = [[NSString alloc] initWithString:imageName];
	playerTmp.hit = hit;
	playerTmp.level = level;
	playerTmp.fireWall = fireWall;
	playerTmp.damage = damage;
	playerTmp.position = [[Position alloc] initWithPosition:position];
	
	playerTmp.animations = [[NSMutableDictionary alloc] initWithDictionary:animations];
	playerTmp.destroyAnimations = [[NSMutableDictionary alloc] initWithDictionary:destroyAnimations];
	playerTmp.idle = idle;
	
	playerTmp.currentAnimation = [[NSString alloc] initWithString:currentAnimation];
	playerTmp.currentFrame = currentFrame;
	playerTmp.waitDelay = waitDelay;
	playerTmp.delay = delay;
	
	playerTmp.bombsTypes = [[NSMutableArray alloc] initWithArray:bombsTypes];
	playerTmp.color = [[NSMutableString alloc] initWithString:color];
	playerTmp.lifeNumber = lifeNumber;
	playerTmp.powerExplosion = powerExplosion;
	playerTmp.timeExplosion = timeExplosion;
	playerTmp.shield = shield;
	playerTmp.speed = speed;
	playerTmp.bombNumber = bombNumber;
	
	return playerTmp;
}

- (void) hurt{
	currentAnimation = @"touched";
	currentFrame = 0;
	istouched = YES;
}

- (void)destroy {
	currentFrame = 0;
	currentAnimation = @"touched";
	istouched = YES;
}

@end
