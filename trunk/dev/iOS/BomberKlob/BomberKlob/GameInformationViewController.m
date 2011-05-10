//
//  GameInformationViewController.m
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameInformationViewController.h"
#import "GameInformationView.h"
#import "RessourceManager.h"
#import "GlobalGameViewControllerSingle.h"

@implementation GameInformationViewController

@synthesize informationView, globalController;

- (id) initWithFrame:(CGRect)dimensionValue Controller:(GlobalGameViewControllerSingle *)controllerValue{
	self = [super init];
	
	if (self){
		globalController = controllerValue;
		self.informationView = [[GameInformationView alloc] initWithFrame:dimensionValue Controller:self];	
		[informationView release];
	}
	return self;
}

- (void)dealloc {
    [informationView release];
	[globalController release];
    [super dealloc];
}

- (Player *) getHumanPlayer {
	return [globalController getHumanPlayer];
}

- (NSInteger) nbPlayers {
	return [globalController nbPlayers];
}

@end
