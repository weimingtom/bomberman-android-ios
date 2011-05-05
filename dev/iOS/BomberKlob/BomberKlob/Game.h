//
//  Game.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Map, Player, AVAudioPlayer;


@interface Game : NSObject {
	NSMutableArray * players;
	NSMutableDictionary * bombsPlanted;
	Map * map;
	AVAudioPlayer *soundStart;
	AVAudioPlayer *soundMode;

	
    
}
@property (nonatomic, retain) NSMutableArray * players;
@property (nonatomic, retain) NSMutableDictionary * bombsPlanted;
@property (nonatomic, retain) Map * map;

- (id) initWithMapName:(NSString *)mapName;
- (void)dealloc;

- (void) initGame;
- (void) startGame;
- (void) endGame;
- (void) draw:(CGContextRef)context;
- (void) update;
- (void) loadSounds;
- (BOOL) isStartSoundFinished;

@end