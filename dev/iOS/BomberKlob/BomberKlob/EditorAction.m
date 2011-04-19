//
//  EditorAction.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorAction.h"
#import "EditorActionView.h"
#import "EditorViewController.h"


@implementation EditorAction

@synthesize editorViewAction, editorViewController, selectedObject, selectedObjectType, removeTool;


- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController {
    self = [super init];
    
    if (self) {
        editorViewController = myController;
        editorViewAction = [[EditorActionView alloc] initWithFrame:frame controller:self];
        selectedObjectType = @"Inanimated";
        selectedObject = @"bloc";
        removeTool = NO;
    }
    
    return self;
}


- (void)dealloc {
    [editorViewAction release];
    [super dealloc];
}

@end
