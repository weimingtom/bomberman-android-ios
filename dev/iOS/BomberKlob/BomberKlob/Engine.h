//
//  Engine.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Game.h"

@interface Engine : NSObject {
    Game * game;
}
@property (nonatomic, retain) Game * game;

- (void) moveTop;
- (void) moveDown;
- (void) moveLeft;
- (void) moveRight;

@end
