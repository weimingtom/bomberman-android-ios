//
//  DeathMatch.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DeathMatch.h"


@implementation DeathMatch

- (id) initWithGame:(Game *)gameValue{
	self = [super initWithGame:gameValue];
	if (self){
		time = 60;
	}
	return self;
}

@end
