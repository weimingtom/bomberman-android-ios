//
//  GlobalGameViewControllerSingle.m
//  BomberKlob
//
//  Created by Kilian Coubo on 14/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GlobalGameViewControllerSingle.h"
#import "GameInformationViewController.h"
#import "GameActionViewController.h"
#import "GameViewControllerSingle.h"
#import "Engine.h"
#import "RessourceManager.h"
#import "Game.h"
#import "Map.h"
#import "PauseMenuGame.h"
#import "MainMenuViewController.h"


@implementation GlobalGameViewControllerSingle

@synthesize gameViewControllerSingle,actionViewController,informationViewController, engine, pauseMenu;

- (id) initWithMapName:(NSString *)mapName {
	self = [super init];
	if (self){
		engine = [[Engine alloc] initWithGame:[[Game alloc] initWithMapName:mapName]];
		resource = [RessourceManager sharedRessource];
//		engine = [[Engine alloc] initWithGame:[[Game alloc] init]];
		CGRect dimension ;
		dimension = CGRectMake(0, resource.screenWidth-(engine.game.map.height*resource.tileSize),resource.tileSize*engine.game.map.width,resource.tileSize*engine.game.map.height);
		self.gameViewControllerSingle = [[GameViewControllerSingle alloc] initWithFrame:dimension Controller:self];
		
		dimension = CGRectMake(0, 0,resource.screenHeight,resource.screenWidth-(engine.game.map.height*resource.tileSize));
		self.informationViewController = [[GameInformationViewController alloc] initWithFrame:dimension Controller:self];

		
		dimension = CGRectMake(engine.game.map.width*resource.tileSize, resource.screenWidth-(engine.game.map.height*resource.tileSize),resource.screenHeight-(engine.game.map.width*resource.tileSize),engine.game.map.height*resource.tileSize)
		;
		self.actionViewController = [[GameActionViewController alloc] initWithFrame:dimension Controller:self];
		
		dimension = CGRectMake(0, 0, resource.screenHeight, resource.screenWidth);
		pauseMenu = [[PauseMenuGame alloc] initWithFrame:dimension controller:self];

		
		[self.view addSubview:gameViewControllerSingle.gameView];
		[self.view addSubview:informationViewController.informationView];
		[self.view addSubview:actionViewController.actionView];
		

		[informationViewController.informationView release];
		[actionViewController.actionView release];
		[gameViewControllerSingle.gameView release];
	}
	return self;
}

- (void)pauseAction{
	[self.view addSubview:pauseMenu.pauseMenuView];
	[gameViewControllerSingle.gameView stopThread];
}

- (void)resumeAction {
    [pauseMenu.pauseMenuView removeFromSuperview];
	[gameViewControllerSingle.gameView runThread];
}


- (void)quitAction {
    MainMenuViewController *mainMenuViewController = [[MainMenuViewController alloc] initWithNibName:@"MainMenuViewController" bundle:nil];
    self.navigationController.navigationBarHidden = NO;
    [self.navigationController pushViewController:mainMenuViewController animated:NO];
    [mainMenuViewController release];
}

@end
