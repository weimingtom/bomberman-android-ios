#import <Foundation/Foundation.h>
#import "Player.h"

@class ColisionMap, Node;

/** The `BotPlayer`class aims to manage a bot player. */
@interface BotPlayer : Player {
    
	NSString *difficulty;
    
    NSArray *path;
    
    ColisionMap *colisionMap;
    
    NSString *movement;
    BOOL plantingBomb;
    BOOL beInDanger;
    
    NSArray *enemies;
}

/** The map containing all informations for the bots (the untraversable case, the traversable case, the dangerous area, ect) */
@property (nonatomic, retain) ColisionMap *colisionMap;

/** The movement who bot must be do. */
@property (nonatomic, retain) NSString *movement;

/** If it must be plant a bomb. */
@property (nonatomic, assign) BOOL plantingBomb;

/** The enemis list. */
@property (nonatomic, retain) NSArray *enemies;

/** The path that bot must be travel. */
@property (nonatomic, retain) NSArray *path;


///--------------------------------------
/// @name Initializing a BotPlayer Object
///--------------------------------------

/** Initializes and returns a newly allocated `BotPlayer` object with the specified position and colision map.
 
 @param imageNameValue  
 @param positionValue The bot position.
 @param colisionMapValue The map containing all informations for the bots
 @return A newly allocated `BotPlayer` object with the specified position and colision map.
 */
- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue colisionMap:(ColisionMap *)colisionMapValue;


///-------------------
/// @name Computing A*
///-------------------

/** Computes the path with A* algorithm.
 
 @param arrivedPosition 
 @return An array containing the result of A* algorithm.
 */
- (NSArray *)pathFinding:(Position *)arrivedPosition;

/** Updates the open list according to a new nodes list.
 
 @param openList The open list.
 @param closedList The close list.
 @param newNodes The new nodes list.
 */
- (void)updateOpenList:(NSMutableDictionary *)openList closedList:(NSMutableDictionary *)closedList newNodes:(NSArray *)newNodes;

/** Finds the best node in open list.
 
 @param openList The open list.
 @return The best node in open list.
 */
- (Node *)findBestNode:(NSDictionary *)openList;

/** Computes the path with the closed list.
 
 @param closedList The close list.
 @param arrived The aim of player.
 @return The path with the closed list.
 */
- (NSArray *)computePath:(NSDictionary *)closedList arrived:(Position *)arrived;


///-------------------
/// @name Managing Bot
///-------------------

/** Computes the direction according to the path. */
- (void)computeDicrection;

/** Makes action for the bot. */
- (void)makeAction;

/** Computes the path lenght. 
 
 @return The path lenght.
 */
- (NSInteger)computeLenghtPath:(NSArray *)pathValue;

/** Finds the nearest enemy. 
 
 @return The nearest player.
 */
- (Player *)findNearestEnemy;

@end
