//
//  Application.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Application.h"
#import "User.h"


@implementation Application

@synthesize user;
@synthesize pseudos;


- (id)init {
    self = [super init];
    
    if (self) {
        user = [[User alloc] init];
        pseudos = [[NSArray alloc] initWithObjects:@"Benjamin", @"Sentenza", nil];
    }
    
    return self;
}


- (void)dealloc {
    [user release];
    [pseudos release];
    [super dealloc];
}


- (BOOL)pseudoAlreadyExists:(NSString *)pseudo {
    NSUInteger i = 0;
    
    while (i < [pseudos count] && ![pseudo isEqual:[pseudos objectAtIndex:i]]) {
        i++;
    }
    
    return !(i == [pseudos count]);
}

@end
