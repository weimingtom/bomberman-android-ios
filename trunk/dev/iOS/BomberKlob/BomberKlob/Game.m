//
//  Game.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Game.h"
#import "RessourceManager.h"


@implementation Game

@synthesize players, map;

- (id) init {
	self = [super init];
    
	if (self) {
//        map = [[Map alloc] init];
        Position *position;
        NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
        map = [[Map alloc] initWithNameMap:@"Default"];
        
        players = [[NSMutableArray alloc] initWithCapacity:[map.players count]];

        position = [[Position alloc] initWithX:(((Position *) [map.players objectAtIndex:0]).x * tileSize) y:(((Position *) [map.players objectAtIndex:0]).y * tileSize)];
        [players addObject:[[Player alloc] initWithColor:@"black" position:position]];
        
        position = [[Position alloc] initWithX:(((Position *) [map.players objectAtIndex:1]).x * tileSize) y:(((Position *) [map.players objectAtIndex:1]).y * tileSize)];
        [players addObject:[[Player alloc] initWithColor:@"red" position:position]];
    
        if ([map.players count] > 2) {
            position = [[Position alloc] initWithX:(((Position *) [map.players objectAtIndex:2]).x * tileSize) y:(((Position *) [map.players objectAtIndex:2]).y * tileSize)];
            [players addObject:[[Player alloc] initWithColor:@"white" position:position]];
            
            if ([map.players count] == 4) {
                position = [[Position alloc] initWithX:(((Position *) [map.players objectAtIndex:3]).x * tileSize) y:(((Position *) [map.players objectAtIndex:3]).y * tileSize)];
                [players addObject:[[Player alloc] initWithColor:@"blue" position:position]];
            }
        }
        
        [position release];
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
