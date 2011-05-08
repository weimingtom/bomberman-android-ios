//
//  AnimationSequence.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>


@interface AnimationSequence : NSObject {
    NSMutableArray *sequences;
	BOOL canLoop;
	NSInteger delayNextFrame;
	NSString *name;
	AVAudioPlayer *sound;
}

@property (nonatomic,retain) NSMutableArray* sequences;
@property (nonatomic) BOOL canLoop;
@property (nonatomic, retain) NSString * name;
@property (nonatomic) NSInteger delayNextFrame;
@property (nonatomic, retain) AVAudioPlayer *sound;

- (id) init;

- (id) initWithLoop:(BOOL)canLoopValue;

- (void) addImageSequence:(UIImage *) imageValue;

- (NSString *)description;
- (void) playSound;

- (BOOL)isUnanimated;


@end
