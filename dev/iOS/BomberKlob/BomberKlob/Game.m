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
		[players addObject:[[Player alloc] initWithColor:@"black" position:[[Position alloc] initWithXAndY:0 :0]]];
		[players addObject:[[Player alloc] initWithColor:@"blue" position:[[Position alloc] initWithXAndY:200 :0]]];
		[players addObject:[[Player alloc] initWithColor:@"red" position:[[Position alloc] initWithXAndY:0 :200]]];
		[players addObject:[[Player alloc] initWithColor:@"white" position:[[Position alloc] initWithXAndY:200 :200]]];

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
