//
//  Game.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Game.h"


@implementation Game

@synthesize players, map;

- (id) init{
	self = [super init];
	if (self){
		players = [[NSMutableArray alloc] init];
		[players addObject:[[Player alloc] init]];
		map = [[Map alloc] init];
	}
	return self;
}

- (void) initGame{
	
}


- (void) startGame{
	
}


- (void) endGame{
	
}


- (void) draw{
	
}



@end
