//
//  GlobalGameViewControllerSingle.h
//  BomberKlob
//
//  Created by Kilian Coubo on 14/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class GameActionViewController, GameInformationViewController, GameViewControllerSingle, Engine, RessourceManager;


@interface GlobalGameViewControllerSingle : UIViewController {
	Engine * engine;
	RessourceManager * resource;
	
    GameActionViewController * actionViewController;
	GameInformationViewController * informationViewController;
	GameViewControllerSingle * gameViewControllerSingle;
}

@property (nonatomic,retain) GameViewControllerSingle * gameViewControllerSingle;
@property (nonatomic,retain) GameInformationViewController * informationViewController;
@property (nonatomic,retain) GameActionViewController * actionViewController;
@property (nonatomic,retain) Engine * engine;

- (id) initWithMapName:(NSString *)mapName;
- (void) resumeAction;

@end
