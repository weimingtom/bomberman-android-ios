#import <Foundation/Foundation.h>
#import "Map.h"

#define WIDTH  21
#define HEIGHT 14

@class Objects, Position, Animated, DBUser;


@interface EditorMap : Map <NSCoding> {
 
	NSMutableArray *grounds;
	NSMutableArray *blocks;
}

@property (nonatomic, retain) NSMutableArray *grounds;
@property (nonatomic, retain) NSMutableArray *blocks;

- (id)initWithMapName:(NSString *)mapName;

- (void)initBasicMap;
- (void)save:(DBUser *)owner;
- (void)load;
- (void)makePreview;

- (void)addGround:(Objects *)ground position:(Position *)position;
- (void)addBlock:(Objects *)block position:(Position *)position;
- (void)addPlayer:(Position *)position color:(NSString *)color;
- (void)deleteBlockAtPosition:(Position *)position;
- (void)deletePlayerAtPosition:(Position *)position;

- (void)draw:(CGContextRef)context;
- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha;
- (void)drawPlayers:(CGContextRef)context;
- (void)drawPlayers:(CGContextRef)context alpha:(CGFloat)alpha;
- (void)drawMapAndPlayers:(CGContextRef)context alpha:(CGFloat)alpha;

- (BOOL)isEmpty:(Position *)position;
- (BOOL)thereIsBlock:(Position *)position;
- (BOOL)thereIsPlayer:(Position *)position;

@end
