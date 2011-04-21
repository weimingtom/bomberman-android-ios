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


@implementation Object

@synthesize imageName, hit, level, fireWall, position;

- (id)init {
	self = [super init];
	
	if (self) {
		ressource = [RessourceManager sharedRessource];
        position = [[Position alloc] init];
	}
	
	return self;
}


- (id)initWithImageName:(NSString *)anImageName position:(Position *)aPosition {
	self = [super init];
	
	if (self) {
        self.imageName = anImageName;
        self.position = aPosition;
		ressource = [RessourceManager sharedRessource];
	}
	
	return self;
}

- (void) resize{
	
}

- (void) update{
	
}

- (BOOL) hasAnimationFinished{
	
	return NO;
}

- (void) destroy{
	
	
}

- (NSString *)description{
	NSString * desc = [NSString stringWithFormat:@"ImageName : %@ hit : %d, Level : %d, FireWall : %d, X :  %d  Y : %d",imageName, hit, level, fireWall, position.x, position.y];
	return desc;
}


- (void)draw:(CGContextRef)context {
    UIImage *image = [ressource.bitmapsInanimates valueForKey:imageName];
    [image drawInRect:CGRectMake(ressource.tileSize * position.x, ressource.tileSize * position.y, ressource.tileSize, ressource.tileSize)];
}


- (void)draw:(CGContextRef)context alpha:(CGFloat)alpha {
    UIImage *image = [ressource.bitmapsInanimates valueForKey:imageName];
    [image drawInRect:CGRectMake(ressource.tileSize * position.x, ressource.tileSize * position.y, ressource.tileSize, ressource.tileSize) blendMode:kCGBlendModeNormal alpha:alpha];
    
//    [image drawInRect:CGRectMake(ressource.tileSize * position.x, ressource.tileSize * position.y, ressource.tileSize, ressource.tileSize)];
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
