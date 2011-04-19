//
//  EditorAction.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class EditorActionView, EditorViewController;


@interface EditorAction : NSObject {
    
    EditorActionView *editorActionView;
    EditorViewController *editorViewController;
    
    NSString *selectedObjectType;
    NSString *selectedObject;
    BOOL removeTool;
}

@property (nonatomic, retain) EditorActionView *editorViewAction;
@property (nonatomic, retain) EditorViewController *editorViewController;
@property (nonatomic, retain) NSString *selectedObjectType;
@property (nonatomic, retain) NSString *selectedObject;
@property (nonatomic) BOOL removeTool;

- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController;
- (void)dealloc;



@end
