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
	GameMap * map;
	AVAudioPlayer *soundStart;
	AVAudioPlayer *soundMode;
	NSMutableDictionary * bitmaps;
	BOOL isStarted;
	BOOL displayGo;
	BOOL isEnded;
	RessourceManager * resource;
	Application *application;
	Player * winner;
	
    
}
@property (nonatomic, retain) NSMutableArray * players;
@property (nonatomic, retain) NSMutableDictionary * bombsPlanted;
@property (nonatomic, retain) NSMutableDictionary * bitmaps;
@property (nonatomic, retain) GameMap * map;
@property (nonatomic) BOOL isStarted;

- (id) initWithMapName:(NSString *)mapName;
- (void)dealloc;

- (void) initGame;
- (void) timerStartGame;
- (void) startGame;
- (void) endGame;
- (void) draw:(CGContextRef)context;
- (void) update;
- (void) loadSounds;
- (void) loadBitmaps;
- (void) disableSound;
- (Player *) getHumanPlayer;
- (void) timerDisplayGo;
- (void) displayGo;

- (void)plantingBombByPlayer:(Bomb *)bomb;
- (void)bombExplode:(Position *)position;

- (void)quitGame;

@end