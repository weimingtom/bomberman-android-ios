//
//  Destructible.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Objects.h"

@interface Destructible : Objects {
    NSInteger life;
}

@property (nonatomic) NSInteger life;

- (id) init;

@end
