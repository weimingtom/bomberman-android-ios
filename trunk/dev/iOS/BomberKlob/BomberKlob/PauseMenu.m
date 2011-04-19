//
//  PauseMenu.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 18/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PauseMenu.h"
#import "EditorViewController.h"
#import "PauseMenuView.h"


@implementation PauseMenu

@synthesize pauseMenuView, editorViewController;


- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController {
    self = [super init];
    
    if (self) {
        editorViewController = myController;
        pauseMenuView = [[PauseMenuView alloc] initWithFrame:frame controller:self];
    }
    
    return self;
}


- (void)dealloc {
    
    [super dealloc];
}


- (void)resumeAction {
    [editorViewController resumeAction];
}


- (void)saveAction {
    [editorViewController saveAction];
}


- (void)quitAction {
    [editorViewController quitAction];
}

@end
