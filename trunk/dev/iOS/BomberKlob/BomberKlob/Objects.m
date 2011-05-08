//
//  Object.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Objects.h"
#import "Position.h"
#import "RessourceManager.h"
#import "AnimationSequence.h"


@implementation Objects

@synthesize imageName, hit, level, fireWall, position, animations,currentAnimation,currentFrame,waitDelay,delay, damage,idle,destroyAnimations,ressource,destroyable, animationFinished;

- (id)init {
	self = [super init];
	
	if (self) {
		ressource = [RessourceManager sharedRessource];
        position = [[Position alloc] init];
		animations = [[NSMutableDictionary alloc] init];
		destroyAnimations = [[NSMutableDictionary alloc] init];
		currentAnimation = @"idle";
	}
	
	return self;
}


- (void)dealloc {
    [imageName release];
    [position release];
    [animations release];
    [currentAnimation release];
    [super dealloc];
}


- (void) resize{
	
}

- (NSString *)description{
	NSString * desc = [NSString stringWithFormat:@"ImageName : %@ hit : %d, Level : %d, FireWall : %d, X :  %d  Y : %d",imageName, hit, level, fireWall, position.x, position.y];
	return desc;
}


- (void)draw:(CGContextRef)context {

	if (destroyable){
		UIImage * image = [((AnimationSequence *)[destroyAnimations objectForKey:imageName]).sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize, ressource.tileSize)];
	}
	else if ([((AnimationSequence *)[animations objectForKey:imageName]).sequences count] == 1) {
		[idle drawInRect:CGRectMake(position.x, position.y, ressource.tileSize, ressource.tileSize)];
	}
	else {
		UIImage * image = [((AnimationSequence *)[animations objectForKey:imageName]).sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize, ressource.tileSize)];
	}
}


- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha {	

	if (currentFrame < [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]){
		UIImage * image = [((AnimationSequence*)[animations objectForKey:imageName]).sequences objectAtIndex:currentFrame];
		    [image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize, ressource.tileSize) blendMode:kCGBlendModeNormal alpha:alpha];
	}
}

- (void) update{	
	if (!destroyable) {
		if ([((AnimationSequence *)[animations objectForKey:imageName]).sequences count] > 2) {
			if (delay == ((AnimationSequence *)[animations objectForKey:imageName]).delayNextFrame) {
				delay = 0;
				if (currentFrame == [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]-1 ) {
					if (!((AnimationSequence *)[animations objectForKey:imageName]).canLoop) {
						animationFinished = YES;
					}
					else{
						currentFrame = 0;
					}
				}
				else
					currentFrame++;
			}
			else{
				delay++;
			}
		}
	}
	else {
		if ([((AnimationSequence *)[destroyAnimations objectForKey:imageName]).sequences count] > 2) {
			if (delay == ((AnimationSequence *)[destroyAnimations objectForKey:imageName]).delayNextFrame) {
				delay = 0;
				if (currentFrame == [((AnimationSequence *)[destroyAnimations objectForKey:imageName]).sequences count]-1) {
					if (((AnimationSequence *)[destroyAnimations objectForKey:imageName]).canLoop == 0) {
						animationFinished = YES;
					}
					else {
						currentFrame = 0;
					}
						
				}
				else {
					currentFrame++;
				}
			}
			else{
				delay++;
			}
		}
	}

}

- (BOOL) hasAnimationFinished{
	return animationFinished;
}


- (id)initWithCoder:(NSCoder *)aDecoder {
    self = [super init];
    
    if (self) {
        Objects *object;
        ressource = [RessourceManager sharedRessource];
        
        self.imageName = [aDecoder decodeObjectForKey:@"imageName"];
        self.position = [aDecoder decodeObjectForKey:@"position"];
        
        object = [[[RessourceManager sharedRessource].bitmapsAnimates objectForKey:imageName] copy]; 
        
        self.hit = object.hit;
        self.level = object.level;
        self.fireWall = object.fireWall;
        self.damage = object.damage;
        self.animations = object.animations;
        self.destroyAnimations = object.destroyAnimations;
        self.idle = object.idle;
        self.currentAnimation = object.currentAnimation;
        self.waitDelay = object.waitDelay;
        self.delay = object.delay;
        self.destroyable = object.destroyable;
        self.animationFinished = object.animationFinished;
        
        [object release];
    }
    
    return self;
}


- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:imageName forKey:@"imageName"];
    [aCoder encodeObject:position forKey:@"position"];
}


- (id)copyWithZone:(NSZone *)zone {
    Objects *copy = [[[self class] alloc] init];
    
    NSString *imageNameCopy = [[NSString alloc] initWithString:imageName];
    Position *positionCopy = [[Position alloc] initWithPosition:position];
    NSMutableDictionary *animationCopy = [[NSMutableDictionary alloc] initWithDictionary:animations];
    NSMutableDictionary *destroyAnimationsCopy = [[NSMutableDictionary alloc] initWithDictionary:destroyAnimations];
    NSString *currentAnimationCopy = [[NSString alloc] initWithString:currentAnimation];
    
    copy.ressource = ressource;
    copy.imageName = imageNameCopy;
    copy.hit = hit;
    copy.level = level;
    copy.fireWall = fireWall;
    copy.damage = damage;
    copy.position = positionCopy;
    copy.animations = animationCopy;
    copy.destroyAnimations = destroyAnimationsCopy;
    copy.idle = idle;
    copy.currentAnimation = currentAnimationCopy;
    copy.currentFrame = currentFrame;
    copy.waitDelay = waitDelay;
    copy.delay = delay;
    copy.destroyable = destroyable;
    
    [imageNameCopy release];
    [positionCopy release];
    [animationCopy release];
    [destroyAnimationsCopy release];
    [currentAnimationCopy release];
    
    return copy;
}


- (void) destroy{
	if ([((AnimationSequence *)[destroyAnimations objectForKey:imageName]).sequences count] > 0) {
		destroyable = YES;
		currentFrame = 0;
		[(AnimationSequence *)[destroyAnimations objectForKey:imageName] playSound];
	}
}


- (NSComparisonResult)compareImageName:(Objects *)object {
    
    return [imageName compare:object.imageName];
}


- (BOOL)isUnanimated {
    
    if ([animations count] == 1) {
        for (id key in destroyAnimations) {
            if (![[destroyAnimations objectForKey:key] isUnanimated]) {
                return NO;
            }
        }
    
        return YES;
    }
    
    return NO;
}


@end
