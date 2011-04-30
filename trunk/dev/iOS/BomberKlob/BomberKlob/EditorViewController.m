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
#import "EditorInformationView.h"
#import "EditorAction.h"
#import "EditorActionView.h"
#import "RessourceManager.h"
#import "Map.h"
#import "Position.h"
#import "Objects.h"
#import "PauseMenu.h"
#import "PauseMenuView.h"
#import "MainMenuViewController.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "DBMap.h"
#import "Undestructible.h"


@implementation EditorViewController

@synthesize mapEditor, selectedTool;


- (id)initWithMapName:(NSString *)mapName {
	self = [super init];
	
	if (self) {
        CGRect frame;
        RessourceManager *resource = [RessourceManager sharedRessource];
        selectedTool = @"blocks";
        
		mapEditor = [[MapEditor alloc] initWithMapName:mapName];

        frame = CGRectMake(0, resource.screenWidth - (mapEditor.map.height * resource.tileSize), resource.tileSize * mapEditor.map.width, resource.tileSize * mapEditor.map.height);        
        editorMapZone = [[EditorMapZone alloc] initWithFrame:frame controller:self];
        
        frame = CGRectMake(0, 0, resource.screenHeight, resource.screenWidth - (mapEditor.map.height * resource.tileSize));
        editorInformation = [[EditorInformation alloc] initWithFrame:frame controller:self];
        
        frame = CGRectMake(mapEditor.map.width * resource.tileSize, resource.screenWidth - (mapEditor.map.height * resource.tileSize), resource.screenHeight - (mapEditor.map.width * resource.tileSize), mapEditor.map.height * resource.tileSize);
        editorAction = [[EditorAction alloc] initWithFrame:frame controller:self];
        
        frame = CGRectMake(0, 0, resource.screenHeight, resource.screenWidth);
        pauseMenu = [[PauseMenu alloc] initWithFrame:frame controller:self];
        
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
    
    if (selectedTool == @"delete") {
        [mapEditor deleteBlockAtPosition:position];
    }
    else if (selectedTool == @"player") {
        [mapEditor addPlayer:position];
    }
    else {
        Objects *block = nil;
        
        if ([editorAction.selectedObjectType isEqual:@"Inanimated"]) {
            block = [(Undestructible *)[[RessourceManager sharedRessource].bitmapsAnimates objectForKey:editorAction.selectedObject] copy];
//            block = [[Undestructible alloc] initWithImageName:editorAction.selectedObject position:position];
        }
        
        if (block != nil)
            [mapEditor addBlock:block position:position];
    }
}


- (void)displayBlocks:(BOOL)display {
    [editorMapZone displayBlocks:display];
}


- (void)pauseAction {
    [self.view addSubview:pauseMenu.pauseMenuView];
}


- (void)saveAction {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [mapEditor saveMapWithOwner:application.user];
    [application loadMaps];
}


- (void)resumeAction {
    [pauseMenu.pauseMenuView removeFromSuperview];
}


- (void)quitAction {
    MainMenuViewController *mainMenuViewController = [[MainMenuViewController alloc] initWithNibName:@"MainMenuViewController" bundle:nil];
    self.navigationController.navigationBarHidden = NO;
    [self.navigationController pushViewController:mainMenuViewController animated:NO];
    [mainMenuViewController release];
}

@end
