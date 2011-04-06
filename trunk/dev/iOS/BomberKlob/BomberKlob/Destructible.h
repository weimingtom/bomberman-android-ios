//
//  Destructible.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Animated.h"

@interface Destructible : Animated {
    NSUInteger life;
}

@property (nonatomic) NSUInteger life;

@end
