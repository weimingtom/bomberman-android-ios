#import <Foundation/Foundation.h>
#import "Position.h"
#import "Destructible.h"

#define INVINCIBILITY_TIME  80
#define INVINCIBILITY_TIME_REFRESH  5

@class Bomb;
/** The `Player` class allows to represent a player. */
@interface Player : Destructible {
    
	NSMutableDictionary * bombsTypes;
	NSString * color;
	NSInteger lifeNumber;
	NSInteger powerExplosion;
	NSInteger timeExplosion;
	NSInteger shield;
	NSInteger speed;
	NSInteger bombNumber;
	BOOL istouched;
	BOOL isKilled;
	BOOL bombPosed;
	BOOL isInvincible;
	NSInteger timeInvincible;
	NSMutableDictionary *png;

}

/** All the bombs's types which the user own.*/
@property (nonatomic,retain)NSMutableDictionary * bombsTypes;

/** All bitmaps to display if the player is touched, killed or hurted.*/
@property (nonatomic,retain)NSMutableDictionary * png;

/** All the player's color.*/
@property (nonatomic,retain) NSString * color;

/** All the power explosion of his bombs.*/
@property (nonatomic) NSInteger powerExplosion;

/** The time before explosion of his bombs.*/
@property (nonatomic) NSInteger timeExplosion;

/** The value of the player's shield.*/
@property (nonatomic) NSInteger shield;

/** The number of bomb wich the player can plant.*/
@property (nonatomic) NSInteger bombNumber;

/** The value of the player's speed.*/
@property (nonatomic) NSInteger speed;

/** the player's life number.*/
@property (nonatomic) NSInteger lifeNumber;

/** Allows to know if the player has been planted a bomb.*/
@property (nonatomic) BOOL bombPosed;

/** Allows to know if the player is hurted.*/
@property (nonatomic) BOOL istouched;

/** Allows to know if the player is killed.*/
@property (nonatomic) BOOL isKilled;

/** Allows to know if the player is invincible.*/
@property (nonatomic) BOOL isInvincible;

/** The invicibilty time of the player after beeing hurted by a bomb.*/
@property (nonatomic, assign) NSInteger timeInvincible;


///------------------------
/// @name Initialize the Player
///------------------------

/** Allows to init a basic Player.
 
 @return The Player.
 */
- (id) init;


/** Allows to init a Player with a color and a position.
 
 @param colorValue The color of the player.
 @param positionValue The player's initial position.
 @return The Player.
 */
- (id) initWithColor:(NSString *)colorValue position:(Position *) positionValue;

/** Allows to init a Player with a color and a position.
 
 @param imageNameValue The image name of the player.
 @param positionValue The player's initial position.
 @return The Player.
 */
- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue;



///------------------------
/// @name Manage the Player
///------------------------

/** Allows to make the player alive.*/
- (void) live;

/** Allows to make the player relive.*/
- (void) relive;

/** Allows to do die the player.*/
- (void) die;

/** Allows to do hurt the player.*/
- (void) hurt;

/** Allows to do a top movement.*/
- (void) moveTop;

/** Allows to do a down movement.*/
- (void) moveDown;

/** Allows to do a left movement.*/
- (void) moveLeft; 

/** Allows to do a right movement.*/
- (void) moveRight;

/** Allows to do a left-top movement.*/
- (void) moveLeftTop;

/** Allows to do a left-down movement.*/
- (void) moveLeftDown;

/** Allows to do a right-down movement.*/
- (void) moveRightDown;

/** Allows to do a right-top movement.*/
- (void) moveRightTop;

/** Allows to stop a top movement.*/
- (void) stopTop;

/** Allows to stop a down movement.*/
- (void) stopDown;

/** Allows to stop a left movement.*/
- (void) stopLeft;

/** Allows to stop a right movement.*/
- (void) stopRight;


/** Allows to stop a left-top movement.*/
- (void) stopLeftTop;

/** Allows to stop a right-top movement.*/
- (void) stopRightTop;

/** Allows to stop a left-down movement.*/
- (void) stopLeftDown;

/** Allows to stop a right-down movement.*/
- (void) stopRightDown;

/** Allows to plant a Bomb.*/
- (void) plantingBomb;



///------------------------
/// @name Draw the Player
///------------------------


/** Allows to draw the player.
 
 @param context The graphic's context for draw the player.
 */
- (void) draw:(CGContextRef)context;

/** Allows to draw the player with an alpha value.
 
 @param context The graphic's context for draw the player.
 @param alpha The alpha value for draw the player.
 */
- (void) draw:(CGContextRef)context alpha:(CGFloat)alpha;


@end
