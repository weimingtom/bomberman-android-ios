#import <Foundation/Foundation.h>
#import "Map.h"

@class ColisionMap, Bomb, Objects, Position;

/** The `Map` class allows to represent a game's map. */
@interface GameMap : Map {
    
    UIImage *bitmap;
    NSMutableDictionary *animatedObjects;
    NSMutableDictionary *animatedObjectsInitial;
    ColisionMap *colisionMap;
}

/** The Image which are used to display the inanimated and undestructible object.*/
@property (nonatomic, retain) UIImage *bitmap;

/** The animated or destructible object of the GameMap.*/
@property (nonatomic, retain) NSMutableDictionary *animatedObjects;

/** The initial animated or destructible object of the GameMap.*/
@property (nonatomic, retain) NSMutableDictionary *animatedObjectsInitial;

/** The `CollisionMap` of the GameMap.*/
@property (nonatomic, retain) ColisionMap *colisionMap;

///------------------------
/// @name Manage the GameMap
///------------------------

/** Allows to load a `.klob`map file. */
- (void)load;


/** Allows to create the screenshot of the inanimates objects.
 
 @param grounds The Matrice which represent the grounds of the Map
 @param blocks The Matrice which represent the blocks of the Map
 */
- (void)makeBitmap:(NSArray *)grounds blocks:(NSArray *)blocks;


/** Allows to update Map's objects. */
- (void)update;


/** Allows to add an Animated Object on the Map.
 
 @param object The object to add on the Map
 */
- (void)addAnimatedObject:(Objects *)object ;

/** Allows to remove an Animated Object on the Map.
 
 @param object The object to remove on the Map
 */
- (void)deleteAnimatedObject:(Position *)position;


///------------------------
/// @name Draw the GameMap
///------------------------

/** Allows to draw all the GameMap.
 
 @param context The graphic's context which permit to draw.
 */
- (void)draw:(CGContextRef)context;

/** Allows to draw all the GameMap thanks to two matrice of a `EditorMap`.
 
 @param context The graphic's context which permit to draw.
 @param grounds The Matrice which represent the grounds of an EditorMap
 @param blocks The Matrice which represent the blocks of an EditorMap
 */
- (void)drawBitmap:(CGContextRef)context grounds:(NSArray *)grounds blocks:(NSArray *)blocks;


///------------------------
/// @name Manage Bombs
///------------------------

/** Allows to plant a bomb on the `GameMap`.
 
 @param bomb The bomb which want to plant.
 */
- (void)bombPlanted:(Bomb *)bomb;

/** Allows to explode a bomb on the `GameMap`.
 
 @param bomb The bomb which want to explode.
 */
- (void)bombExplode:(Bomb *)bomb;

@end
