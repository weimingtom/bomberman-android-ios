//
//  EditorMap.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@class EditorMapZoneView, EditorViewController, Position, Object;

@interface EditorMapZone : NSObject {
    
    EditorMapZoneView *editorMapZoneView;
    EditorViewController *editorViewController;
}

@property (nonatomic, retain) EditorMapZoneView *editorMapZoneView;
@property (nonatomic, retain) EditorViewController *editorViewController;

- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController;
- (void)dealloc;

- (void)clickOnPosition:(Position *)position;

@end
