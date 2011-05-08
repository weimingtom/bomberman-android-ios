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



@implementation Game

@synthesize players, map, bombsPlanted, bitmaps;

- (id) initWithMapName:(NSString *)mapName {
	self = [super init];
    
	if (self) {
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
//		[self loadSounds];
		[self loadBitmaps];
	}
    
    return self;
}

//- (id) init {
//	self = [super init];
//    
//	if (self) {
//		bombsPlanted = [[NSMutableDictionary alloc] init];
//		RessourceManager * resource = [RessourceManager sharedRessource];
//        Position *position;
//        Player *player;
//        NSInteger tileSize = resource.tileSize;
//        map = [[GameMap alloc] init];
//        
//        players = [[NSMutableArray alloc] initWithCapacity:[map.players count]];
//        
//        // TODO: Changer le tableau des couleurs en fonction de la couleur de joueur
//        NSArray *colorsPlayers = [[NSArray alloc] initWithObjects:@"white", @"blue", @"red", @"black", nil];
//        
//        for (int i = 0; i < [map.players count]; i++) {
//            position = [[Position alloc] initWithX:(((Position *) [map.players objectAtIndex:i]).x * tileSize) y:(((Position *) [map.players objectAtIndex:i]).y * tileSize)];
//			player = [(Player *)[resource.bitmapsPlayer objectForKey:[colorsPlayers objectAtIndex:i]] copy];
//			player.position = position;            
//            [players addObject:player];
//            
//            [position release];
//            [player release];
//        }
//        
//        [colorsPlayers release];
////		[self loadSounds];
//		[self loadBitmaps];
//	}
//    
//	return self;
//}


- (void)dealloc {
    [players release];
    [map release];
    [super dealloc];
}


- (void) initGame{
	
}


- (void) startGame{
	[soundStart play];
}


- (void) endGame{
	
}


- (void) draw:(CGContextRef)context{
	@synchronized (self) {
		[map draw:context];	
        
		NSMutableDictionary * bombs = [bombsPlanted mutableCopy];
		for (Position * position in bombs) {
			[[bombs objectForKey:position] draw:context];
		}
        
		for (Player * player in players) {
			[player draw:context];
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

- (BOOL) isStartSoundFinished {
	if ([soundStart isPlaying]) {
		return false;
	}
	else{
		[soundMode play];
		return true;
	}
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
