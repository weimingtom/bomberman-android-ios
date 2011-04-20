//
//  Undestructible.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Undestructible.h"


@implementation Undestructible

- (id) initWithImageName:(NSString *)anImageName position:(Position *)aPosition animations:(NSDictionary *)anAnimations{
	//NSLog(@"IMAGE NAME: %@ \n POSITION: %@ \n ANIMATIONS : %@",anImageName,aPosition,anAnimations);

	self = [super initWithImageName:anImageName position:aPosition animations:anAnimations];
	if (self) {
		//NSLog(@"IMAGE NAME: %@ \n POSITION: %@ \n ANIMATIONS : %@",anImageName,aPosition,anAnimations);
	}
	return self;
}




@end
