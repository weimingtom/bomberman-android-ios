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

@interface ColisionCase : NSObject {
    
    NSMutableArray *counters;
    NSMutableArray *types;
}

- (id)initWithType:(CaseType)typeValue;

- (void)addValue:(CaseType)typeValue;
- (void)removeValue:(CaseType)typeValue;

- (void)draw:(CGContextRef)context x:(NSInteger)x y:(NSInteger)y;

- (BOOL)isTraversableByPlayer;
- (BOOL)isTraversableByFire;
- (BOOL)isUndestructibleBlock;
- (BOOL)isDestructibleBlock;
- (BOOL)isBomb;
- (BOOL)isFire;
- (BOOL)isDangerousArea;

@end
