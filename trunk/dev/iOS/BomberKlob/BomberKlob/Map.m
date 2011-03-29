//
//  Map.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Map.h"


@implementation Map

- (id)initWithName:(NSString *)aName owner:(NSInteger)anOwner officiel:(BOOL)anOfficiel {
    self = [super init]; 
    
    if (self) {
        name = aName;
        owner = anOwner;
        officiel = anOfficiel;
    }
    
    return self;
}


- (void)dealloc {
    [name release];
    [super dealloc];
}


- (NSString *)description {
    return [NSString stringWithFormat:@"Name: %@\nId owner: %d\nOfficiel: %d", name, owner, officiel];
}

@end
