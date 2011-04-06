//
//  Bomb.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Animated.h"


@interface Bomb : Animated {
    NSUInteger power;
	NSUInteger time;
	NSString * type;
}

@end
