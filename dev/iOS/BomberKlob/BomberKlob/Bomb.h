//
//  Bomb.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Destructible.h"


@interface Bomb : Destructible {
    NSInteger power;
	NSString * type;
	BOOL explode;
}

@property (nonatomic) NSInteger power;
@property (nonatomic) BOOL explode;
@property (nonatomic,retain) NSString * type;

- (id) initWithImageName:(NSString *)anImageName position:(Position *)aPosition;
- (void) draw:(CGContextRef) context;
- (void) update;
- (BOOL) hasAnimationFinished;
- (void) destroyable;

@end
