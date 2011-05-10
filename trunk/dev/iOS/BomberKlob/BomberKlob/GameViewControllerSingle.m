//
//  GameControllerSingle.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameViewControllerSingle.h"
#import "Engine.h"
#import "Game.h"
#import "Map.h"
#import "Position.h"
#import "RessourceManager.h"
#import "GlobalGameViewControllerSingle.h"


@implementation GameViewControllerSingle
@synthesize gameView,globalController;

- (id) initWithFrame:(CGRect)dimensionValue Controller:(GlobalGameViewControllerSingle *)controllerValue{
	self = [super init];
	if (self){
		self.globalController = controllerValue;
		self.gameView = [[GameView alloc] initWithController:self frame:dimensionValue];
	}
	return self;
}
- (void)dealloc {
	[globalController release];
	[gameView release];
    [super dealloc];
}

-(BOOL) gameIsStarted{
	return [globalController gameIsStarted];
}

- (void) updateMap {
	return [globalController updateMap];
}


@end
