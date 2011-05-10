#import <Foundation/Foundation.h>

@class Position;

@interface Node : NSObject {
    
    Position *position;
    Node *parent;
    NSInteger costSinceStart;
    NSInteger costUntilArrived;
}

@property (nonatomic, retain) Position *position;
@property (nonatomic, retain) Node *parent;
@property (nonatomic, assign) NSInteger costSinceStart;
@property (nonatomic, assign) NSInteger costUntilArrived;

- (id)initWithPosition:(Position *)positionValue parent:(Node *)parentValue costSinceStart:(NSInteger)costSinceStartValue costUntilArrived:(NSInteger)costUntilArrivedValue;
- (id)initWithX:(NSInteger)x y:(NSInteger)y parent:(Node *)parentValue costSinceStart:(NSInteger)costSinceStartValue costUntilArrived:(NSInteger)costUntilArrivedValue;

- (NSInteger)totalCost;

@end
