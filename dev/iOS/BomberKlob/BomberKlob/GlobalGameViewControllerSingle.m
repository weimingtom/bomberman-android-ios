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

@synthesize gameViewController,actionViewController,informationViewController;

- (id) initWithMapName:(NSString *)mapName {
	self = [super init];
	
	if (self){
		engine = [[Engine alloc] initWithGame:[[Game alloc] initWithMapName:mapName]];
		resource = [RessourceManager sharedRessource];
		CGRect dimension ;
		dimension = CGRectMake(0, resource.screenWidth-(engine.game.map.height*resource.tileSize),resource.tileSize*engine.game.map.width,resource.tileSize*engine.game.map.height);
		self.gameViewController = [[GameViewControllerSingle alloc] initWithFrame:dimension Engine:engine];
		
		dimension = CGRectMake(0, 0,resource.screenHeight,resource.screenWidth-(engine.game.map.height*resource.tileSize));
		self.informationViewController = [[GameInformationViewController alloc] initWithFrame:dimension Engine:engine];

		
		dimension = CGRectMake(engine.game.map.width*resource.tileSize, resource.screenWidth-(engine.game.map.height*resource.tileSize),resource.screenHeight-(engine.game.map.width*resource.tileSize),engine.game.map.height*resource.tileSize)
		;
		self.actionViewController = [[GameActionViewController alloc] initWithFrame:dimension Engine:engine];

		
		[self.view addSubview:gameViewController.gameView];
		[self.view addSubview:informationViewController.informationView];
		[self.view addSubview:actionViewController.actionView];
		

		[informationViewController.informationView release];
		[actionViewController.actionView release];
		[gameViewController.gameView release];

	}
	return self;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	CGPoint pt = [[touches anyObject] locationInView:self.view];
//	NSLog(@" Global pt.X : %f , pt.Y : %f",pt.x,pt.y);
	CGRect dimension = gameViewController.dimension;
	
	if (pt.x < dimension.size.width && pt.x > 0 && pt.y < dimension.size.height+dimension.origin.y && pt.y > dimension.origin.y){
		[gameViewController touchesBegan:pt];
	}
}
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	CGPoint pt = [[touches anyObject] locationInView:self.view];
	CGRect dimension = gameViewController.dimension;
	if (pt.x < dimension.size.width && pt.x > 0 && pt.y < dimension.size.height+dimension.origin.y && pt.y > dimension.origin.y){
		[gameViewController touchesMoved:pt];
	}
}
- (void) touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event{
	CGPoint pt = [[touches anyObject] locationInView:self.view];
	CGRect dimension = gameViewController.dimension;
	if (pt.x < dimension.size.width && pt.x > 0 && pt.y < dimension.size.height+dimension.origin.y && pt.y > dimension.origin.y){
		[gameViewController touchesCancelled:pt];
	}
}
- (void) touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event{
	CGPoint pt = [[touches anyObject] locationInView:self.view];
	CGRect dimension = gameViewController.dimension;
	if (pt.x < dimension.size.width && pt.x > 0 && pt.y < dimension.size.height+dimension.origin.y && pt.y > dimension.origin.y){
		[gameViewController touchesEnded:pt];
	}
}

@end
