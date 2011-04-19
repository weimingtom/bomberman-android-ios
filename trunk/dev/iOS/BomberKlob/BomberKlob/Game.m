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
#import "Map.h"


@implementation Game

@synthesize players, map;

- (id) initWithMapName:(NSString *)mapName {
	self = [super init];
    
	if (self) {
//        map = [[Map alloc] init];
        Position *position;
        Player *player;
        NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
        map = [[Map alloc] initWithMapName:mapName];
        
        players = [[NSMutableArray alloc] initWithCapacity:[map.players count]];
        
        // TODO: Changer le tableau des couleurs en fonction de la couleur de joueur
        NSArray *colorsPlayers = [[NSArray alloc] initWithObjects:@"white", @"blue", @"red", @"black", nil];
        
        for (int i = 0; i < [map.players count]; i++) {
            position = [[Position alloc] initWithX:(((Position *) [map.players objectAtIndex:i]).x * tileSize) y:(((Position *) [map.players objectAtIndex:i]).y * tileSize)];
            player = [[Player alloc] initWithColor:[colorsPlayers objectAtIndex:i] position:position];
            
            [players addObject:player];
            
            [position release];
            [player release];
        }
        
        [colorsPlayers release];
	}
    
	return self;
}


- (void)dealloc {
    [players release];
    [map release];
    [super dealloc];
}


- (void) initGame{
	
}


- (void) startGame{
	
}


- (void) endGame{
	
}


- (void) draw{
	
}



@end
