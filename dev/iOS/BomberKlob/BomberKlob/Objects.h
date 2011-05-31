#import <Foundation/Foundation.h>

@class Position;
@class RessourceManager;

/** The `Objects` class allows to represent an Object. */
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

/** The object's name. */
@property (nonatomic, retain) NSString * imageName;

/** Allows to know if the object is traversable by a player. */
@property (nonatomic) BOOL hit;

/** Allows to know if the object is on the ground or not. */
@property (nonatomic) NSInteger level;

/** Allows to know if the object let pass the bomb's fire. */
@property (nonatomic) BOOL fireWall;

/** Allows to know if the object should be destroy. */
@property (nonatomic) BOOL destroyable;

/** Allows to know the damages that the object do. */
@property (nonatomic) NSInteger damage;

/** Allows to know the position of the object. */
@property (nonatomic, retain) Position * position;

/** Allows to get ressources. */
@property (nonatomic, assign) RessourceManager * ressource;

/** Contain the object's AnimationSequence. */
@property (nonatomic, retain) NSMutableDictionary * animations ;

/** Contain the object's destroy AnimationSequence. */
@property (nonatomic, retain) NSMutableDictionary * destroyAnimations ;

/** Contain the object's Idle image. */
@property (nonatomic, retain) UIImage *idle;

/** The current animation direction. */
@property (nonatomic, retain) NSString * currentAnimation;

/** The number of the current animation. */
@property (nonatomic) NSInteger currentFrame;

/** The delay after the next frame of the current AnimationSequence. */
@property (nonatomic) NSInteger waitDelay;

/** The delay after the next frame of the current AnimationSequence. */
@property (nonatomic) NSInteger delay;

/** A boolean for know if the current Animation is finished or not. */
@property (nonatomic) BOOL animationFinished;

///------------------------
/// @name Initialize the Object
///------------------------

/** Allows to init an Object with a type and a position.
 
 @param imageNameValue the type of the Object.
 @param positionValue the initial position of the Object.
 @return The Object.
 */
- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue;



///------------------------
/// @name Manage the Object
///------------------------

/** Allows to update the object. */
- (void)update;

/** Allows to destroy the object. */
- (void)destroy;

/** Allows to update the object. */
- (void) update;

/** Allows to know if the current animation of the object is finished. 
 
 @return `YES' if the current animation has finished, `NO`otherwise
 */
- (BOOL)hasAnimationFinished;

/** Allows to compare the name with an another object. 
 
 @return `YES' if the two imageName are equals, `NO`otherwise
 */
- (NSComparisonResult)compareImageName:(Objects *)object;

/** Allows to know if the object is unanimated. 
 
 @return `YES' if the object is unanimated, `NO`otherwise
*/
- (BOOL)isUnanimated;


///------------------------
/// @name Draw the Object
///------------------------

/** Allows to draw the object.
 
 @param context The graphic's context for draw the object.
 */
- (void)draw:(CGContextRef)context;

/** Allows to draw the object with an alpha value.
 
 @param context The graphic's context for draw the player.
 @param alpha The alpha value for draw the object.
 */
- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha;

@end
