#import <Foundation/Foundation.h>

@class Position;
@class RessourceManager;

@interface Objects : NSObject <NSCoding, NSCopying> {
    
	RessourceManager *ressource;
    NSString *imageName;
	BOOL hit;
	NSInteger level;
	BOOL fireWall;
	NSInteger damage;
	Position *position;
	
	NSMutableDictionary * animations;
	NSMutableDictionary * destroyAnimations;
	UIImage *idle;

	NSString *currentAnimation;
	NSInteger currentFrame;
	NSInteger waitDelay;
	NSInteger delay;
	BOOL destroyable;
	BOOL animationFinished;
}

@property (nonatomic, retain) NSString * imageName;
@property (nonatomic) BOOL hit;
@property (nonatomic) NSInteger level;
@property (nonatomic) BOOL fireWall;
@property (nonatomic) BOOL destroyable;

@property (nonatomic) NSInteger damage;
@property (nonatomic, retain) Position * position;
@property (nonatomic, assign) RessourceManager * ressource;


@property (nonatomic, retain) NSMutableDictionary * animations ;
@property (nonatomic, retain) NSMutableDictionary * destroyAnimations ;
@property (nonatomic, retain) UIImage *idle;

@property (nonatomic, retain) NSString * currentAnimation;
@property (nonatomic) NSInteger currentFrame;
@property (nonatomic) NSInteger waitDelay;
@property (nonatomic) NSInteger delay;
@property (nonatomic) BOOL animationFinished;

- (id)init;
- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue;
- (void)resize;
- (void)update;
- (BOOL)hasAnimationFinished;
- (void)destroy;

- (void)draw:(CGContextRef)context;
- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha;
- (void) update;
- (BOOL) hasAnimationFinished;

- (NSComparisonResult)compareImageName:(Objects *)object;
- (BOOL)isUnanimated;

@end
