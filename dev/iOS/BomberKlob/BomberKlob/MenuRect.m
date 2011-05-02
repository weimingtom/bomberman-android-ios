#import "MenuRect.h"


@implementation MenuRect

@synthesize x, y, width, height;


- (id)initWithPoint:(CGPoint)point width:(NSInteger)widthValue height:(NSInteger)heightValue {
    self = [super init];
    
    if (self) {
        self.x = point.x;
        self.y = point.y;
        self.width = widthValue;
        self.height = heightValue;
    }
    
    return self;
}


- (id)initWithX:(NSInteger)xValue y:(NSInteger)yValue width:(NSInteger)widthValue height:(NSInteger)heightValue {
    self = [super init];
    
    if (self) {
        self.x = xValue;
        self.y = yValue;
        self.width = widthValue;
        self.height = heightValue;
    }
    
    return self;
}


- (CGRect)getCGRect {
    return CGRectMake(x, y, width, height);
}


- (NSString *)description {
    return [NSString stringWithFormat:@"x: %d, y: %d, width: %d, height: %d", x, y, width, height];
}

@end
