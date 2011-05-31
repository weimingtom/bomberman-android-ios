//
//  GlobalGameViewControllerSingle.h
//  BomberKlob
//
//  Created by Kilian Coubo on 14/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
/** The `Player` class allows to represent a player. */
@class GameActionViewController, GameInformationViewController, GameViewControllerSingle, Engine, RessourceManager,PauseMenuGame, Position, Player;


@interface GlobalGameViewControllerSingle : UIViewController {
	Engine * engine;
	RessourceManager * resource;
    GameActionViewController * actionViewController;
	GameInformationViewController * informationViewController;
	GameViewControllerSingle * gameViewControllerSingle;
	PauseMenuGame *pauseMenu;
	NSThread * isGameEndedThread;

}

@property (nonatomic,retain) GameViewControllerSingle * gameViewControllerSingle;
@property (nonatomic,retain) GameInformationViewController * informationViewController;
@property (nonatomic,retain) GameActionViewController * actionViewController;
@property (nonatomic,retain) PauseMenuGame * pauseMenu;
@property (nonatomic,retain) Engine * engine;


- (id) initWithMapName:(NSString *)mapName;

- (void) pauseAction;
- (void) resumeAction;
- (void) quitAction;

- (void) plantingBomb;

- (void) startTimerIsGameEnded;
- (void) startTimerIsGameEndedThread;
- (void) isGameEnded;

- (BOOL) gameIsStarted;
- (void) updateMap;
- (Player *) getHumanPlayer;
- (NSInteger) nbPlayers;

@end
