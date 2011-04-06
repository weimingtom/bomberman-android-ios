//
//  Animated.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Object.h"

@interface Animated : Object {
    NSDictionary * animations;
	NSString * currentAnimation;
	NSUInteger currentFrame;
	NSUInteger waitDelay;
}

@end
