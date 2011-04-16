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


@implementation GameActionViewController
@synthesize actionView, dimension;

- (id) init{
	self = [super init];
	
	if (self){
		RessourceManager * resource = [RessourceManager sharedRessource];
		dimension = CGRectMake(resource.screenHeight*0.90, resource.screenWidth*0.12, resource.screenHeight, resource.screenWidth*0.88);
		self.actionView = [[GameActionView alloc] initWithFrame:dimension];	
		[self.view addSubview:actionView];
		[actionView release];
	}
	return self;
}

@end
