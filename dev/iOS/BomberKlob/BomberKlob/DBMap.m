#import "DBMap.h"
#import "DBUser.h"


@implementation DBMap

@synthesize name;
@synthesize owner;
@synthesize official;

- (id)initWithName:(NSString *)aName owner:(NSUInteger)idOwner official:(BOOL)anOfficial {
    self = [super init]; 
    
    if (self) {
        self.name = aName;
        self.owner = [[DBUser alloc] initWithId:idOwner];
        official = anOfficial;
    }
    
    return self;
}


- (void)dealloc {
    [name release];
    [owner release];
    [super dealloc];
}


- (NSString *)description {
    return [NSString stringWithFormat:@"Name: %@\nId owner: %@\nOfficiel: %d", name, owner, official];
}

@end