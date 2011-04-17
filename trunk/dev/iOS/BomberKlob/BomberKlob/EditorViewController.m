//
//  EditorController.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorViewController.h"
#import "MapEditor.h"
#import "EditorMapZone.h"
#import "EditorMapZoneView.h"
#import "EditorInformation.h"
#import "EditorAction.h"
#import "RessourceManager.h"
#import "Map.h"
#import "Position.h"
#import "Object.h"
#import "Inanimated.h"


@implementation EditorViewController

@synthesize mapEditor;


- (id)init {
	self = [super init];
	
	if (self) {
        CGRect frame;
        RessourceManager *resource = [RessourceManager sharedRessource];
        
		mapEditor = [[MapEditor alloc] init];

        frame = CGRectMake(0, resource.screenWidth - (mapEditor.map.height * resource.tileSize), resource.tileSize * mapEditor.map.width, resource.tileSize * mapEditor.map.height);        
        editorMapZone = [[EditorMapZone alloc] initWithFrame:frame controller:self];
        
        frame = CGRectMake(0, 0, resource.screenHeight, resource.screenWidth - (mapEditor.map.height * resource.tileSize));
        editorInformation = [[EditorInformation alloc] initWithFrame:frame controller:self];
        
        frame = CGRectMake(mapEditor.map.width * resource.tileSize, resource.screenWidth - (mapEditor.map.height * resource.tileSize), resource.screenHeight - (mapEditor.map.width * resource.tileSize), mapEditor.map.height * resource.tileSize);
        editorAction = [[EditorAction alloc] initWithFrame:frame controller:self];
        
        [self.view addSubview:editorMapZone.editorMapZoneView];
        [self.view addSubview:editorInformation.editorInformationView];
        [self.view addSubview:editorAction.editorViewAction];
        
        [editorMapZone.editorMapZoneView release];
        [editorInformation.editorInformationView release];
        [editorAction.editorViewAction release];
	}
	
	return self;
}


- (void)dealloc {
    [mapEditor release];
    [editorMapZone release];
    [editorInformation release];
    [editorAction release];
    [super dealloc];
}


- (void)clickOnPosition:(Position *)position {
    
    if (editorAction.removeTool) {
        [mapEditor deleteBlockAtPosition:position];
    }
    else {
        Object *block = nil;
        
        if ([editorAction.selectedObjectType isEqual:@"Inanimated"]) {
            block = [[Inanimated alloc] initWithImageName:editorAction.selectedObject position:position];
        }
        
        if (block != nil)
            [mapEditor addBlock:block position:position];
    }
}

@end
