//
//  System.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DBSystem.h"
#import "DBUser.h"
#import "DataBase.h"


@implementation DBSystem

@synthesize volume, mute, language, lastUser;


- (id)initWithVolume:(NSUInteger)aVolume mute:(BOOL)aMute language:(NSString *)aLanguage lastUser:(DBUser *)anUser {
    self = [super init]; 
    
    if (self) {
        dataBase = [DataBase sharedDataBase];
        
        self.volume = aVolume;
        self.mute = aMute;
        self.language = aLanguage;
        self.lastUser = anUser;
    }
    
    return self;
}


- (void)dealloc {
    [language release];
    [lastUser release];
    [super dealloc];
}


- (NSString *)description {
    return [NSString stringWithFormat:@"Volume: %d\nMute: %d\nLanguage: %@\nLast user: %@", volume, mute, language, lastUser];
}


- (void)updateVolume {    
    [dataBase update:@"System" set:[NSString stringWithFormat:@"volume = %d", volume] where:nil];
}


- (void)updateMute {
    [dataBase update:@"System" set:[NSString stringWithFormat:@"mute = %d", mute] where:nil];
}


- (void)updateLanguage {
    [dataBase update:@"System" set:[NSString stringWithFormat:@"language = '%@'", language] where:nil];
}


- (void)updateLastUser {    
    [dataBase update:@"System" set:[NSString stringWithFormat:@"lastUser = %d", lastUser.identifier] where:nil];
}


- (void)setVolume:(NSUInteger)value {
    volume = value;
    [self updateVolume];
}


- (void)setMute:(BOOL)value {
    mute = value;
    [self updateMute];
}


- (void)setLanguage:(NSString *)value {
    [value retain];
    [language release];
    language = value;
    [self updateLanguage];
}


- (void)setLastUser:(DBUser *)value {
    [value retain];
    [lastUser release];
    lastUser = value;
    [self updateLastUser];
}

@end
