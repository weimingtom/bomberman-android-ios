//
//  Multiplayer.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Multiplayer.h"


@implementation Multiplayer
- (id) initWithName:(NSString*) nameValue {
	if([super init] == self){
		name = nameValue;
		adressServer = @"http://127.0.0.1/MyServletDeTueur";
	}
}

@end
