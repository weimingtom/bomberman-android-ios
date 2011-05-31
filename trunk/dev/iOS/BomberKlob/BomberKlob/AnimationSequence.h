#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>

/** The `AnimationSequence` class allows to represent an animation sequence of tile. */
@interface AnimationSequence : NSObject {
    NSMutableArray *sequences;
	BOOL canLoop;
	NSInteger delayNextFrame;
	NSString *name;
	AVAudioPlayer *sound;
}

/** The Animation Sequence.*/
@property (nonatomic,retain) NSMutableArray* sequences;

/** Allows to know if the Animation sequence can loop or not.*/
@property (nonatomic) BOOL canLoop;

/** Allows to know the name of the Animation sequence.*/
@property (nonatomic, retain) NSString * name;

/** Allows to know the interval delay between two frames (tiles).*/
@property (nonatomic) NSInteger delayNextFrame;

/** The Animation sequence's sound.*/
@property (nonatomic, retain) AVAudioPlayer *sound;


///------------------------
/// @name Initialize the AnimationSequence
///------------------------

/** Allows to init a basic Animation Sequence.
 
 @return The AnimationSequence.
 */
- (id) init;

/** Allows to init an Animation Sequence which can loop or not.
 
 @param canLoopValue `YES`if the AnimationSequence can loop, `NO`otherwise.
 @return The AnimationSequence.
 */
- (id) initWithLoop:(BOOL)canLoopValue;


///------------------------
/// @name Manage the AnimationSequence
///------------------------


/** Allows to add a frame to the AnimationSequence.
 
 @param imageValue the frame to add to the AnimationSequence.
 */
- (void) addImageSequence:(UIImage *) imageValue;


/** Allows to play the sound of the AnimationSequence.*/
- (void) playSound;

/** Allows to know if the AnimationSequence is animated or not.
 
 @return `YES`if the AnimationSequence is unanimated, `NO`otherwise.
 */
- (BOOL)isUnanimated;



@end
