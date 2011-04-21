//
//  Application.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Application.h"
#import "DBUser.h"
#import "DBSystem.h"
#import "DBMap.h"
#import "DataBase.h"


@implementation Application

@synthesize user, pseudos, maps, system, playerMenu, playerButton;


- (id)init {
    self = [super init]; 
    
    if (self) {
        dataBase = [DataBase sharedDataBase];
        
        [self loadSystem];
        [self loadPseudos];
        [self loadMaps];
        [self loadAudio];        
        [self playSoundMenu];
    }
    
    return self;
}


- (void)dealloc {
    [user release];
    [pseudos release];
    [maps release];
    [system release]; 
    [playerMenu release];
    [playerButton release];
    [super dealloc];
}


- (NSString *)description {
    return [NSString stringWithFormat:@" User:\n%@\n\n Pseudos:\n%@\n Maps:\n%@\n\n System:\n%@\n", user, pseudos, maps, system];
}


- (void)loadPseudos {
    NSMutableArray *playersPseudos = [[NSMutableArray alloc] init];
    
    sqlite3_stmt *statement = [dataBase select:@"pseudo" from:@"AccountPlayer" where:nil];
    
    while (sqlite3_step(statement) == SQLITE_ROW) {
        [playersPseudos addObject:[NSString stringWithUTF8String:(char *) sqlite3_column_text(statement, 0)]]; 
    }
    
    sqlite3_finalize(statement);
    
    if ([playersPseudos count] > 0) {
        self.pseudos = playersPseudos;
    }
    
    [playersPseudos release];
}


- (void)loadSystem {
    NSUInteger volume;
    BOOL mute;
    NSString *language;
    
    sqlite3_stmt *statement = [dataBase select:@"*" from:@"System" where:nil];
    
    while (sqlite3_step(statement) == SQLITE_ROW) {
        volume = sqlite3_column_int(statement, 0); 
        mute = (BOOL) sqlite3_column_int(statement, 1);
        language = [[NSString alloc] initWithUTF8String:(char *) sqlite3_column_text(statement, 2)];
        
        
        if (sqlite3_column_int(statement, 3) > 0) {
            user = [[DBUser alloc] initWithId:((NSInteger) sqlite3_column_int(statement, 3))];
        }
    }
    
    system = [[DBSystem alloc] initWithVolume:volume mute:mute language:language lastUser:user];
    sqlite3_finalize(statement);
    
    [language release];
}


- (void)loadMaps {
    NSMutableArray *mapsTmp = [[NSMutableArray alloc] init];
    DBMap *map;
    
    sqlite3_stmt *statement = [dataBase select:@"*" from:@"Map" where:nil];
    
    while (sqlite3_step(statement) == SQLITE_ROW) {
        map = [[DBMap alloc] initWithName:[NSString stringWithUTF8String:(char *) sqlite3_column_text(statement, 0)] owner:sqlite3_column_int(statement, 1) official:sqlite3_column_int(statement, 1)];
        [mapsTmp addObject:map];
        [map release];
    }
    
    sqlite3_finalize(statement);
    
    if ([mapsTmp count] > 0)
        self.maps = mapsTmp;
    
    [mapsTmp release];
}


- (void)loadAudio {
	// Check for the file.
	NSError *error;
    float volume = system.volume;
	NSString *pathMenuSound = [[NSBundle mainBundle] pathForResource:@"menu" ofType:@"m4a"];
    NSString *pathButtonSound = [[NSBundle mainBundle] pathForResource:@"button" ofType:@"wav"];    
    
	if ([[NSFileManager defaultManager] fileExistsAtPath:pathMenuSound]) {
        // Initialize the player
        self.playerMenu = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath:pathMenuSound] error:&error];
        self.playerButton = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath:pathButtonSound] error:&error];
        
        if (!playerMenu || !playerButton) 
            NSLog(@"Error: %@", [error localizedDescription]);
        
        // Prepare the player and set the loops to, basically, unlimited
        [self.playerMenu prepareToPlay];
        [self.playerMenu setNumberOfLoops:-1];
        self.playerMenu.volume = (volume / 100);
        
        [self.playerButton prepareToPlay];
        self.playerButton.volume = (volume / 100);
    }
}


- (BOOL)existPlayer {
    return (user != nil);
}


- (BOOL)pseudoAlreadyUsed:(NSString *)pseudo {
    NSUInteger i = 0;
    
    while (i < [pseudos count] && ![pseudo isEqual:[pseudos objectAtIndex:i]]) {
        i++;
    }
    
    return !(i == [pseudos count]);
}


- (void)addMap:(DBMap *)map {
    NSMutableArray *newMaps = [[NSMutableArray alloc] initWithArray:maps];
    [newMaps addObject:map];
    [maps release];
    
    maps = [[NSArray alloc] initWithArray:newMaps];
    [newMaps release];
}


- (void)playSoundMenu {
    if (system.volume > 0 && !system.mute)
        [self.playerMenu play];
}


- (void)playSoundButton {
    if (system.volume > 0 && !system.mute)
        [self.playerButton play];
}


- (void)modifyVolume:(NSInteger)newVolume {
    NSInteger oldVolume = system.volume;
    
    if (newVolume != oldVolume) {
        self.playerMenu.volume = (newVolume / 100.0);
        self.playerButton.volume = (newVolume / 100.0);
    }
    
    if (newVolume == 0) 
        [playerMenu pause];
    
    if (oldVolume == 0)
        [playerMenu play];
}


- (void)modifyMute:(BOOL)newMute {
    
    if (newMute)
        [playerMenu pause];
    else if (system.volume > 0) 
        [playerMenu play];
}


- (NSArray *)unofficialMaps {
    DBMap *map;
    NSMutableArray *unofficialMaps = [[NSMutableArray alloc] init];
    
    for (int i = 0; i < [maps count]; i++) {
        map = [maps objectAtIndex:i];
        
        if (map.official == 0) {
            [unofficialMaps addObject:map];
        }
    }
    
    return [unofficialMaps autorelease];
}


- (BOOL)hasUnofficialMaps {
    DBMap *map;
    
    for (int i = 0; i < [maps count]; i++) {
        map = [maps objectAtIndex:i];	
        
        if (map.official == 0) {
            return YES;
        }
    }
    
    return NO;
}


- (void)setUser:(DBUser *)value {
    [value retain];
    [user release];
    user = value;
    system.lastUser = value;
}

@end
