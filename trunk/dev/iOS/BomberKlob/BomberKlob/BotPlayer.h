#import <Foundation/Foundation.h>
#import "Player.h"

@class ColisionMap, Node;

@interface BotPlayer : Player {
    
	NSString *difficulty;
    
    NSArray *path;
    
    ColisionMap *colisionMap;
    
    NSString *movement;
    BOOL plantingBomb;
    BOOL beInDanger;
    
    NSArray *enemies;
}

@property (nonatomic, retain) ColisionMap *colisionMap;
@property (nonatomic, retain) NSString *movement;
@property (nonatomic, assign) BOOL plantingBomb;
@property (nonatomic, retain) NSArray *enemies;
@property (nonatomic, retain) NSArray *path;

- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue colisionMap:(ColisionMap *)colisionMapValue;

- (NSArray *)pathFinding:(Position *)arrivedPosition;
- (void)updateOpenList:(NSMutableDictionary *)openList closedList:(NSMutableDictionary *)closedList newNodes:(NSArray *)newNodes;
- (Node *)findBestNode:(NSDictionary *)openList;
- (NSArray *)computePath:(NSDictionary *)closedList arrived:(Position *)arrived;
- (void)computeDicrection;
- (void)makeAction;
- (NSInteger)computeLenghtPath:(NSArray *)pathValue;
- (Player *)findNearestEnemy;

@end
