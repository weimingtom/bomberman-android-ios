#import <Foundation/Foundation.h>
#import "Game.h"

/** The `Single` class allows to represent a single player game. */
@interface Single : Game {
	NSThread * timerThread;
	NSString * time;
	NSCondition * timeCondition;
	BOOL isPaused;
}

/** The time of the single game.*/
@property (nonatomic,retain) NSString * time;

/** The timer thread of the single game.*/
@property (nonatomic,retain) NSThread * timerThread;

/** The timer condition of the single game (Allows to pause the thread).*/
@property (nonatomic,retain) NSCondition * timeCondition;

/** A boolean for know is the single game is paused or not.*/
@property (nonatomic) BOOL isPaused;


///------------------------
/// @name Manage the Time
///------------------------

/** Allows to pause the single game. */
- (void) pauseGame;

/** Allows to start the timer thread of the single game. */
- (void) startTimer;

/** Allows to start the timer of the single game. */
- (void) runTimer;

/** Allows to update the timer each second. */
- (void) updateTime;

/** Allows to pause the game.
 
 @param enable `YES`If you want to pause the game, `NO`otherwise.
 */
- (void) pauseGame:(BOOL)enable;

@end
