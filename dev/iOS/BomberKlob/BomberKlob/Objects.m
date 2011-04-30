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

@synthesize imageName, hit, level, fireWall, position, animations,currentAnimation,currentFrame,waitDelay,delay, damages,idle,destroyAnimations,ressource,destroyable;

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


- (id)initWithImageName:(NSString *)anImageName position:(Position *)aPosition animations:(NSDictionary *)anAnimations {
	self = [super init];
	
	if (self) {
        self.imageName = anImageName;
        self.position = aPosition;
		ressource = [RessourceManager sharedRessource];
		self.animations = anAnimations;
		self.currentFrame = 0;
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

	if ([((AnimationSequence *)[animations objectForKey:imageName]).sequences count] == 1) {
		[idle drawInRect:CGRectMake(position.x, position.y, ressource.tileSize, ressource.tileSize)];
	}
	else if (!destroyable) {
		UIImage * image = [((AnimationSequence *)[animations objectForKey:imageName]).sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize, ressource.tileSize)];
	}
	else {
		UIImage * image = [((AnimationSequence *)[destroyAnimations objectForKey:imageName]).sequences objectAtIndex:currentFrame];
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
			if (delay == waitDelay) {
				delay = 0;
				if (currentFrame == [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]-1) {
					currentFrame = 0;
					if (!((AnimationSequence *)[animations objectForKey:imageName]).canLoop) {
						animationFinished = YES;
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
			if (delay == waitDelay) {
				delay = 0;
				if (currentFrame == [((AnimationSequence *)[destroyAnimations objectForKey:imageName]).sequences count]-1) {
					currentFrame = 0;
					if (!((AnimationSequence *)[animations objectForKey:imageName]).canLoop) {
						
						animationFinished = YES;
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

}

- (BOOL) hasAnimationFinished{
	return animationFinished;
}


- (id)initWithCoder:(NSCoder *)aDecoder {
    self = [super init];
    
    if (self) {
        ressource = [RessourceManager sharedRessource];
        
        self.imageName = [aDecoder decodeObjectForKey:@"imageName"];
        self.hit = [aDecoder decodeBoolForKey:@"hit"];
        self.position = [aDecoder decodeObjectForKey:@"position"];
        
        animations = [[NSMutableDictionary alloc] initWithDictionary:[ressource.bitmapsInanimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:imageName, nil]]];
    }
    
    return self;
}


- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:imageName forKey:@"imageName"];
    [aCoder encodeBool:hit forKey:@"hit"];
    [aCoder encodeObject:position forKey:@"position"];
}


- (id)copyWithZone:(NSZone *)zone {
    Objects *copy = [[Objects alloc] init];
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
    copy.damages = damages;
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
	destroyable = YES;
}

@end
