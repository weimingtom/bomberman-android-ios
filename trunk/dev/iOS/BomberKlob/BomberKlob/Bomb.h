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
    NSInteger power;
	NSString * type;
	BOOL explode;
	NSMutableDictionary * animationExplosed;
}

@property (nonatomic) NSInteger power;

- (id) initWithImageName:(NSString *)anImageName position:(Position *)aPosition;
- (void) draw:(CGContextRef) context;
- (void) update;
- (BOOL) hasAnimationFinished;
- (void) destroy;

@end
