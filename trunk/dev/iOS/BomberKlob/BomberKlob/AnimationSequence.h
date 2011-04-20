//
//  AnimationSequence.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface AnimationSequence : NSObject {
    NSMutableArray * sequences;
	BOOL canLoop;
	NSString * name;
}

@property (nonatomic,retain) NSMutableArray* sequences;
@property (nonatomic) BOOL canLoop;
@property (nonatomic, retain) NSString * name;

- (id) init;

- (id) initWithNameAndLoop:(NSString *) nameValue:(BOOL)canLoopValue;

- (void) addImageSequence:(CGImageRef *) imageValue;

- (NSString *)description;


@end
