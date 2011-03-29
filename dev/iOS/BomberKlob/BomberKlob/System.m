//
//  System.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "System.h"


@implementation System

@synthesize volume;
@synthesize language;
@synthesize lastUser;

- (id)initWithVolume:(NSInteger)aVolume language:(NSString *)aLanguage lastUser:(NSInteger)alastUser {
    self = [super init]; 
    
    if (self) {
        volume = aVolume;
        language = aLanguage;
        lastUser = alastUser;
    }
    
    return self;
}


- (void)dealloc {
    [language release];
    [super dealloc];
}


- (NSString *)description {
    return [NSString stringWithFormat:@"Volume: %d\nLanguage: %@\nLast user: %d", volume, language, lastUser];
}

@end
