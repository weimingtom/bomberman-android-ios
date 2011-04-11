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
	
	NSLog(@"INIT GameController");
	self = [super init];
	
	if (self) {
		currentPosition = [[Position alloc] initWithXAndY:(NSUInteger)0:(NSUInteger) 0];
		lastPosition = [[Position alloc] initWithXAndY:(NSUInteger)0:(NSUInteger) 0];
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
	
	[gameView release];
	
	[super dealloc];
	
}

- (void) managementMovement{
	
}



@end
