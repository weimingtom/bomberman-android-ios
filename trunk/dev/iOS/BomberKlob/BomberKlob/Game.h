//
//  Game.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Map, Player, AVAudioPlayer, RessourceManager,Application, GameMap,Bomb,Position;


@interface Game : NSObject {
	NSMutableArray * players;
	NSMutableDictionary * bombsPlanted;
	AVAudioPlayer *soundStart;
	AVAudioPlayer *soundMode;
	NSMutableDictionary * bitmaps;
	BOOL isStarted;
	BOOL displayGo;
	BOOL isEnded;
	RessourceManager * resource;
	Application *application;
	Player * winner;
	GameMap * map;
}
@property (nonatomic, retain) NSMutableArray * players;
@property (nonatomic, retain) NSMutableDictionary * bombsPlanted;
@property (nonatomic, retain) NSMutableDictionary * bitmaps;
@property (nonatomic, retain) GameMap * map;
@property (nonatomic) BOOL isStarted;
@property (nonatomic) BOOL isEnded;

///------------------------
/// @name Init the Game
///------------------------

- (void) initGame;
- (id) initWithMapName:(NSString *)mapName;

///------------------------
/// @name Managing the Game
///------------------------

- (void) timerStartGame;
- (void) startGame;
- (void) timerDisplayGo;
- (void) stopDisplayGo;
- (void) endGame;
- (void) quitGame;
- (void) pauseGame:(BOOL)enable;


///------------------------
/// @name Draw the Game
///------------------------


- (void) draw:(CGContextRef)context;

///------------------------
/// @name Managing the game's sound and bitmaps
///------------------------

- (void) loadSounds;
- (void) loadBitmaps;
- (void) disableSound;

///------------------------
/// @name Managing the Human player
///------------------------

- (Player *) getHumanPlayer;

///------------------------
/// @name Managing the Bombs
///------------------------

- (void)plantingBombByPlayer:(Bomb *)bomb;
- (void)bombExplode:(Position *)position;

- (void) updateMap;

- (NSInteger) nbPlayers;

@end