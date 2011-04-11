//
//  GameType.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameType.h"


@implementation GameType

- (id) initWithGame:(Game *) gameValue{
	self = [super init];
	if (self){
		gameType = gameValue;
	}
	return self;
}

@end
