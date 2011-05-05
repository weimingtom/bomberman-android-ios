//
//  Single.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Single.h"


@implementation Single
@synthesize time;

- (id)init {
    self = [super init];
    if (self) {
        time = @"2:30";
		NSLog(@"hello");
    }
    return self;
}
- (void) pauseGame{
	
}

@end
