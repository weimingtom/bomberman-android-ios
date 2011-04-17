//
//  EditorMap.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorMapZone.h"
#import "EditorMapZoneView.h"
#import "EditorViewController.h"
#import "Position.h"


@implementation EditorMapZone

@synthesize editorMapZoneView, editorViewController;


- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController {
    self = [super init];
    
    if (self) {
        self.editorViewController = myController;
        editorMapZoneView = [[EditorMapZoneView alloc] initWithFrame:frame controller:self];	
    }
    
    return self;
}


- (void)dealloc {
    [editorMapZoneView release];
    [super dealloc];
}


- (void)clickOnPosition:(Position *)position {
    [editorViewController clickOnPosition:position];
}

@end
