//
//  Engine.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Game, RessourceManager;

@interface Engine : NSObject {
    Game * game;
	RessourceManager * resource;
}
@property (nonatomic, retain) Game * game;

- (id) initWithGame:(Game *) gameValue;

- (void) moveTop;
- (void) moveDown;
- (void) moveLeft;
- (void) moveRight;

- (void) moveLeftTop;
- (void) moveLeftDown;
- (void) moveRightDown;
- (void) moveRightTop;

@end
