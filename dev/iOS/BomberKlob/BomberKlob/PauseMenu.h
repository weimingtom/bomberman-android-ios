//
//  PauseMenu.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 18/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class PauseMenuView, EditorViewController;


@interface PauseMenu : NSObject {

    PauseMenuView *pauseMenuView;
    EditorViewController *editorViewController;
}

@property (nonatomic, retain) PauseMenuView *pauseMenuView;
@property (nonatomic, retain) EditorViewController *editorViewController;

- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController;
- (void)dealloc;

- (void)resumeAction;
- (void)saveAction;
- (void)quitAction;

@end
