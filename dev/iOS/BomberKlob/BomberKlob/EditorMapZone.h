//
//  EditorMap.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#define ALPHA 0.2


@class EditorMapZoneView, EditorViewController, Position, Object;

@interface EditorMapZone : NSObject {
    
    EditorMapZoneView *editorMapZoneView;
    EditorViewController *editorViewController;
    
    CGFloat alpha;
}

@property (nonatomic, retain) EditorMapZoneView *editorMapZoneView;
@property (nonatomic, retain) EditorViewController *editorViewController;
@property (nonatomic) CGFloat alpha;

- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController;
- (void)dealloc;

- (void)clickOnPosition:(Position *)position;
- (void)displayBlocks:(BOOL)display;

@end
