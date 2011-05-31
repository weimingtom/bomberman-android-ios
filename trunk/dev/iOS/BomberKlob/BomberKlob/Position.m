#import "Position.h"


@implementation Position

@synthesize x, y;


- (id) init {
	self = [super init];
	
    if (self) {
        
    }
    
	return self;
}


- (id) initWithPosition:(Position *) position{
	self = [super init];
	if (self) {
		x = position.x;
		y = position.y;
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


- (void)dealloc {
    [super dealloc];
}


- (NSString *)description {
	NSString * desc = [NSString stringWithFormat:@"x: %d y: %d",x,y];
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


- (id)copyWithZone:(NSZone *)zone {
    Position *copy = [[[self class] allocWithZone:zone] initWithX:x y:y];
    
    return copy;
}


- (NSUInteger)hash {
    return [[self description] hash];
}



@end
