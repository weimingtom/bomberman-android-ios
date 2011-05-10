#import <Foundation/Foundation.h>

@class Game, RessourceManager, Objects, Bomb, Player;

/** The `Engine` class allows to make all the calculations of the game (collisions, delay bomb explosions ... ). */
@interface Engine : NSObject {
    Game * game;
	RessourceManager * resource;
	NSThread * updateBombsThread;
	NSCondition * updateBombsCondition;
	NSThread * updatePlayersThread;
	NSCondition * updatePlayersCondition;
	BOOL updateBombsPause;
	BOOL updatePlayersPause;

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

/** Allows you to stop the movements when the player is up. */
- (void) stopTop;

/** Allows you to stop the movements when the player is down. */
- (void) stopDown;

/** Allows you to stop the movements when the player is to the left. */
- (void) stopLeft;

/** Allows you to stop the movements when the player is to the left. */
- (void) stopRight;

/** Allows you to stop the movements when the player is on the Left-Top corner. */
- (void) stopLeftTop;

/** Allows you to stop the movements when the player is on the Right-Top corner. */
- (void) stopRightTop;

/** Allows you to stop the movements when the player is on the Left-Down corner. */
- (void) stopLeftDown;

/** Allows you to stop the movements when the player is on the Right-Down corner. */
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

/** Allows to verify if there are bomb to explode on the map. */
- (BOOL) thereAreBombToExplode;

/** Allows to display bomb's fire when a bomb explodes and to destroy objects in collision with it. */
- (void) displayFire:(Bomb *) bomb;

/** Allows to plant a Bomb on the game. */
- (void) plantingBomb:(Bomb *)bomb;




///------------------------
/// @name Manage threads
///------------------------

/** Allows to pause all the threads of the Engine and the Game. */
- (void) pauseThread:(BOOL) enable;

/** Allows to cancel all the threads of the Engine and the Game. */
- (void) stopThread;



///------------------------
/// @name Updating the Bot players
///------------------------

/** Allows to start a timer wich will permit to update bot players regularly. */
- (void) startTimerPlayers;

/** Allows to start a thread wich will permit to update bot players regularly. */
- (void) startTimerPlayersThread;

/** Allows to update the bot players. */
- (void) updatePlayers;

/** Allows to know if the game has been started. */
- (BOOL) gameIsStarted;

/**Allows to update the game's map. */
- (void) updateMap;

- (Player *) getHumanPlayer;

- (NSInteger) nbPlayers;

@end
