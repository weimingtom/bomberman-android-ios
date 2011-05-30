#import <Foundation/Foundation.h>

@class Map, Player, AVAudioPlayer, RessourceManager, Application, GameMap, Bomb, Position, BotPlayer;

/** The `Game` class allows to represent a Game. */
@interface Game : NSObject {
	NSMutableArray * players;
	NSMutableDictionary * bombsPlanted;
	AVAudioPlayer *soundStart;
	AVAudioPlayer *soundMode;
	NSMutableDictionary * bitmaps;
	BOOL isStarted;
	BOOL displayGo;
	BOOL isEnded;
	RessourceManager * resource;
	Application *application;
	Player * winner;
	GameMap * map;
}

/** The `players` which are in the game.*/
@property (nonatomic, retain) NSMutableArray * players;

/** The `bombs` which are planted in on the map.*/
@property (nonatomic, retain) NSMutableDictionary * bombsPlanted;

/** The `bitmaps` which are displayed in the game.*/
@property (nonatomic, retain) NSMutableDictionary * bitmaps;

/** The `map` game.*/
@property (nonatomic, retain) GameMap * map;

/** A boolean for know if the game has been started.*/
@property (nonatomic) BOOL isStarted;

/** A boolean for know if the game is ended.*/
@property (nonatomic) BOOL isEnded;

///------------------------
/// @name Init the Game
///------------------------

/**  Initializes the `Game`.
 
 @return the Game.
 */
- (void) initGame;

/**  Initializes the `Game` with a `Map`.
 
 @param mapName The map's name.
 @return the Game.
 */
- (id) initWithMapName:(NSString *)mapName;

///------------------------
/// @name Managing the Game
///------------------------

/**  Allows to start the timer for the start of the game.
 */
- (void) timerStartGame;

/**  Start the `Game`.
 */
- (void) startGame;

/**  Allows to start a timer for display Go at the begin of the game.
 */
- (void) timerDisplayGo;

/**  Allows to stop the display of Go.
 */
- (void) stopDisplayGo;

/**  Allows to end the Game.
 */
- (void) endGame;

/**  Allows to quit the game.
 */
- (void) quitGame;

/**  Allows to pause the game.
 
 @param enable Allows to enable the pause.
 */
- (void) pauseGame:(BOOL)enable;


///------------------------
/// @name Draw the Game
///------------------------

/**  Allows to draw the Game.
 
 @param context The graphic context.
 */
- (void) draw:(CGContextRef)context;

///------------------------
/// @name Managing the game's sound and bitmaps
///------------------------

/**  Allows to load all the sound of the Game.
 */
- (void) loadSounds;

/**  Allows to load all the bitmaps of the Game.
 */
- (void) loadBitmaps;

/**  Allows to disable all the sound of the Game.
 */
- (void) disableSound;

///------------------------
/// @name Managing the Human player
///------------------------

/**  Allows to get the human player of the Game.
 
 @return the human player of the Game.
 */
- (Player *) getHumanPlayer;

///------------------------
/// @name Managing the Bombs
///------------------------

/** Allows to plant a Bomb on the game. 
 
 @param bomb The bomb which will be planted by th eplayer.
 @param player The player wich will plant the bomb.
 */
- (void)plantingBombByPlayer:(Bomb *)bomb;

/** Allows to explode a bomb. 
 
 @param position The position of the bomb.
 */
- (void)bombExplode:(Position *)position;


/** Allows to update the `Map`. 
 */
- (void) updateMap;

/** Allows get the number of player. 
 
 @return the number of player.
 */
- (NSInteger) nbPlayers;

/** Allows get all the `botPlayer` of the `Game`. 
 
 @return the `botPlayers` of the `Game`.
 */
- (NSArray *)getBotPlayers;

@end