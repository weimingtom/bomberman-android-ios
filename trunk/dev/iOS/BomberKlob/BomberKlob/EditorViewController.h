//
//  EditorController.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class MapEditor, EditorMapZone, EditorInformation, EditorAction, PauseMenu, Position, Object;


@interface EditorViewController : UIViewController {
    
    MapEditor *mapEditor;
    
    EditorMapZone *editorMapZone;
    EditorInformation *editorInformation;
    EditorAction *editorAction;
    PauseMenu *pauseMenu;
}

@property (nonatomic, retain) MapEditor *mapEditor;

- (id)initWithMapName:(NSString *)mapName;
- (void)dealloc;

- (void)clickOnPosition:(Position *)position;
- (void)pauseAction;
- (void)resumeAction;
- (void)saveAction;
- (void)quitAction;

@end
