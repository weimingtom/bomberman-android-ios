//
//  Game.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Game.h"
#import "RessourceManager.h"
#import "Player.h"
#import "GameMap.h"
#import "Bomb.h"
#import <AVFoundation/AVFoundation.h>
#import "Application.h"
#import "DBSystem.h"
#import "BomberKlobAppDelegate.h"



@implementation Game

@synthesize players, map, bombsPlanted, bitmaps, isStarted, isEnded;

- (id) initWithMapName:(NSString *)mapName {
	self = [super init];
    
	if (self) {
		application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
		resource = [RessourceManager sharedRessource];
		[resource init];
		bombsPlanted = [[NSMutableDictionary alloc] init];
        Position *position;
        Player *player;
        NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
        map = [[GameMap alloc] initWithMapName:mapName];

        players = [[NSMutableArray alloc] initWithCapacity:[map.players count]];
        
        for (NSString *key in map.players) {
            position = [[Position alloc] initWithX:(((Position *) [map.players objectForKey:key]).x * tileSize) y:(((Position *) [map.players objectForKey:key]).y * tileSize)];
            player = [(Player *)[[RessourceManager sharedRessource].bitmapsPlayer objectForKey:key] copy];
            player.position = position;
            
            [players addObject:player];
            
            [position release];
            [player release];
        }
		[self loadSounds];
		[self loadBitmaps];
		isStarted = NO;
		displayGo = NO;
		isEnded = NO;
		return self;
	}
    
    return self;
}


- (void)dealloc {
    [players release];
    [map release];
    [super dealloc];
}


- (void) initGame{
	if (!application.system.mute){
		soundStart.volume = application.system.volume/100;
		soundMode.volume = application.system.volume/100;
	}
	else {
		soundStart.volume = 0;
		soundMode.volume = 0;
	}
	[soundStart play];
	NSThread * startThread = [[[NSThread alloc] initWithTarget:self selector:@selector(timerStartGame) object:nil]autorelease];
	[startThread start];
}

- (void) timerStartGame {
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	
	[[NSTimer scheduledTimerWithTimeInterval: 4 target: self selector: @selector(startGame) userInfo:self repeats: NO] retain];	
	[runLoop run];
	[pool release];
}

- (void) startGame{
	isStarted = YES;
	[soundMode play];
	displayGo = YES;
	NSThread * goThread = [[[NSThread alloc] initWithTarget:self selector:@selector(timerDisplayGo) object:nil]autorelease];
	[goThread start];
}

- (void) timerDisplayGo {
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	
	[[NSTimer scheduledTimerWithTimeInterval: 0.5 target: self selector: @selector(displayGo) userInfo:self repeats: NO] retain];	
	[runLoop run];
	[pool release];
}

- (void) displayGo {
	displayGo = NO;
}


- (void) endGame{
	isEnded = YES;
}


- (void) draw:(CGContextRef)context{
	@synchronized (self) {
		[map draw:context];	
		for (Player * player in players) {
			[player draw:context];
		}
		NSMutableDictionary * bombs = [bombsPlanted mutableCopy];
		for (Position * position in bombs) {
			[[bombs objectForKey:position] draw:context];
		}
		
		if (!isStarted){
			UIImage * image = [bitmaps objectForKey:@"ready"] ;
			int width = (map.width*resource.tileSize) * 0.75;
			int height = (map.height*resource.tileSize) * 0.75;
			[image drawInRect:CGRectMake(((map.width*resource.tileSize)/2)-(width/2), ((map.height*resource.tileSize)/2)-(width/2), width, height)];
		}
		if (displayGo) {
			UIImage * image = [bitmaps objectForKey:@"go"] ;
			int width = (map.width*resource.tileSize) * 0.75;
			int height = (map.height*resource.tileSize) * 0.75;
			[image drawInRect:CGRectMake(((map.width*resource.tileSize)/2)-(width/2), ((map.height*resource.tileSize)/2)-(width/2), width, height)];
		}
		if (isEnded) {
			UIImage * image;
			int width = (map.width*resource.tileSize) * 0.75;
			int height = (map.height*resource.tileSize) * 0.75;
			if (winner == [self getHumanPlayer]) {
				image = [bitmaps objectForKey:@"winner"] ;
				[image drawInRect:CGRectMake(((map.width*resource.tileSize)/2)-(width/2), ((map.height*resource.tileSize)/2)-(width/2), width, height)];
			}
			else if (winner != nil) {
				image = [bitmaps objectForKey:@"loser"] ;
				[image drawInRect:CGRectMake(((map.width*resource.tileSize)/2)-(width/2), ((map.height*resource.tileSize)/2)-(width/2), width, height)];
			}
			else {
				image = [bitmaps objectForKey:@"draw"] ;
				[image drawInRect:CGRectMake(((map.width*resource.tileSize)/2)-(width/2), ((map.height*resource.tileSize)/2)-(width/2), width, height)];
			}
		}
	}
}

- (void) update {
	[map update];
}

- (void) loadSounds {
	NSError *error;
	NSString *pathMenuSoundStart = [[NSBundle mainBundle] pathForResource:@"battle_start" ofType:@"mp3" inDirectory:@"Sounds"];
	NSString *pathMenuSoundMode = [[NSBundle mainBundle] pathForResource:@"battle_mode" ofType:@"mp3" inDirectory:@"Sounds"];
	
	if ([[NSFileManager defaultManager] fileExistsAtPath:pathMenuSoundStart]) {
		AVAudioPlayer * sound = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath:pathMenuSoundStart] error:&error];
		if (!sound) {
			NSLog(@"Error: %@", [error localizedDescription]);
		}
		else {
			[sound prepareToPlay];
			[sound setNumberOfLoops:0];
			sound.volume = 1;
			soundStart = sound;
		}
	}
	if ([[NSFileManager defaultManager] fileExistsAtPath:pathMenuSoundMode]) {
		AVAudioPlayer * sound = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath:pathMenuSoundMode] error:&error];
		if (!sound) {
			NSLog(@"Error: %@", [error localizedDescription]);
		}
		else {
			[sound prepareToPlay];
			[sound setNumberOfLoops:0];
			sound.volume = -1;
			soundMode = sound;
		}
	}
}

- (void) loadBitmaps{
	bitmaps = [[NSMutableDictionary alloc] init];
	UIImage * image = [UIImage imageNamed:@"draw.png"];
	[bitmaps setObject:image forKey:@"draw"];
	image = [UIImage imageNamed:@"go.png"];
	[bitmaps setObject:image forKey:@"go"];
	image = [UIImage imageNamed:@"loser.png"];
	[bitmaps setObject:image forKey:@"loser"];
	image = [UIImage imageNamed:@"winner.png"];
	[bitmaps setObject:image forKey:@"winner"];
	image = [UIImage imageNamed:@"ready.png"];
	[bitmaps setObject:image forKey:@"ready"];
}

- (void) disableSound {
	[soundMode stop];
	[soundStart stop];
	soundStart = nil;
	soundMode = nil;
}

- (Player *) getHumanPlayer {
	if ([players count] > 0) {
		return [players objectAtIndex:0];
	}
	else return nil;
}


- (void)plantingBombByPlayer:(Bomb *)bomb {
    [bombsPlanted setObject:bomb forKey:bomb.position];
    [map bombPlanted:bomb];
}


- (void)bombExplode:(id)position {
    [map bombExplode:(Bomb *)[bombsPlanted objectForKey:position]];
    [bombsPlanted removeObjectForKey:position];
}


@end
