#import <Foundation/Foundation.h>


@interface RessourceManager : NSObject {
	NSInteger tileSize;
	NSInteger screenHeight;
	NSInteger screenWidth;

	NSMutableDictionary * bitmapsAnimates;
	NSMutableDictionary * bitmapsPlayer;
	NSMutableDictionary * bitmapsBombs;
	NSMutableDictionary * bitmapsInformationGameView;

}
@property (nonatomic, retain) NSMutableDictionary * bitmapsAnimates;
@property (nonatomic, retain) NSMutableDictionary * bitmapsPlayer;
@property (nonatomic, retain) NSMutableDictionary * bitmapsBombs;
@property (nonatomic, retain) NSMutableDictionary  * bitmapsInformationGameView;

@property (nonatomic) NSInteger tileSize;
@property (nonatomic) NSInteger screenHeight;
@property (nonatomic) NSInteger screenWidth;


+(RessourceManager*)sharedRessource;

+ (id) alloc;

- (id) init;

- (void) loadProperty;

- (void) loadPlayer;

- (void) loadBombs;

- (void) loadObjects;

- (void) update;

- (void) hasAnimationFinished;

- (void) destroy;

@end
