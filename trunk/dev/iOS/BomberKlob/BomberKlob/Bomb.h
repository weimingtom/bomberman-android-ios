#import <Foundation/Foundation.h>
#import "Destructible.h"


@class Player;

/** The `Bomb` class allows to represent a Bomb. */
@interface Bomb : Destructible {
    NSInteger power;
	NSString * type;
	BOOL explode;
	Player * owner;
	NSInteger time;
}

/** The power explosion of the bomb.*/
@property (nonatomic) NSInteger power;

/** The time before the explosion.*/
@property (nonatomic) NSInteger time;

/** The boolean for know if the bomb has been exploded.*/
@property (nonatomic) BOOL explode;

/** The type of the bomb.*/
@property (nonatomic,retain) NSString * type;

/** The Player who has planted the bomb.*/
@property (nonatomic,retain) Player * owner;

///------------------------
/// @name Initialize the Bomb
///------------------------

/** Allows to init a bomb with a type and a position.
 
 @param imageNameValue the type of the bomb.
 @param positionValue the initial position of the bomb.
 @return The Bomb.
 */
- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue;


///------------------------
/// @name Initialize the Bomb
///------------------------

/** Allows to update the bomb. */
- (void) update;

/** Allows to know if the bomb should be destroy or not.
 
 @return `YES`If the bomb should be destroy, `NO`otherwise.
 */
- (BOOL) hasAnimationFinished;

// TODO: Méthode inutiles:
//- (void) destroyable;
//- (void) draw:(CGContextRef) context;

@end
