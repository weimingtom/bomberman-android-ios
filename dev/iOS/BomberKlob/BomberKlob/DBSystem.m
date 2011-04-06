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

@synthesize volume;
@synthesize language;
@synthesize lastUser;


- (id)initWithVolume:(NSUInteger)aVolume language:(NSString *)aLanguage lastUser:(DBUser *)anUser {
    self = [super init]; 
    
    if (self) {
        dataBase = [DataBase instance];
        
        volume = aVolume;
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
    return [NSString stringWithFormat:@"Volume: %d\nLanguage: %@\nLast user: %d", volume, language, lastUser];
}


// TODO: A compléter...
- (void)saveInDataBase {
    
}


- (void)updateLastUser {    
    [dataBase update:@"System" set:[NSString stringWithFormat:@"lastUser = %d", lastUser.identifier] where:nil];
}

@end
