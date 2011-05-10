#import <Foundation/Foundation.h>

@class EditorMap, Bomb, Objects, Position, Node;


@interface ColisionMap : NSObject {
    
    NSMutableArray *map;
    NSMutableArray *bombs;
}

- (id)initWithMap:(EditorMap *)mapValue;

- (void)initMap:(EditorMap *)mapValue;
- (void)draw:(CGContextRef)context;

- (void)addDangerousArea:(Bomb *)bomb;
- (void)updateDangerousArea:(BOOL)remove;
- (void)deleteDangerousArea:(Bomb *)bomb;

- (void)addFire:(Position *)position;
- (void)deleteObject:(Objects *)object;

- (void)bombPlanted:(Bomb *)bomb;
- (void)bombExploded:(Bomb *)bomb;

- (BOOL)isTraversableByPlayer:(NSInteger)i j:(NSInteger)j;
- (BOOL)isTraversableByFire:(NSInteger)i j:(NSInteger)j;
- (BOOL)isBomb:(NSInteger)i j:(NSInteger)j;
- (BOOL)isFire:(NSInteger)i j:(NSInteger)j;
- (BOOL)isDangerousArea:(NSInteger)i j:(NSInteger)j;

- (NSArray *)adjacentCases:(Node *)node arrived:(Position *)arrived;
- (NSInteger)heuristicManhattan:(Position *)start arrived:(Position *)arrived;

- (NSInteger)nbCase;

@end
