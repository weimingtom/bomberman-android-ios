//
//  Map.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DBMap.h"
#import "User.h"


@implementation DBMap

@synthesize name;
@synthesize owner;
@synthesize official;

- (id)init {
    self = [super init]; 
    
    if (self) {
    }
    
    return self;
}


- (void)dealloc {
    [name release];
    [owner release];
    [super dealloc];
}


- (NSString *)description {
    return [NSString stringWithFormat:@"Name: %@\nId owner: %d\nOfficiel: %d", name, owner, official];
}


// TODO: A completer...
- (void)saveInDataBase {
    
}

@end