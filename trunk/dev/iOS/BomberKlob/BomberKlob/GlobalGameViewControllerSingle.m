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
#import "Single.h"
#import "GameMap.h"
#import "Bomb.h"


@implementation GlobalGameViewControllerSingle

@synthesize gameViewControllerSingle,actionViewController,informationViewController, engine, pauseMenu;

- (id) initWithMapName:(NSString *)mapName {
	self = [super init];
	if (self){
		engine = [[Engine alloc] initWithGame:[[Single alloc] initWithMapName:mapName]];
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
		[self startTimerIsGameEnded];
	}
	return self;
}

- (void)pauseAction{
	[self.view addSubview:pauseMenu.pauseMenuView];
	[gameViewControllerSingle.gameView pauseThread:YES];
	[engine pauseThread:YES];
}

- (void)resumeAction {
    [pauseMenu.pauseMenuView removeFromSuperview];
	[gameViewControllerSingle.gameView pauseThread:NO];
	[engine pauseThread:NO];
}


- (void)quitAction {
	[engine.game disableSound];
	[gameViewControllerSingle.gameView stopThread];
	[engine stopThread];
    MainMenuViewController *mainMenuViewController = [[MainMenuViewController alloc] initWithNibName:@"MainMenuViewController" bundle:nil];
    self.navigationController.navigationBarHidden = NO;
    [self.navigationController pushViewController:mainMenuViewController animated:NO];
    [mainMenuViewController release];
}


- (void)plantingBomb {
    Bomb *bomb = [[[resource.bitmapsBombs objectForKey:@"normal"] copy] autorelease];
    
    [engine plantingBomb:bomb];
}

- (void) startTimerIsGameEnded{
	NSThread * isGameEndedThread = [[[NSThread alloc] initWithTarget:self selector:@selector(startTimerIsGameEndedThread) object:nil]autorelease];
	[isGameEndedThread start];
}

- (void) startTimerIsGameEndedThread {
	
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	
	[[NSTimer scheduledTimerWithTimeInterval:1 target: self selector: @selector(isGameEnded) userInfo:self repeats: YES] retain];	
	[runLoop run];
	[pool release];
}

- (void) isGameEnded {
	if (engine.game.isEnded) {
		[self pauseAction];
		sleep(5);
		[self quitAction];
	}
}

@end
