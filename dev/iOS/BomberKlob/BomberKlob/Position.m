//
//  Position.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Position.h"


@implementation Position

@synthesize x, y;

- (id) init {
	self = [super init];
	
    if (self) {
        
    }
    
	return self;
}


- (id) initWithX:(NSInteger)aX y:(NSInteger)aY {
	self = [super init];
	if (self){
		x = aX;
		y = aY;
	}
	return self;
}


- (NSString *)description {
	NSString * desc = [NSString stringWithFormat:@"X : %d \n Y : %d",x,y];
	return desc;
}


- (BOOL)isEqual:(id)object {
    return (x == ((Position *) object).x) && (y == ((Position *) object).y);
}


- (id)initWithCoder:(NSCoder *)aDecoder {
    self = [super init];
    
    if (self) {
        self.x = [aDecoder decodeIntegerForKey:@"x"];
        self.y = [aDecoder decodeIntegerForKey:@"y"];
    }
    
    return self;
}


- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeInteger:x forKey:@"x"];
    [aCoder encodeInteger:y forKey:@"y"];
}

@end
