#import <Foundation/Foundation.h>

@class Game, RessourceManager, Objects, Bomb, Player, BotPlayer;

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

/**  Initializes the `Engine` with a `Map`.
 
 @param mapName The map's name.
 @return the engine.
 */
- (id) initWithMapName:(NSString *)mapName;

/**  Initializes the `Engine` with a `Game`.
 
 @param gameValue The game used to initialize the engine.
 @return the engine.
 */
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

/** Lets see if the object is in collision with a bomb planted on the game by advancing of x and y.
 
 @return `YES` if the object is in collision with a bomb, `NO` otherwise.
 */
- (BOOL) isInCollisionWithABomb: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue;




///----------------------------------------
/// @name Managing the Movements of Players
///----------------------------------------

/** Allows you to manage the movements to the Top. */
- (void) moveTop:(Player *)player ;

/** Allows you to manage the movements to the Bottom. */
- (void) moveDown:(Player *)player ;

/** Allows you to manage the movements to the Left. */
- (void) moveLeft:(Player *)player ;

/** Allows you to manage the movements to the Right. */
- (void) moveRight:(Player *)player ;

/** Allows you to manage the movements to the Left-Top corner. */
- (void) moveLeftTop:(Player *)player ;

/** Allows you to manage the movements to the Left-Bottom corner. */
- (void) moveLeftDown:(Player *)player ;

/** Allows you to manage the movements to the Right-Bottom corner. */
- (void) moveRightDown:(Player *)player ;

/** Allows you to manage the movements to the Right-Top corner. */
- (void) moveRightTop:(Player *)player ;

/** Allows you to stop the movements to the Top. */
- (void) stopTop:(Player *)player;

/** Allows you to stop the movements to the Bottom. */
- (void) stopDown:(Player *)player;

/** Allows you to stop the movements to the Left. */
- (void) stopLeft:(Player *)player;

/** Allows you to stop the movements to the Right. */
- (void) stopRight:(Player *)player;

/** Allows you to stop the movements to the Left-Top corner. */
- (void) stopLeftTop:(Player *)player;

/** Allows you to stop the movements to the Right-Top corner. */
- (void) stopRightTop:(Player *)player;

/** Allows you to stop the movements to the Left-Bottom corner. */
- (void) stopLeftDown:(Player *)player;

/** Allows you to stop the movements to the Right-Bottom corner. */
- (void) stopRightDown:(Player *)player;



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

/** Allows to display bomb's fire when a bomb explodes and to destroy objects in collision with it.
 
 @param bomb The bomb that we will display the flames.
 */
- (void) displayFire:(Bomb *) bomb;

/** Allows to plant a Bomb on the game. 
 
 @param bomb The bomb which will be planted by th eplayer.
 @param player The player wich will plant the bomb.
 */
- (void)plantingBomb:(Bomb *)bomb owner:(Player *)owner;




///------------------------
/// @name Manage threads
///------------------------

/** Allows to pause all the threads of the Engine and the Game. 
 
 @param enable This boolean permit to pause or to stop the pause of the thread.
 */
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

/** Allows to know if the game has been started. 
 
 @return `YES` if the game has been started, `NO` otherwise.
 */
- (BOOL) gameIsStarted;

/** Allows to make and action of a bot. 
 
 @param botPlayer The botPlayer wich will make the action.
 */
- (void)makeActionBot:(BotPlayer *)botPlayer;

/**Allows to update the game's map. */
- (void) updateMap;

/**Allows to get the human player of the game. 
 
 @return the human player of the game.
 */
- (Player *) getHumanPlayer;

/**Allows to get the number of player of the game. 
 
 @return the number of player.
 */
- (NSInteger) nbPlayers;

@end
