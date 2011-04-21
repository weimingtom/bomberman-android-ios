//
//  Object.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Position;
@class RessourceManager;

@interface Object : NSObject <NSCoding> {
    
	RessourceManager *ressource;
    NSString *imageName;
	BOOL hit;
	NSUInteger level;
	BOOL fireWall;
	Position *position;
	
	NSDictionary * animations;
	NSString * currentAnimation;
	NSInteger currentFrame;
	NSInteger waitDelay;
	NSInteger delay;
}

@property (nonatomic, retain) NSString * imageName;
@property (nonatomic) BOOL hit;
@property (nonatomic) NSUInteger level;
@property (nonatomic) BOOL fireWall;
@property (nonatomic, retain) Position * position;

@property (nonatomic, retain) NSDictionary * animations ;
@property (nonatomic, retain) NSString * currentAnimation;
@property (nonatomic) NSInteger currentFrame;
@property (nonatomic) NSInteger waitDelay;
@property (nonatomic) NSInteger delay;

- (id)init;
- (void)resize;
- (void)update;
- (BOOL)hasAnimationFinished;
- (void)destroy;

- (void)draw:(CGContextRef)context;
- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha;

- (id)initWithImageName:(NSString *)anImageName position:(Position *)aPosition animations:(NSDictionary *)anAnimations;
- (void) update;
- (BOOL) hasAnimationFinished;

@end
