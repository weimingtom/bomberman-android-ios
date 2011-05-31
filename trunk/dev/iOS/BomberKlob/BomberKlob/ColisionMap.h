#import <Foundation/Foundation.h>

@class EditorMap, Bomb, Objects, Position, Node;


/** The `ColisionMap` class aims to manage the map containing all information for the bot. */
@interface ColisionMap : NSObject {
    
    NSMutableArray *map;
    NSMutableArray *bombs;
    NSArray *players;
}


///----------------------------------------
/// @name Initializing a ColisionMap Object
///----------------------------------------

/** Initializes and returns a newly allocated `ColisionMap` object with map and players.
 
 @param mapValue The current map.
 @param playersValue The players on map.
 @return A newly allocated `ColisionMap` object with map and players.
 */
- (id)initWithMap:(EditorMap *)mapValue players:(NSArray *)playersValue;

/** Initializes the colision according to the current map.
 
 @param mapValue The current map.
 */
- (void)initMap:(EditorMap *)mapValue;


///---------------------
/// @name Drawing Method
///---------------------

/** Draws the colision map in context.
 
 @param context The view context.
 */
- (void)draw:(CGContextRef)context;


///--------------------------------
/// Methods to manage a ColisionMap
///--------------------------------

/** Adds a dangerous area.
 
 @param bomb The bomb that generate the dangerous area.
 */
- (void)addDangerousArea:(Bomb *)bomb;

/** Updates the dangerous area.
 
 @param remove If it must remove the bombs.
 */
- (void)updateDangerousArea:(BOOL)remove;

/** Deletes the dangerous area.
 
 @param bomb The bomb that generate the dangerous area.
 */
- (void)deleteDangerousArea:(Bomb *)bomb;

/** Adds fire generate by  bomb.
 
 @param position The fire position.
 */
- (void)addFire:(Position *)position;

/** Deletes an object.
 
 @param object The object delete.
 */
- (void)deleteObject:(Objects *)object;

/** Plants a bomb.
 
 @param bomb The bomb that will be plant.
 */
- (void)bombPlanted:(Bomb *)bomb;

/** Explodes a bomb.
 
 @param bomb The bomb that will be explod.
 */
- (void)bombExploded:(Bomb *)bomb;


///--------------------
/// @name Checking Case
///--------------------

/** Return `YES` if the case at position (i, j) is traversable by player, otherwise `NO`.
 
 @param i The abscissa value.
 @param j The orderly value.
 @return `YES` if the case at position (i, j) is traversable by player, otherwise `NO`.
 */
- (BOOL)isTraversableByPlayer:(NSInteger)i j:(NSInteger)j;

/** Return `YES` if the case at position (i, j) is traversable by fire, otherwise `NO`.
 
 @param i The abscissa value.
 @param j The orderly value.
 @return `YES` if the case at position (i, j) is traversable by fire, otherwise `NO`.
 */
- (BOOL)isTraversableByFire:(NSInteger)i j:(NSInteger)j;

/** Return `YES` if the case at position (i, j) is a bomb, otherwise `NO`.
 
 @param i The abscissa value.
 @param j The orderly value.
 @return `YES` if the case at position (i, j) is a bomb, otherwise `NO`.
 */
- (BOOL)isBomb:(NSInteger)i j:(NSInteger)j;

/** Retruns `YES` if the case at position (i, j) is a fire, otherwise `NO`.
 
 @param i The abscissa value.
 @param j The orderly value.
 @return `YES` if the case at position (i, j) is a fire, otherwise `NO`.
 */
- (BOOL)isFire:(NSInteger)i j:(NSInteger)j;

/** Retruns `YES` if the case at position (i, j) is a dangerous area, otherwise `NO`.
 
 @param i The abscissa value.
 @param j The orderly value.
 @return `YES` if the case at position (i, j) is a dangerous area, otherwise `NO`.
 */
- (BOOL)isDangerousArea:(NSInteger)i j:(NSInteger)j;

/** Retruns `YES` if the case at position (i, j) is a destructible block, otherwise `NO`.
 
 @param i The abscissa value.
 @param j The orderly value.
 @return `YES` if the case at position (i, j) is a destructible block, otherwise `NO`.
 */
- (BOOL)isDestructibleBlock:(NSInteger)i j:(NSInteger)j;


///--------------------
/// @name Other Methods
///--------------------

/** Retruns the adjacent cases of `node`.
 
 @param node The current node.
 @param arrived  The arrived.
 @return The adjacent cases of `node`.
 */
- (NSArray *)adjacentCases:(Node *)node arrived:(Position *)arrived;

/** Computes the heuristic Manhattan cost.
 
 @param start The start.
 @param arrived The arrived.
 @return The heuristic Manhattan cost.
 */
- (NSInteger)heuristicManhattan:(Position *)start arrived:(Position *)arrived;

/** Return the number of case.
 
 @return The number of case.
 */
- (NSInteger)nbCase;

@end
