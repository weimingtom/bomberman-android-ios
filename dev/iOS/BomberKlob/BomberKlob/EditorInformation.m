//
//  EditorInformation.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorInformation.h"
#import "EditorInformationView.h"
#import "EditorViewController.h"


@implementation EditorInformation

@synthesize editorInformationView, editorViewController;


- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController {
    self = [super init];
    
    if (self) {
        editorViewController = myController;
        editorInformationView = [[EditorInformationView alloc] initWithFrame:frame controller:self];
    }
    
    return self;
}


- (void)dealloc {
    [editorInformationView release];
    [super dealloc];
}


- (void)pauseAction {
    [editorViewController pauseAction];
}

@end
