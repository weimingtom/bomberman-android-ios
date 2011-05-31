#import <Foundation/Foundation.h>
#import "Map.h"

@class Objects, Position, Animated, DBUser;

/** The `EditorMap` class aims to model the maps who are used in the editor map. */
@interface EditorMap : Map <NSCoding> {
 
	NSMutableArray *grounds;
	NSMutableArray *blocks;
}

/** The matrix representing the ground of the map. */
@property (nonatomic, retain) NSMutableArray *grounds;

/** The matrix representing the blocks who are displayed on the grounds. */
@property (nonatomic, retain) NSMutableArray *blocks;


///--------------------------------------
/// @name Initializing a EditorMap Object
///--------------------------------------

/** Initializes and returns a newly allocated `EditorMap` object with name.
 
 @param mapName The name of map.
 @return A newly allocated `EditorMap` object with name.
 @see initBasicMap
 */
- (id)initWithMapName:(NSString *)mapName;


///
/// @name Primitive Methods to Initialize or Save a Map
///

/**  */
- (void)initBasicMap;

/** Saves the map in nameMap.klob file.
 
 Creates also a map preview in nameMap.png file. 
 
 @param owner The map owner.
 */
- (void)save:(DBUser *)owner;

/**  Loads the map thanks to her name.
 
 @see makePreview
 */
- (void)load;

/** Makes a map preview.
 
 @see load
 */
- (void)makePreview;

/** Reset the map.
 
 @see load
 */
- (void)reset;


///----------------------------
/// @name Methods to make a Map
///----------------------------

/** Adds a ground element thanks to his position.
 
 @param ground The ground to add.
 @param position The position ground.
 @see addBlock:position:
 @see addPlayer:color:
 */
- (void)addGround:(Objects *)ground position:(Position *)position;

/** Adds a block thanks to his position.
 
 @param block The block to add.
 @param position The position block.
 @see addGround:position:
 @see addPlayer:color:
 */
- (void)addBlock:(Objects *)block position:(Position *)position;

/** Adds a player with his position and color.
 
 @param position The position player.
 @param color The player color.
 @see addGround:position:
 @see addBlock:position:
 */ 
- (void)addPlayer:(Position *)position color:(NSString *)color;

/** Deletes a block thanks to his position.
 
 @param position The position block.
 @see addBlock:position:
 @see deletePlayerAtPosition:
 */
- (void)deleteBlockAtPosition:(Position *)position;

/** Deletes a player thanks to his position.
 
 @param position The position player.
 @see addPlayer:color:
 @see deletePlayerAtPosition:
 */
- (void)deletePlayerAtPosition:(Position *)position;


///----------------------
/// @name Drawing Methods
///----------------------

/** Draws the map in the context.
 
 @param context The view context.
 @see draw:alpha:
 @see drawPlayers:
 @see drawPlayers:alpha:
 @see drawMapAndPlayers:alpha:
 */
- (void)draw:(CGContextRef)context;

/** Draws the map in the context with an alpha.
 
 @param context The view context.
 @param alpha The drawing alpha.
 @see draw:
 @see drawPlayers:
 @see drawPlayers:alpha:
 @see drawMapAndPlayers:alpha:
 */
- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha;

/** Draws the players in the context.
 
 @param context The view context.
 @see draw:
 @see draw:alpha:
 @see drawPlayers:alpha:
 @see drawMapAndPlayers:alpha:
 */
- (void)drawPlayers:(CGContextRef)context;

/** Draws the players in the context with an alpha.
 
 @param context The view context.
 @param alpha The drawing alpha.
 @see draw:
 @see draw:alpha:
 @see drawPlayers:
 @see drawMapAndPlayers:alpha:
 */
- (void)drawPlayers:(CGContextRef)context alpha:(CGFloat)alpha;

/** Draws the map and players in the context with an alpha. 
 
 @param context The view context.
 @param alpha The drawing alpha.
 @see draw:
 @see draw:alpha:
 @see drawPlayers:
 @see drawPlayers:alpha:
 */
- (void)drawMapAndPlayers:(CGContextRef)context alpha:(CGFloat)alpha;


///-----------------------
/// @name Checking the Map
///-----------------------

/** Returns `YES` if the case in ground matrix at `position` is empty, otherwise `NO`.
 
 @param position The case position.
 @return `YES` if the case in blocks matrix at `position` is empty, otherwise `NO`.
 @see thereIsBlock:
 @see thereIsPlayer:
 */
- (BOOL)isEmpty:(Position *)position;

/** Returns `YES` if there is a block at `position`, otherwise `NO`.
 
 @param position The block position.
 @return `YES` if there is a block at `position`, otherwise `NO`.
 @see isEmpty:
 @see thereIsPlayer:
 */
- (BOOL)thereIsBlock:(Position *)position;

/** Returns `YES` if there is a player at `position`, otherwise `NO`.
 
 @param position The player position.
 @return `YES` if there is a player at `position`, otherwise `NO`.
 @see isEmpty:
 @see thereIsBlock:
 */
- (BOOL)thereIsPlayer:(Position *)position;

@end
