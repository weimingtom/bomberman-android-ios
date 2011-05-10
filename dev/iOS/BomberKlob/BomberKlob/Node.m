#import "Node.h"
#import "Position.h"


@implementation Node

@synthesize position, parent, costSinceStart, costUntilArrived;


- (id)initWithPosition:(Position *)positionValue parent:(Node *)parentValue costSinceStart:(NSInteger)costSinceStartValue costUntilArrived:(NSInteger)costUntilArrivedValue {
    self = [super init];
    
    if (self) {
        self.position = positionValue;
        self.parent = parentValue;
        self.costSinceStart = costSinceStartValue;
        self.costUntilArrived = costUntilArrivedValue;
    }
    
    return self;
}


- (id)initWithX:(NSInteger)x y:(NSInteger)y parent:(Node *)parentValue costSinceStart:(NSInteger)costSinceStartValue costUntilArrived:(NSInteger)costUntilArrivedValue {
    self = [super init];
    
    if (self) {
        position = [[Position alloc] initWithX:x y:y];
        self.parent = parentValue;
        self.costSinceStart = costSinceStartValue;
        self.costUntilArrived = costUntilArrivedValue;
    }
    
    return self;
}


- (void)dealloc {
    [position release];
    [parent release];
    [super dealloc];
}


- (NSInteger)totalCost {
    return (costSinceStart + costUntilArrived);
}


- (NSString *)description {
    return [NSString stringWithFormat:@"Position: (%d,%d), Parent: [%@], CostSinceStart: %d, CostUntilArrived: %d", position.x, position.y, parent, costSinceStart, costUntilArrived];
}

@end
