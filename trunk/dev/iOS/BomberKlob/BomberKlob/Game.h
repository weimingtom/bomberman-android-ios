//
//  Game.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class GameMap, Player, Bomb, Position, AVAudioPlayer;


@interface Game : NSObject {
	NSMutableArray * players;
	NSMutableDictionary * bombsPlanted;
	GameMap * map;
	AVAudioPlayer *soundStart;
	AVAudioPlayer *soundMode;
	NSMutableDictionary * bitmaps;
	
    
}
@property (nonatomic, retain) NSMutableArray * players;
@property (nonatomic, retain) NSMutableDictionary * bombsPlanted;
@property (nonatomic, retain) NSMutableDictionary * bitmaps;
@property (nonatomic, retain) GameMap * map;

- (id) initWithMapName:(NSString *)mapName;
- (void)dealloc;

- (void) initGame;
- (void) startGame;
- (void) endGame;
- (void) draw:(CGContextRef)context;
- (void) update;
- (void) loadSounds;
- (void) loadBitmaps;
- (BOOL) isStartSoundFinished;

- (void)plantingBombByPlayer:(Bomb *)bomb;
- (void)bombExplode:(Position *)position;

@end