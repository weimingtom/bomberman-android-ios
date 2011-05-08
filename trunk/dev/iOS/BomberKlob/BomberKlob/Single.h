//
//  Single.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Game.h"

@interface Single : Game {
	NSThread * timerThread;
	NSMutableString * time;
}
@property (nonatomic,retain) NSMutableString * time;

- (void) pauseGame;
- (void) startTimer;
- (void) runTimer;
- (void) updateTime;

@end
