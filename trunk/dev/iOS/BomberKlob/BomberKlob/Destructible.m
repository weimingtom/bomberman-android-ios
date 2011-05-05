//
//  Destructible.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Destructible.h"

@implementation Destructible

@synthesize life;

- (id)init {
    self = [super init];
    
    if (self) {
        
    }
    
    return self;
}


- (id)copyWithZone:(NSZone *)zone {
    Destructible *copy = [super copyWithZone:zone];
    
    copy.life = life;
    
    return copy;
}

@end
