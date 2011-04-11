//
//  Object.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Object.h"
#import "Position.h"
#import "RessourceManager.h"


@implementation Object

@synthesize imageName,hit, level, fireWall, position;

- (id) init{
	self = [super init];
	
	if (self) {
		ressource = [RessourceManager sharedRessource];
		position = [[Position alloc] init];
	}
	
	return self;
}

- (void) resize{
	
}

- (void) update{
	
}

- (void) hasAnimationFinished{
	
	
}

- (void) destroy{
	
	
}

- (NSString *)description{
	NSString * desc = [NSString stringWithFormat:@"ImageName : %@ hit : %d, Level : %d, FireWall : %d, X :  %d  Y : %d",imageName, hit, level, fireWall, position.x, position.y];
	return desc;

	}

@end
