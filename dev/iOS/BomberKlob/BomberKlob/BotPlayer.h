#import <Foundation/Foundation.h>
#import "Player.h"

@class ColisionMap, Node;

@interface BotPlayer : Player {
    
	NSString *difficulty;
    
    NSMutableDictionary *openList;
    NSMutableDictionary *closedList;
    NSMutableArray *path;
    
    ColisionMap *colisionMap;
    
    NSString *action;
}

@property (nonatomic, retain) ColisionMap *colisionMap;
@property (nonatomic, retain) NSString *action;

- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue colisionMap:(ColisionMap *)colisionMapValue;

- (void)pathFinding:(Position *)arrivedPosition;
- (void)updateOpenList:(NSArray *)newNodes;
- (Node *)findBestNode;
- (void)computePath:(Position *)arrived;
- (void)computeDicrection;
- (void)makeAction;

@end
