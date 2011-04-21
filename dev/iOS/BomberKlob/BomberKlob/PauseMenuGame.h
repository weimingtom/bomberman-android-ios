//
//  PauseMenuGame.h
//  BomberKlob
//
//  Created by Kilian Coubo on 21/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@class PauseMenuGameView, GlobalGameViewControllerSingle;


@interface PauseMenuGame : NSObject {
	
    PauseMenuGameView *pauseMenuView;
    GlobalGameViewControllerSingle *globalViewController;
}

@property (nonatomic, retain) PauseMenuGameView *pauseMenuView;
@property (nonatomic, retain) GlobalGameViewControllerSingle *globalViewController;

- (id)initWithFrame:(CGRect)frame controller:(GlobalGameViewControllerSingle *)myController;
- (void)dealloc;

- (void)resumeAction;
- (void)saveAction;
- (void)quitAction;

@end
