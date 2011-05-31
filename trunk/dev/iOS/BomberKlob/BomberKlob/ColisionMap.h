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
 
 @param bomb 
 */
- (void)bombPlanted:(Bomb *)bomb;

/**
 
 @param bomb 
 */
- (void)bombExploded:(Bomb *)bomb;


///--------------------
/// @name Checking Case
///--------------------

/**
 
 @param i 
 @param j 
 @return 
 */
- (BOOL)isTraversableByPlayer:(NSInteger)i j:(NSInteger)j;

/**
 
 @param i 
 @param j 
 @return 
 */
- (BOOL)isTraversableByFire:(NSInteger)i j:(NSInteger)j;

/**
 
 @param i 
 @param j 
 @return 
 */
- (BOOL)isBomb:(NSInteger)i j:(NSInteger)j;

/**
 
 @param i 
 @param j 
 @return 
 */
- (BOOL)isFire:(NSInteger)i j:(NSInteger)j;

/**
 
 @param i 
 @param j 
 @return 
 */
- (BOOL)isDangerousArea:(NSInteger)i j:(NSInteger)j;

/**
 
 @param i 
 @param j 
 @return 
 */
- (BOOL)isDestructibleBlock:(NSInteger)i j:(NSInteger)j;


///--------------------
/// @name Other Methods
///--------------------

/**
 
 @param node 
 @param arrived  
 @return 
 */
- (NSArray *)adjacentCases:(Node *)node arrived:(Position *)arrived;

/**
 
 @param start 
 @param arrived  
 @return 
 */
- (NSInteger)heuristicManhattan:(Position *)start arrived:(Position *)arrived;

/**
 
 @return 
 */
- (NSInteger)nbCase;

@end
