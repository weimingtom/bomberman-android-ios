#import <Foundation/Foundation.h>
#import "Map.h"

@class ColisionMap, Bomb, Objects, Position;


@interface GameMap : Map {
    
    UIImage *bitmap;
    NSMutableDictionary *animatedObjects;
    NSMutableDictionary *animatedObjectsInitial;
    ColisionMap *colisionMap;
}

@property (nonatomic, retain) UIImage *bitmap;
@property (nonatomic, retain) NSMutableDictionary *animatedObjects;
@property (nonatomic, retain) NSMutableDictionary *animatedObjectsInitial;
@property (nonatomic, retain) ColisionMap *colisionMap;

- (void)load;
- (void)makeBitmap:(NSArray *)grounds blocks:(NSArray *)blocks;

- (void)draw:(CGContextRef)context;
- (void)drawBitmap:(CGContextRef)context grounds:(NSArray *)grounds blocks:(NSArray *)blocks;

- (void)update;
- (void)restart;

- (void)addAnimatedObject:(Objects *)object ;
- (void)deleteAnimatedObject:(Position *)position;

- (void)bombPlanted:(Bomb *)bomb;
- (void)bombExplode:(Bomb *)bomb;

@end
