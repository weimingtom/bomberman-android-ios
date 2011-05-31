#import <Foundation/Foundation.h>

typedef enum CaseType CaseType;
enum CaseType
{
    EMPTY = 0, // Empty
    
    // Objects that are display on the screen
    UNDESTRUCTIBLE_BLOCK = 1, // Undestructible objects that are not traversable by player and fire
    DESTRUCTIBLE_BLOCK = 2, // Destructible objects that are not traversable by player and fire
    GAPE = 3,  // Objects that are just traversable by fire and not by player
    BOMB = 5,  // Bombs planted
    FIRE = 6,  // Explosions fire
    
    // Objects that are just for ai mannaging
    DANGEROUS_AREA = 4 // It is where, there will be explosions fire
};
// Priorities: EMPTY < BLOCK < GAPE < DANGEROUS_AREA < BOMB < FIRE

/** The `ColisionCase` class aims to manage the case of colision map. */
@interface ColisionCase : NSObject {
    
    NSMutableArray *counters;
    NSMutableArray *types;
}


///----------------------------------------
/// @name Initializing a ColisionCase Object
///----------------------------------------

/** Initializes and returns a newly allocated `ColisionCase` object with type.
 
 @param typeValue The type value.
 @return A newly allocated `ColisionCase` object with type.
 */
- (id)initWithType:(CaseType)typeValue;


///--------------------------------
/// Methods to manage a ColisionMap
///--------------------------------

/** Adds type to the case.
 
 @param typeValue The type value.
 */
- (void)addValue:(CaseType)typeValue;

/** Removes type to the case.
 
 @param typeValue The type value.
 */
- (void)removeValue:(CaseType)typeValue;


///---------------------
/// @name Drawing Method
///---------------------

/** Draws the case.
 
 @param context The view context.
 @param x The abscissa value.
 @param y The orderly value.
 */
- (void)draw:(CGContextRef)context x:(NSInteger)x y:(NSInteger)y;


///--------------------
/// @name Checking Case
///--------------------

/** Return `YES` if the case is traversable by player, otherwise `NO`.
 
 @return `YES` if the case is traversable by player, otherwise `NO`.
 */
- (BOOL)isTraversableByPlayer;

/** Return `YES` if the case is traversable by fire, otherwise `NO`.
 
 @return `YES` if the case is traversable by fire, otherwise `NO`.
 */
- (BOOL)isTraversableByFire;

/** Retruns `YES` if the case is a undestructible block, otherwise `NO`.
 
 @return `YES` if the case is a undestructible block, otherwise `NO`.
 */
- (BOOL)isUndestructibleBlock;

/** Retruns `YES` if the case is a destructible block, otherwise `NO`.

 @return `YES` if the case is a destructible block, otherwise `NO`.
 */
- (BOOL)isDestructibleBlock;

/** Return `YES` if the case is a bomb, otherwise `NO`.
 
 @return `YES` if the case is a bomb, otherwise `NO`.
 */
- (BOOL)isBomb;

/** Retruns `YES` if the case is a fire, otherwise `NO`.

 @return `YES` if the case is a fire, otherwise `NO`.
 */
- (BOOL)isFire;

/** Retruns `YES` if the case is a dangerous area, otherwise `NO`.

 @return `YES` if the case is a dangerous area, otherwise `NO`.
 */
- (BOOL)isDangerousArea;

@end
