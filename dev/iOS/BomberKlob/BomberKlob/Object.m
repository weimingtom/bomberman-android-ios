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

@synthesize imageName, hit, level, fireWall, position, animations,currentAnimation,currentFrame,waitDelay,delay;

- (id)init {
	self = [super init];
	
	if (self) {
		ressource = [RessourceManager sharedRessource];
        position = [[Position alloc] init];
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

- (void) destroy{
	
	
}

- (NSString *)description{
	NSString * desc = [NSString stringWithFormat:@"ImageName : %@ hit : %d, Level : %d, FireWall : %d, X :  %d  Y : %d",imageName, hit, level, fireWall, position.x, position.y];
	return desc;
}


- (void)draw:(CGContextRef)context {
	/*for (NSString * key in animations) {
		NSLog(@"Test : %@  ",[animations objectForKey:key]);
	}*/
	if (currentFrame < [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]){
		UIImage * image = [((AnimationSequence*)[animations objectForKey:imageName]).sequences objectAtIndex:currentFrame];
		[image drawInRect:CGRectMake(position.x, position.y, ressource.tileSize , ressource.tileSize)];
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
	if ([((AnimationSequence *)[animations objectForKey:imageName]).sequences count] > 1) {
		if (delay == waitDelay) {
			delay = 0;
			if (currentFrame == [((AnimationSequence *)[animations objectForKey:imageName]).sequences count]-1) {
				currentFrame = -1;
			}
			else
				currentFrame++;
		}
		else{
			delay++;
		}
	}
}

- (BOOL) hasAnimationFinished{
	
	if (currentFrame < 0) {
		return true;
	}
	else
		return false;
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

@end
