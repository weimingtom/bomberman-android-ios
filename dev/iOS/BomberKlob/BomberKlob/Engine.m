//
//  Engine.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Engine.h"
#import "Player.h"


@implementation Engine
@synthesize game;

- (id) initWithGame:(Game *) gameValue{
	self = [super init];
	if (self){
		game = gameValue;
	}
	return self;
}



- (void) moveTop{
	((Player *)[game.players objectAtIndex:0]).moveTop;
}

- (void) moveDown{
    ((Player *)[game.players objectAtIndex:0]).moveDown;
}

- (void) moveLeft{
    ((Player *)[game.players objectAtIndex:0]).moveLeft;
}

- (void) moveRight{
    ((Player *)[game.players objectAtIndex:0]).moveRight;
}

- (void) moveLeftTop{
	((Player *)[game.players objectAtIndex:0]).moveLeftTop;
}

- (void) moveLeftDown{
	((Player *)[game.players objectAtIndex:0]).moveLeftDown;
}

- (void) moveRightDown{
	((Player *)[game.players objectAtIndex:0]).moveRightDown;
}

- (void) moveRightTop{
	((Player *)[game.players objectAtIndex:0]).moveRightTop;

}

@end
