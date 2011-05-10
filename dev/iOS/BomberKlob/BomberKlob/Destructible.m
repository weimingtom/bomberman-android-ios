//
//  Destructible.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Destructible.h"
#import "RessourceManager.h"

@implementation Destructible

@synthesize life;

- (id)init {
    self = [super init];
    
    if (self) {
        
    }
    
    return self;
}


- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue {
	self = [super initWithImageName:imageNameValue position:positionValue];
	
	if (self) {
        Destructible *copy = [(Destructible *)[ressource.bitmapsAnimates objectForKey:imageNameValue] copy];
        
        self.life = copy.life;
	}
	
	return self;
}


- (id)copyWithZone:(NSZone *)zone {
    Destructible *copy = [super copyWithZone:zone];
    
    copy.life = life;
    
    return copy;
}

@end
