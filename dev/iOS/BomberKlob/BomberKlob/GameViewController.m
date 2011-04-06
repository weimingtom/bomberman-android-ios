//
//  GameController.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameViewController.h"


@implementation GameViewController

@synthesize gameView;

- (id) init {
	
	NSLog(@"INIT GameController");
	self = [super init];
	
	if (self) {
		self.gameView = [[GameView alloc ]initWithFrame:CGRectMake(0, 0, 320, 460)];
		[self load];
	}
	return self;
}

- (void)load{
	[self.view addSubview:gameView];
	[gameView release];
}


- (void)remove{
	
}



// Dealloc
- (void)dealloc {
	
	[super dealloc];
	
}

- (void) managementMovement{
	
}

@end
