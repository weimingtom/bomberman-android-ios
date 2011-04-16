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

- (id) initWithFrame:(CGRect)dimensionValue Engine:(Engine*) engineValue{
	self = [super init];
	
	if (self){
		RessourceManager * resource = [RessourceManager sharedRessource];
		dimension = dimensionValue;
		self.informationView = [[GameInformationView alloc] initWithFrame:dimension Engine:engineValue];	
		[self.view addSubview:informationView];
		[informationView release];
	}
	return self;
}

@end
