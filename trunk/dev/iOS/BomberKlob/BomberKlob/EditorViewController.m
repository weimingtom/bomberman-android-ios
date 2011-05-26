#import "EditorViewController.h"
#import "Editor.h"
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
#import "EditorMap.h"


@implementation EditorViewController

@synthesize mapEditor, selectedTool;


- (id)initWithMapName:(NSString *)mapName {
	self = [super init];
	
	if (self) {
        CGRect frame;
        RessourceManager *resource = [RessourceManager sharedRessource];
        selectedTool = @"blocks";
        
		mapEditor = [[Editor alloc] initWithMapName:mapName];

        frame = CGRectMake(0, resource.screenWidth - (mapEditor.map.height * resource.tileSize), resource.tileSize * mapEditor.map.width, resource.tileSize * mapEditor.map.height);        
        editorMapZone = [[EditorMapZone alloc] initWithFrame:frame controller:self];
        
        frame = CGRectMake(0, 0, resource.screenHeight, resource.screenWidth - (mapEditor.map.height * resource.tileSize));
        editorInformation = [[EditorInformation alloc] initWithFrame:frame controller:self];
        
        frame = CGRectMake(mapEditor.map.width * resource.tileSize, resource.screenWidth - (mapEditor.map.height * resource.tileSize), resource.screenHeight - (mapEditor.map.width * resource.tileSize), mapEditor.map.height * resource.tileSize);
        editorAction = [[EditorAction alloc] initWithFrame:frame controller:self];
        
        frame = CGRectMake(0, 0, resource.screenHeight, resource.screenWidth);
        pauseMenu = [[PauseMenu alloc] initWithFrame:frame controller:self];
        pauseMenu.pauseMenuView.alpha = 0.0;
        
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
    [pauseMenu release];
    [selectedTool release];
    [super dealloc];
}


- (void)clickOnPosition:(Position *)position {
    
    if (selectedTool == @"delete") {
        [mapEditor deleteObjectAtPosition:position];
    }
    else if (selectedTool == @"player") {
        [mapEditor addPlayer:position color:editorInformation.colorPlayer];
    }
    else {
        Objects *block = [[[RessourceManager sharedRessource].bitmapsAnimates objectForKey:[editorAction getSelectedObject]] copy];
        
        if ([editorAction.selectedObjectType isEqual:@"level0"]) {
            [mapEditor addGround:block position:position];
        }
        else if ([editorAction.selectedObjectType isEqual:@"level1"]) {
            [mapEditor addBlock:block position:position];
        }
    }
}


- (void)displayBlocks:(BOOL)display {
    [editorMapZone displayBlocks:display];
}


- (void)pauseAction {
    [self.view addSubview:pauseMenu.pauseMenuView];
    
    [UIView beginAnimations:@"DisplayPause" context:nil];
    [UIView setAnimationDuration:0.2];
    [UIView setAnimationBeginsFromCurrentState:YES];
    
    pauseMenu.pauseMenuView.alpha = 1.0;
    
    [UIView commitAnimations];
}


- (void)saveAction {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [mapEditor saveMapWithOwner:application.user];
    [application loadMaps];
}


- (void)resumeAction {    
    pauseMenu.pauseMenuView.alpha = 0.0;    
    [pauseMenu.pauseMenuView removeFromSuperview];
}


- (void)reset {
    [mapEditor reset];
    [editorMapZone needDisplay];
}


- (void)quitAction {
    MainMenuViewController *mainMenuViewController = [[MainMenuViewController alloc] initWithNibName:@"MainMenuViewController" bundle:nil];
    self.navigationController.navigationBarHidden = NO;
    [self.navigationController pushViewController:mainMenuViewController animated:NO];
    [mainMenuViewController release];
}

@end
