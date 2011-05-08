//
//  Single.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Single.h"
#import "Player.h"


@implementation Single

@synthesize time;

- (id)init {
    self = [super init];
    
    if (self) {
        time = @"2:30";
		[self startTimer];
    }
    
    return self;
}

- (id)initWithMapName:(NSString *)mapName{
	self = [super initWithMapName:mapName];
    if (self) {
        time = @"1:30";
		[self startTimer];
    }
    return self;
}

- (void) pauseGame{
	
}

- (void) startTimer{
	timerThread = [[[NSThread alloc] initWithTarget:self selector:@selector(runTimer) object:nil]autorelease]; 
	[timerThread start];
}

- (void) runTimer {
	while (!isStarted) {}
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	
	[[NSTimer scheduledTimerWithTimeInterval: 1 target: self selector: @selector(updateTime) userInfo:self repeats: YES] retain];
	[runLoop run];
	[pool release];
}

- (void) updateTime {
	int m,s;
	sscanf([time UTF8String],"%d:%d", &m,&s);
	s--;
	if (s < 0) {
		s = 59;
		m--;
		if (m < 0) {
			m = 0;
			s = 0;
			[self endGame];
		}
	}
	if (s < 10) 
		time = [NSString stringWithFormat:@"%d:0%d",m,s];
	else
		time = [NSString stringWithFormat:@"%d:%d",m,s];
	[time retain];
}


- (void)endGame {
	[super endGame];
	NSInteger maxLife = [self getHumanPlayer].lifeNumber;
	winner = [self getHumanPlayer];
	for (Player * player in players) {
		if (player.lifeNumber > maxLife) {
			maxLife = player.lifeNumber;
			winner = player;
		}
	}
	for (Player * player in players) {
		if (player.lifeNumber == maxLife && winner != player && winner == [self getHumanPlayer]) {
			winner = nil;
			break;
		}
	}
}
@end
