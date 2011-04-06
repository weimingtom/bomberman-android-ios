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

@synthesize user;
@synthesize pseudos;
@synthesize maps;
@synthesize system;

- (id)init {
    self = [super init]; 
    
    if (self) {
        dataBase = [DataBase instance];
        
        [self loadSystem];
        [self loadPseudos];
        [self loadMaps];
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
        language = [NSString stringWithUTF8String:(char *) sqlite3_column_text(statement, 2)];
        
        
        if (sqlite3_column_int(statement, 3) > 0) {
            user = [[DBUser alloc] initWithId:((NSInteger) sqlite3_column_int(statement, 3))];
        }
    }
  
    system = [[DBSystem alloc] initWithVolume:volume mute:mute language:language lastUser:user];
    sqlite3_finalize(statement);
}


// TODO: A compléter...
- (void)loadMaps {
    
}


// TODO: A vérifier...
- (BOOL)existPlayer {
    return (user != nil);
}


- (BOOL)pseudoAlreadyExists:(NSString *)pseudo {
    NSUInteger i = 0;
    
    while (i < [pseudos count] && ![pseudo isEqual:[pseudos objectAtIndex:i]]) {
        i++;
    }
    
    return !(i == [pseudos count]);
}
 

- (void)setUser:(DBUser *)value {
    [value retain];
    [user release];
    user = value;
    system.lastUser = value;
}

@end
