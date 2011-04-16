//
//  Engine.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Engine.h"
#import "Player.h"
#import "RessourceManager.h"


@implementation Engine
@synthesize game;

- (id) initWithGame:(Game *) gameValue{
	self = [super init];
	if (self){
		resource = [RessourceManager sharedRessource];
		game = gameValue;
	}
	return self;
}

- (BOOL) isInCollision: (Player *) player: (NSInteger) xValue: (NSInteger) yValue{

	if (player.position.x+xValue < 0) {
		return true;
	}
	if (player.position.x+xValue + resource.tileSize > resource.screenHeight){
		return true;
	}
	if (player.position.y+yValue + resource.tileSize*2 > resource.screenWidth){
		return true;
	}
	if (player.position.y+yValue < 0){
		return true;
	}
	int xmin = floor((player.position.x+xValue+player.speed) / resource.tileSize);
	int ymin = floor((player.position.y+yValue+player.speed+resource.tileSize) / resource.tileSize);
	
	int xmax = ceil((player.position.x+xValue + resource.tileSize-player.speed) / resource.tileSize);
	int ymax = ceil(((player.position.y+yValue + (resource.tileSize*2)-player.speed) / resource.tileSize));
		
	for (int i=xmin; i <= xmax; i++) {
		for (int j =ymin; j <= ymax; j++) {
			if ((xmin >= 0 && ymin >=0) && (xmax < game.map.width && ymax < game.map.height)) {
				if (![[[game.map.blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"])
					return true;
			}
			else
				return true;

		}
	}

	return false;

}


- (void) moveTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :0 :-player.speed]){
		player.moveTop;
	}
	
	//NSLog(@"hello  : %f - %f",floor(player.position.y/15.0),ceil(player.position.y/15.0));

	/*if (player.position.y+resource.tileHeight*2 >= floor(player.position.y/15) && player.position.y+resource.tileHeight*2 >= round(player.position.y/15)) {
	}*/
}

- (void) moveDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :0 :player.speed]){
		player.moveDown;
	}
}

- (void) moveLeft{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :-player.speed :0]){
		player.moveLeft;
	}
}

- (void) moveRight{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :player.speed :0]){
		player.moveRight;
	}
}

- (void) moveLeftTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :-player.speed :-player.speed]){
		player.moveLeftTop;
	}
}

- (void) moveLeftDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :-player.speed :player.speed]){
		player.moveLeftDown;
	}
}

- (void) moveRightDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :player.speed :player.speed]){
		player.moveRightDown;
	}
}

- (void) moveRightTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :player.speed :-player.speed]){
		player.moveRightTop;
	}

}

@end
