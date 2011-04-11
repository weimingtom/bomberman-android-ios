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

@end
