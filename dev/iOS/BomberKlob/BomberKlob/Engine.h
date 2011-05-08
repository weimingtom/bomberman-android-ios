#import <Foundation/Foundation.h>

@class Game, RessourceManager, Objects, Bomb;

/** The `Engine` class allows to make all the calculations of the game (collisions, delay bomb explosions ... ). */
@interface Engine : NSObject {
    Game * game;
	RessourceManager * resource;
	NSThread * updateBombsThread;
	NSCondition * updateBombsCondition;
	BOOL updateBombsPause;
}

/** The `game` with which the engine will make his calculations.*/
@property (nonatomic, retain) Game * game;


///--------------------------
/// @name Initializing Engine
///--------------------------

/** Initializes the `Engine` with a `Game`. */
- (id) initWithMapName:(NSString *)mapName;

/** Initializes the `Engine` with a `Game`. */
- (id) initWithGame:(Game *) gameValue;

///----------------------------------------
/// @name Managing the Collision of Objects
///----------------------------------------

/** Lets see if the object is in collisions by advancing of x and y.
 
 @return `YES` if the object is in collision, `NO` otherwise.
 */
- (BOOL) isInCollision: (Objects *) player: (NSInteger) xValue: (NSInteger) yValue;


/** Lets see if the object is in collisions with a player. If Yes the player will be destroy.

 */
- (void) collisionWithPlayer: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue bomb:(Bomb *) aBomb;

- (BOOL) isInCollisionWithABomb: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue;




///----------------------------------------
/// @name Managing the Movements of Players
///----------------------------------------

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

- (void) stopTop;

- (void) stopDown;

- (void) stopLeft;

- (void) stopRight;

- (void) stopLeftTop;

- (void) stopRightTop;

- (void) stopLeftDown;

- (void) stopRightDown;



///------------------------
/// @name Updating the Bomb
///------------------------

/** Allows to update the animations bombs and explode them. */
- (void) updateBombs;

/** Allows to start a timer wich will permit to update bombs regularly. */
- (void) startTimerBombs;

/** Allows to start a thread wich will permit to update bombs regularly. */
- (void) startTimerBombsThread;

- (BOOL) thereAreBombToExplode;

- (void) displayFire:(Bomb *) bomb;

-(void) pauseThread:(BOOL) enable;
- (void) stopThread;
- (void)plantingBomb:(Bomb *)bomb;


@end
