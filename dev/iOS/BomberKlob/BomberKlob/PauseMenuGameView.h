//
//  PauseMenuGameView.h
//  BomberKlob
//
//  Created by Kilian Coubo on 21/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class PauseMenu, RessourceManager;


@interface PauseMenuGameView : UIView {
	
    PauseMenu *pauseMenu;
    
    RessourceManager *resource;
    
    UIButton *resume;
    UIButton *quit;
    
    BOOL newMap;
}

@property (nonatomic, retain) PauseMenu *pauseMenu;
@property (nonatomic, retain) UIButton *resume;
@property (nonatomic, retain) UIButton *save;
@property (nonatomic, retain) UIButton *quit;

- (id)initWithFrame:(CGRect)frame controller:(PauseMenu *)myController;
- (void)dealloc;

- (void)initUserInterface;

- (void)resumeAction;

- (void)quitAction;

@end
