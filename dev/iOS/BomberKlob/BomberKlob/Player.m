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
@synthesize speed, lifeNumber, bombsPlanted, bombNumber,bombsTypes,powerExplosion,shield,timeExplosion, color;

- (id) init{
	self = [super init];
	if (self){
		color = @"white";
		lifeNumber = 0;
		powerExplosion = 1;
		timeExplosion = 3;
		shield = 1;
		speed = 1;
		bombNumber = 1;
		position.x = 0;
		position.y = 0;
		currentAnimation = @"idle";
		bombsPlanted = [[NSMutableArray alloc] init];
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
		timeExplosion = 3;
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
    [bombsPlanted release];
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


- (void) moveDown{
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


- (void) moveLeft{
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


- (void) moveRight{
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

- (void) moveLeftTop{
	if (currentAnimation != @"upLeft") {
		currentAnimation = @"upLeft";
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

- (void) moveLeftDown{
	if (currentAnimation != @"downLeft") {
		currentAnimation = @"downLeft";
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

- (void) moveRightDown{
	if (currentAnimation != @"downRight") {
		currentAnimation = @"downRight";
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

- (void) moveRightTop{
	if (currentAnimation != @"upRight") {
		currentAnimation = @"upRight";
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


- (void) plantingBomb{
	
}


- (void) hurt{
	
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
        [image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize , ressource.tileSize*1.5) blendMode:kCGBlendModeNormal alpha:alpha];
	}
	else{
		if ([sequences count] == 1) {
			currentFrame = 0;
			UIImage * image = [sequences objectAtIndex:currentFrame];
            [image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize , ressource.tileSize*1.5) blendMode:kCGBlendModeNormal alpha:alpha];
		}
	}
}


- (void)update{
	if (delay == waitDelay) {
		delay = 0;
		if (currentFrame >= 3) {
			currentFrame = 0;
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
	playerTmp.damages = damages;
	playerTmp.position = [[Position alloc] initWithPosition:position];
	
	playerTmp.animations = [[NSMutableDictionary alloc] initWithDictionary:animations];
	playerTmp.destroyAnimations = [[NSMutableDictionary alloc] initWithDictionary:destroyAnimations];
	playerTmp.idle = idle;
	
	playerTmp.currentAnimation = [[NSString alloc] initWithString:currentAnimation];
	playerTmp.currentFrame = currentFrame;
	playerTmp.waitDelay = waitDelay;
	playerTmp.delay = delay;
	
	playerTmp.bombsPlanted = [[NSMutableArray alloc] initWithArray:bombsPlanted];
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

@end
