//
//  Destructible.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Objects.h"

/** The `Destructible` class allows to represent a Destructible object. */
@interface Destructible : Objects {
    NSInteger life;
}

/** The life of the destructible object.*/
@property (nonatomic) NSInteger life;

@end
