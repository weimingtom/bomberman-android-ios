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

@interface Objects : NSObject <NSCoding, NSCopying> {
    
	RessourceManager *ressource;
    NSString *imageName;
	BOOL hit;
	NSInteger level;
	BOOL fireWall;
	NSInteger damages;
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

@property (nonatomic) NSInteger damages;
@property (nonatomic, retain) Position * position;
@property (nonatomic, assign) RessourceManager * ressource;


@property (nonatomic, retain) NSMutableDictionary * animations ;
@property (nonatomic, retain) NSMutableDictionary * destroyAnimations ;
@property (nonatomic, assign) UIImage *idle;

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
