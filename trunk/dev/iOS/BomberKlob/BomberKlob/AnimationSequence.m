//
//  AnimationSequence.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AnimationSequence.h"


@implementation AnimationSequence

@synthesize sequences, canLoop, name, delayNextFrame,sound;

- (id) init{
	self = [super init];
	if(self){
		sequences = [[NSMutableArray alloc] init];
	}
	return self;
}

- (id) initWithLoop:(BOOL)canLoopValue{
	self = [super init];
	if(self){
		sequences = [[NSMutableArray alloc] init];
		canLoop = canLoopValue;
	}
	return self;
}


- (void)dealloc {
    [sequences release];
    [name release];
    [super dealloc];
}


- (void) addImageSequence:(UIImage *) imageValue{
	[sequences addObject:imageValue];
}

- (NSString *)description{
	NSString * desc = [NSString stringWithFormat:@"Sequence Name : %@ \n canLoop : %d  \n Sequence : %@",name, canLoop, sequences];
	return desc;
}

- (void) playSound {
	[sound play];
}


- (BOOL)isUnanimated {
    
    if ([sequences count] == 0) {
        return YES;
    }
    
    return NO;
}

@end
