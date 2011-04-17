//
//  EditorController.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class MapEditor, EditorMapZone, EditorInformation, EditorAction, Position, Object;


@interface EditorViewController : UIViewController {
    
    MapEditor *mapEditor;
    
    EditorMapZone *editorMapZone;
    EditorInformation *editorInformation;
    EditorAction *editorAction;
}

@property (nonatomic, retain) MapEditor *mapEditor;

- (id)init;
- (void)dealloc;

- (void)clickOnPosition:(Position *)position;

@end
