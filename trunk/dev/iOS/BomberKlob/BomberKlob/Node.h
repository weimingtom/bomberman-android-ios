#import <Foundation/Foundation.h>

@class Position;

/** The `Node` class aims to manage the node to compute the pathfinding A*. */
@interface Node : NSObject {
    
    Position *position;
    Node *parent;
    NSInteger costSinceStart;
    NSInteger costUntilArrived;
}

/** The node position. */
@property (nonatomic, retain) Position *position;

/** The node parent. */
@property (nonatomic, retain) Node *parent;

/** The cost to go to this node since start. */
@property (nonatomic, assign) NSInteger costSinceStart;

/** The cost to go until arrived thanks to the heuristic (Manhattan). */
@property (nonatomic, assign) NSInteger costUntilArrived;


///---------------------------------
/// @name Initializing a Node Object
///---------------------------------

/** Initializes and returns a newly allocated `Node` object with position, parent, cost to go to this node since start and cost to go until arrived.
 
 @param positionValue The node position.
 @param parentValue The node parent.
 @param costSinceStartValue The cost to go to this node since start.
 @param costUntilArrivedValue The cost to go until arrived thanks to the heuristic (Manhattan).
 @see initWithX:y:parent:costSinceStart:costUntilArrived:
 */
- (id)initWithPosition:(Position *)positionValue parent:(Node *)parentValue costSinceStart:(NSInteger)costSinceStartValue costUntilArrived:(NSInteger)costUntilArrivedValue;

/** Initializes and returns a newly allocated `Node` object with abscissa, orderly, parent, cost to go to this node since start and cost to go until arrived.
 
 @param x The abscissa position.
 @param y The orderly position.
 @param parentValue The node parent.
 @param costSinceStartValue The cost to go to this node since start.
 @param costUntilArrivedValue The cost to go until arrived thanks to the heuristic (Manhattan).
 @see initWithPosition:parent:costSinceStart:costUntilArrived:
 */
- (id)initWithX:(NSInteger)x y:(NSInteger)y parent:(Node *)parentValue costSinceStart:(NSInteger)costSinceStartValue costUntilArrived:(NSInteger)costUntilArrivedValue;


///-------------------
/// @name Other Method
///-------------------

/** Retruns the addition of `costSinceStartValue` and `costUntilArrivedValue`.
 
 @return The addition of `costSinceStartValue` and `costUntilArrivedValue`.
 */
- (NSInteger)totalCost;

@end
