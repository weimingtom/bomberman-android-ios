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

@implementation GameInformationViewController

@synthesize informationView, dimension;

- (id) init{
	self = [super init];
	
	if (self){
		RessourceManager * resource = [RessourceManager sharedRessource];
		dimension = CGRectMake(0, 0,resource.screenHeight , resource.screenWidth*0.12);
		self.informationView = [[GameInformationView alloc] initWithFrame:dimension];	
		[self.view addSubview:informationView];
		[informationView release];
	}
	return self;
}

@end
