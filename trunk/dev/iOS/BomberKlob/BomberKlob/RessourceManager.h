#import <Foundation/Foundation.h>

/** The `RessourceManager` class allows to make all the loads of the game and get it ). */
@interface RessourceManager : NSObject {
	NSInteger tileSize;
	NSInteger screenHeight;
	NSInteger screenWidth;

	NSMutableDictionary * bitmapsAnimates;
	NSMutableDictionary * bitmapsPlayer;
	NSMutableDictionary * bitmapsBombs;
	NSMutableDictionary * bitmapsInformationGameView;

}

/** The Animated Objects of the game.*/
@property (nonatomic, retain) NSMutableDictionary * bitmapsAnimates;

/** All the colored players of the game.*/
@property (nonatomic, retain) NSMutableDictionary * bitmapsPlayer;

/** All the bombs of the game.*/
@property (nonatomic, retain) NSMutableDictionary * bitmapsBombs;

/** The bitmaps of the `GameInformationView`.*/
@property (nonatomic, retain) NSMutableDictionary  * bitmapsInformationGameView;

/** The tile's size.*/
@property (nonatomic) NSInteger tileSize;

/** The screen height size.*/
@property (nonatomic) NSInteger screenHeight;

/** The screen width size.*/
@property (nonatomic) NSInteger screenWidth;


///------------------------
/// @name Updating the Bot players
///------------------------

/**Allows to get the `RessourceManager`. 
 
 @return the shared ressource.
 */
+(RessourceManager*)sharedRessource;

/**Allows to alloc memory for the `RessourceManager`. 
 
 @return the shared ressource.
 */
+ (id) alloc;

/**Allows to init memory for the `RessourceManager`. 
 
 @return the shared ressource.
 */
- (id) init;


///------------------------
/// @name Manage the loads
///------------------------


/**Allows to load the screen properties. */
- (void) loadProperty;

/**Allows to load the players. */
- (void) loadPlayer;

/**Allows to load the bombs. */
- (void) loadBombs;

/**Allows to load all the objects different of bombs and players. */
- (void) loadObjects;


@end
