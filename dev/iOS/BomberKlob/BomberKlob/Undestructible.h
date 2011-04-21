//
//  Undestructible.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Object.h"

@interface Undestructible : Object {
    
}

- (id) initWithImageName:(NSString *)anImageName position:(Position *)aPosition animations:(NSDictionary *)anAnimations;

@end
