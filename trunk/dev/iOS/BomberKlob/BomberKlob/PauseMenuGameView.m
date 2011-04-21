//
//  PauseMenuGameView.m
//  BomberKlob
//
//  Created by Kilian Coubo on 21/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PauseMenuGameView.h"


#import "PauseMenuView.h"
#import "PauseMenu.h"
#import "RessourceManager.h"


@implementation PauseMenuGameView

@synthesize pauseMenu, resume, save, quit;


- (id)initWithFrame:(CGRect)frame controller:(PauseMenu *)myController {
    self = [super initWithFrame:frame];
    
    if (self) {
        resource = [RessourceManager sharedRessource];
        
        self.pauseMenu = myController;
        self.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.65];
        
        [self initUserInterface]; 
    }
    
    return self;
}


- (void)dealloc {
    [super dealloc];
}


- (void)initUserInterface {
    
    resume = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    resume.frame = CGRectMake(0, 0, 80, 37);
    resume.center = CGPointMake(resource.screenHeight / 2, 100);
    [resume setTitle:@"Resume" forState:UIControlStateNormal];
    [resume addTarget:self action:@selector(resumeAction) forControlEvents:UIControlEventTouchDown];
    
    save = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    save.frame = CGRectMake(0, 0, 60, 37);
    save.center = CGPointMake(resource.screenHeight / 2, 150);
    [save setTitle:@"Save" forState:UIControlStateNormal];
    [save addTarget:self action:@selector(saveAction) forControlEvents:UIControlEventTouchDown];
    
    quit = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    quit.frame = CGRectMake(0, 0, 56, 37);
    quit.center = CGPointMake(resource.screenHeight / 2, 200);
    [quit setTitle:@"Quit" forState:UIControlStateNormal];
    [quit addTarget:self action:@selector(quitAction) forControlEvents:UIControlEventTouchDown];
    
    [self addSubview:resume];
    [self addSubview:save];
    [self addSubview:quit];
}


- (void)resumeAction {
    [pauseMenu resumeAction];
}


- (void)saveAction {
    [pauseMenu saveAction];
}


- (void)quitAction {
    [pauseMenu quitAction];
}

@end
