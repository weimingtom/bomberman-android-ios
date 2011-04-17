//
//  EditorMapZoneView.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class EditorMapZone, Position;


@interface EditorMapZoneView : UIView {
    
    EditorMapZone *editorMapZone;
    
    NSInteger tileSize;
    Position *oldTouchPosition;
}

@property (nonatomic, retain) EditorMapZone *editorMapZone;
@property (nonatomic, retain) Position *oldTouchPosition;

- (id)initWithFrame:(CGRect)frame controller:(EditorMapZone *)myController;

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;

@end
