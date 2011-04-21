//
//  Engine.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Game, RessourceManager, Object;

/** The `Engine` class allows to make all the calculations of the game (collisions, delay bomb explosions ... ). */
@interface Engine : NSObject {
    Game * game;
	RessourceManager * resource;
}

/** The `game` with which the engine will make his calculations.*/
@property (nonatomic, retain) Game * game;


///----------------------------
/// @name Initializing Engine
///----------------------------

/** Initializes the `Engine` with a `Game`. */
- (id) initWithGame:(Game *) gameValue;

///-------------------------------------------
/// @name Allows you to Manage the Collision of Objects
///-------------------------------------------

/** Lets see if the object is in collisions by advancing of x and y.
 
 @return `YES` if the object is in collision, `NO` otherwise.
 */
- (BOOL) isInCollision: (Object *) player: (NSInteger) xValue: (NSInteger) yValue;

///-------------------------------------------
/// @name Allows you to Manage the Movements of Players
///-------------------------------------------

/** Allows you to manage the movements to the Top. */
- (void) moveTop;

/** Allows you to manage the movements to the Bottom. */
- (void) moveDown;

/** Allows you to manage the movements to the Left. */
- (void) moveLeft;

/** Allows you to manage the movements to the Right. */
- (void) moveRight;

/** Allows you to manage the movements to the Left-Top corner. */
- (void) moveLeftTop;

/** Allows you to manage the movements to the Left-Bottom corner. */
- (void) moveLeftDown;

/** Allows you to manage the movements to the Right-Bottom corner. */
- (void) moveRightDown;

/** Allows you to manage the movements to the Right-Top corner. */
- (void) moveRightTop;


///-------------------------------------------
/// @name Allows you to Update the Bomb
///-------------------------------------------

/** Allows to update the animations bombs and explode them. */
- (void) updateBombs;

/** Allows to start a timer wich will permit to update bombs regularly. */
- (void) startTimerBombs;

/** Allows to start a thread wich will permit to update bombs regularly. */
- (void) startTimerBombsThread;

@end
