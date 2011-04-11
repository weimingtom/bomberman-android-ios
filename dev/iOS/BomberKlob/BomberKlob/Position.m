//
//  Position.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Position.h"


@implementation Position

@synthesize x, y;

- (id) init{
	self = [super init];
	
	return self;
}

- (id) initWithXAndY:(NSUInteger)xValue:(NSUInteger) yValue{
	self = [super init];
	if (self){
		x = xValue;
		y = yValue;
	}
	return self;
}

- (NSString *)description{
	NSString * desc = [NSString stringWithFormat:@"X : %d \nY : %d",x,y];
	return desc;
	}

@end
