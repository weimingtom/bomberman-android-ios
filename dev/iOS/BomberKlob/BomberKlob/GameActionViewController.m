//
//  GameActionViewController.m
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameActionViewController.h"
#import "GameActionView.h"
#import "RessourceManager.h"
#import "Engine.h"
#import "GlobalGameViewControllerSingle.h"


@implementation GameActionViewController
@synthesize actionView, globalController;

- (id) initWithFrame:(CGRect)dimensionValue Controller:(GlobalGameViewControllerSingle *)controllerValue{
	self = [super init];
	
	if (self){
		self.globalController = controllerValue;
		self.actionView = [[GameActionView alloc] initWithFrame:dimensionValue Controller:self];	
	}
	return self;
}


- (void)plantingBomb {
    [globalController plantingBomb];
}

@end
