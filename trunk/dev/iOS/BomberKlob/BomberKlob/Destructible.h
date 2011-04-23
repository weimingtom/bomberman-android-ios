//
//  Destructible.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Object.h"

@interface Destructible : Object {
    NSInteger life;
}

@property (nonatomic) NSInteger life;

- (id) init;

@end
