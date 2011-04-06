//
//  Game.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Player.h"
#import "Map.h"


@interface Game : NSObject {
	NSMutableArray * players;
	Map * map;

}

- (void) initGame;
- (void) startGame;
- (void) endGame;
- (void) draw;

@end
