//
//  GameController.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameViewController.h"
#import "Position.h"


@implementation GameViewController

@synthesize gameView;

- (id) init {
	self = [super init];
	
	if (self) {
		currentPosition = [[Position alloc] initWithX:0 y:0];
		lastPosition = [[Position alloc] initWithX:0 y:0];
	}
	
	return self;
}

- (void)load{

}


- (void)remove{
	
}



// Dealloc
- (void)dealloc {
	
	[gameView release];
	
	[super dealloc];
	
}

- (void) managementMovement{
	
}



@end
