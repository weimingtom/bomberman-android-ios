//
//  DeathMatch.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameType.h"

@interface DeathMatch : GameType {
	NSTimer *timer;
	NSInteger time;
}

- (id) initWithGame:(Game *)gameValue;

@end
