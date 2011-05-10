//
//  PauseMenuGame.m
//  BomberKlob
//
//  Created by Kilian Coubo on 21/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PauseMenuGame.h"
#import "GlobalGameViewControllerSingle.h"


@implementation PauseMenuGame

@synthesize pauseMenuView, globalViewController;


- (id)initWithFrame:(CGRect)frame controller:(GlobalGameViewControllerSingle *)myController {
    self = [super init];
    
    if (self) {
        globalViewController = myController;
        pauseMenuView = [[PauseMenuGameView alloc] initWithFrame:frame controller:self];
    }
    
    return self;
}


- (void)dealloc {
	[pauseMenuView release];
    [globalViewController release];
    [super dealloc];
}


- (void)resumeAction {
    [globalViewController resumeAction];
}


- (void)quitAction {
    [globalViewController quitAction];
}


@end
