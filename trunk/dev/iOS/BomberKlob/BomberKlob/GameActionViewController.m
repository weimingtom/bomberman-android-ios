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


@implementation GameActionViewController
@synthesize actionView, dimension;

- (id) initWithFrame:(CGRect)dimensionValue Engine:(Engine*)engineValue{
	self = [super init];
	
	if (self){
		RessourceManager * resource = [RessourceManager sharedRessource];
		dimension = dimensionValue;
		self.actionView = [[GameActionView alloc] initWithFrame:dimension Engine:engineValue];	
		[self.view addSubview:actionView];
		[actionView release];
	}
	return self;
}



@end
