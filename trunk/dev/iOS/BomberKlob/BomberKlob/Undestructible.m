//
//  Undestructible.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Undestructible.h"


@implementation Undestructible

- (id)init {
    self = [super init];
    
    if (self) {
        
    }
    
    return self;
}


- (id)copyWithZone:(NSZone *)zone {
    Undestructible *copy = [super copyWithZone:zone];
    
    return copy;
}

@end
