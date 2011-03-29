//
//  Application.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Application.h"
#import "User.h"
#import "System.h"
#import "Map.h"


@implementation Application

@synthesize user;
@synthesize pseudos;
@synthesize maps;
@synthesize system;

- (id)init {
    self = [super init]; 
    
    if (self) {
        [self loadLastUser];
        [self loadPseudos];
        [self loadMaps];
        [self loadSystem];
    }
    
    return self;
}


- (void)dealloc {
    [user release];
    [pseudos release];
    [maps release];
    [system release];    
    [super dealloc];
}


- (NSString *)description {
    return [NSString stringWithFormat:@" User:\n%@\n\n Pseudos:\n%@\n Maps:\n%@\n\n System:\n%@\n", user, pseudos, maps, system];
}


- (void)loadLastUser {
    
    if (system.lastUser > 0) {
        self.user = [[User alloc] initWithId:system.lastUser]; 
    }
    else {
        self.user = nil;
    }
}


- (void)loadPseudos {
    self.pseudos = [NSArray arrayWithObjects:@"Joueur1", @"Joueur2", @"Joueur3", nil];
}


- (void)loadMaps {
    Map *map1 = [[Map alloc] initWithName:@"Map1" owner:0 officiel:YES];
    Map *map2 = [[Map alloc] initWithName:@"Map2" owner:0 officiel:YES];
    Map *map3 = [[Map alloc] initWithName:@"Map3" owner:0 officiel:YES];
    Map *map4 = [[Map alloc] initWithName:@"Map4" owner:0 officiel:YES];
    Map *map5 = [[Map alloc] initWithName:@"Map5" owner:0 officiel:YES];
    Map *map6 = [[Map alloc] initWithName:@"Map6" owner:2 officiel:NO];
    Map *map7 = [[Map alloc] initWithName:@"Map7" owner:1 officiel:NO];
    Map *map8 = [[Map alloc] initWithName:@"Map8" owner:3 officiel:NO];
    Map *map9 = [[Map alloc] initWithName:@"Map9" owner:1 officiel:NO];
    Map *map10 = [[Map alloc] initWithName:@"Map10" owner:1 officiel:NO];
    
    
    self.maps = [NSArray arrayWithObjects:map1, map2, map3, map4, map5, map6, map7, map8, map9, map10, nil];
    
    [map1 release];
    [map2 release];
    [map3 release];
    [map4 release];
    [map5 release];
    [map6 release];
    [map7 release];
    [map8 release];
    [map9 release];
    [map10 release];
}


- (void)loadSystem {
    self.system = [[System alloc] initWithVolume:100 language:@"en" lastUser:0];
}


- (BOOL)existPlayer {
    return ([pseudos count] > 0);
}


- (BOOL)pseudoAlreadyExists:(NSString *)pseudo {
    NSUInteger i = 0;
    
    while (i < [pseudos count] && ![pseudo isEqual:[pseudos objectAtIndex:i]]) {
        i++;
    }
    
    return !(i == [pseudos count]);
}
 

@end
