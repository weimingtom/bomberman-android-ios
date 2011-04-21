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


@implementation GlobalGameViewControllerSingle

@synthesize gameViewControllerSingle,actionViewController,informationViewController, engine;

- (id) initWithMapName:(NSString *)mapName {
	self = [super init];
	
	
	if (self){
		engine = [[Engine alloc] initWithGame:[[Game alloc] initWithMapName:mapName]];
		resource = [RessourceManager sharedRessource];
		CGRect dimension ;
		dimension = CGRectMake(0, resource.screenWidth-(engine.game.map.height*resource.tileSize),resource.tileSize*engine.game.map.width,resource.tileSize*engine.game.map.height);
		self.gameViewControllerSingle = [[GameViewControllerSingle alloc] initWithFrame:dimension Controller:self];
		
		dimension = CGRectMake(0, 0,resource.screenHeight,resource.screenWidth-(engine.game.map.height*resource.tileSize));
		self.informationViewController = [[GameInformationViewController alloc] initWithFrame:dimension Controller:self];

		
		dimension = CGRectMake(engine.game.map.width*resource.tileSize, resource.screenWidth-(engine.game.map.height*resource.tileSize),resource.screenHeight-(engine.game.map.width*resource.tileSize),engine.game.map.height*resource.tileSize)
		;
		self.actionViewController = [[GameActionViewController alloc] initWithFrame:dimension Controller:self];

		
		[self.view addSubview:gameViewControllerSingle.gameView];
		[self.view addSubview:informationViewController.informationView];
		[self.view addSubview:actionViewController.actionView];
		

		[informationViewController.informationView release];
		[actionViewController.actionView release];
		[gameViewControllerSingle.gameView release];

	}
	return self;
}

- (void) resumeAction{
	
}

@end
