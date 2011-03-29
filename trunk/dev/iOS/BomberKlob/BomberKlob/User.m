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
        pseudo = @"Klob";
        userName = nil;
        password = nil;
        connectionAuto = NO;
        rembemberPassword = NO;
        color = @"red";
        menuPosition = @"right";
        gameWon = 0;
        gameLost = 0;
    }
    
    return self;
}


- (id)initWithId:(NSInteger)identifier {
    return nil;
}


- (void)dealloc {
    [pseudo release];
    [userName release];
    [password release];
    [color release];
    [menuPosition release];
    [super dealloc];
}

@end
