//
//  User.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "User.h"


@implementation User

@synthesize pseudo;


- (id)init {
    self = [super init];
    
    if (self) {
        pseudo = @"Benjamin";
    }
    
    return self;
}


- (id)initWithId:(NSInteger)identifier {
    return nil;
}


- (void)dealloc {
    [super dealloc];
}

@end
