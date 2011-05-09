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
@synthesize speed, lifeNumber, bombNumber,bombsTypes,powerExplosion,shield,timeExplosion, color, bombPosed, png, istouched, isKilled,isInvincible;

- (id) init{
	self = [super init];
	if (self){
		color = @"white";
		lifeNumber = 0;
		powerExplosion = 1;
		timeExplosion = 5;
		shield = 1;
		speed = 1;
		bombNumber = 1;
		position.x = 0;
		position.y = 0;
		currentAnimation = @"idle";
		bombsTypes = [[NSMutableArray alloc] init];
		png = [[NSMutableDictionary alloc] init];
		bombsTypes = [[NSMutableDictionary alloc] init];
		[bombsTypes setObject:[ressource.bitmapsBombs objectForKey:@"normal"] forKey:@"normal"];
		timeInvincible = 80;
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
		png = [[NSMutableDictionary alloc] init];
		bombsTypes = [[NSMutableDictionary alloc] init];
		[bombsTypes setObject:[ressource.bitmapsBombs objectForKey:@"normal"] forKey:@"normal"];
		timeInvincible = 80;
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
	NSLog(@"hello");
}



- (void) draw:(CGContextRef)context{
    
	NSMutableArray * sequences = ((AnimationSequence *)[animations valueForKey:currentAnimation]).sequences;
	if (!isInvincible) {
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
	else if((timeInvincible % 5) == 0) {
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
	if (timeInvincible < 0) {
		isInvincible = NO;
	}
	else {
		timeInvincible--;
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
				timeInvincible = 80;
				isInvincible = YES;
				currentAnimation = @"idle";
			}
		}
		else
			currentFrame++;
	}
	else{
		delay++;
	}
}

- (id)copyWithZone:(NSZone *)zone {
    Player *copy = [super copyWithZone:zone];
    
    NSMutableDictionary *bombsTypeTmp = [[NSMutableDictionary alloc] initWithDictionary:bombsTypes];
    NSString *colorTmp = [[NSString alloc] initWithString:color];
	
	copy.bombsTypes = bombsTypeTmp;
	copy.color = colorTmp;
	copy.lifeNumber = lifeNumber;
	copy.powerExplosion = powerExplosion;
	copy.timeExplosion = timeExplosion;
	copy.shield = shield;
	copy.speed = speed;
	copy.bombNumber = bombNumber;
	copy.png = [[NSMutableDictionary alloc] initWithDictionary:png];
    
    [bombsTypeTmp release];
    [colorTmp release];
    
	return copy;
}

- (void) hurt{
	if (!isInvincible) {
		currentAnimation = @"touched";
		currentFrame = 0;
		istouched = YES;
	}
}

- (void)destroy {
	if (!isInvincible) {
		currentFrame = 0;
		istouched = YES;
		if (lifeNumber <= 0) {
			currentAnimation = @"kill";
			isKilled = YES;
		}
		else
			[self hurt];
	}
}

@end
