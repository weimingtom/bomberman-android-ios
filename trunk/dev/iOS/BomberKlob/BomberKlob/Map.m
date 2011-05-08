#import "Map.h"


@implementation Map

@synthesize name, width, height, players;

- (id)init {
    self = [super init];
    
    if (self) {
        
    }
    
    return self;
}


- (void)dealloc {
    [name release];
    [players release];
    [super dealloc];
}

@end
