//
//  Object.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Position;

@interface Object : NSObject {
    NSString * imageName;
	BOOL hit;
	NSInteger level;
	BOOL fireWall;
	Position * position;
}

- (void) resize;

- (void) update;

- (void) hasAnimationFinished;

- (void) destroy;

@end
