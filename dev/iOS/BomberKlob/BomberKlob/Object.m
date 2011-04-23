//
//  Object.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Object.h"
#import "Position.h"
#import "RessourceManager.h"
#import "AnimationSequence.h"


@implementation Object

@synthesize imageName, hit, level, fireWall, position, animations,currentAnimation,currentFrame,waitDelay,delay, damages,idle,destroyAnimations,ressource,destroy;

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
	else if (!destroy) {
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
		    [image drawInRect:CGRectMake(ressource.tileSize * position.x, ressource.tileSize * position.y, ressource.tileSize, ressource.tileSize) blendMode:kCGBlendModeNormal alpha:alpha];
	}
    
//    [image drawInRect:CGRectMake(ressource.tileSize * position.x, ressource.tileSize * position.y, ressource.tileSize, ressource.tileSize)];
}

- (void) update{	
	if (!destroy) {
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
    }
    
    return self;
}


- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:imageName forKey:@"imageName"];
    [aCoder encodeBool:hit forKey:@"hit"];
    [aCoder encodeObject:position forKey:@"position"];
}

- (Object *) copy{
	Object * objectCopy = [[Object alloc] init];
	objectCopy.ressource = ressource;
    objectCopy.imageName = [[NSString alloc] initWithString:imageName];
	objectCopy.hit = hit;
	objectCopy.level = level;
	objectCopy.fireWall = fireWall;
	objectCopy.damages = damages;
	objectCopy.position = [[Position alloc] initWithPosition:position];
	
	objectCopy.animations = [[NSMutableDictionary alloc] initWithDictionary:animations];
	objectCopy.destroyAnimations = [[NSMutableDictionary alloc] initWithDictionary:destroyAnimations];
	objectCopy.idle = idle;
	
	objectCopy.currentAnimation = [[NSString alloc] initWithString:currentAnimation];
	objectCopy.currentFrame = currentFrame;
	objectCopy.waitDelay = waitDelay;
	objectCopy.delay = delay;
	objectCopy.destroy = destroy;
	
	return objectCopy;
}

- (void) destroy{
	destroy = YES;
	
}

@end
