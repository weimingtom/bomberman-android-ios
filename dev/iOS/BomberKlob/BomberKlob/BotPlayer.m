#import "BotPlayer.h"
#import "Node.h"
#import "ColisionMap.h"
#import "RessourceManager.h"


@implementation BotPlayer

@synthesize colisionMap, action;


- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue colisionMap:(ColisionMap *)colisionMapValue {
    self = [super initWithImageName:imageNameValue position:positionValue];
    
    if (self) {
        difficulty = [[NSString alloc] initWithString:@"Hard"];
        openList = [[NSMutableDictionary alloc] init];
        closedList = [[NSMutableDictionary alloc] init];
        path = [[NSMutableArray alloc] init];
        self.colisionMap = colisionMapValue;
        self.action = [NSString stringWithString:@"left"];
        
        [self pathFinding:[[Position alloc] initWithX:5 y:5]];
        [self computeDicrection];
    }
    
    return self;
}


- (void)dealloc {
    [difficulty release];
    [openList release];
    [closedList release];
    [super dealloc];
}


- (void)pathFinding:(Position *)arrivedPosition {
    BOOL find = NO;
    BOOL unattainable = NO;
    Position *currentPosition = [[Position alloc] initWithX:(position.x / ressource.tileSize) y:(position.y / ressource.tileSize)];
    Node *currentNode = [[Node alloc] initWithPosition:currentPosition parent:nil costSinceStart:0 costUntilArrived:[colisionMap heuristicManhattan:currentPosition arrived:arrivedPosition]];
    
    [openList removeAllObjects];
    [closedList removeAllObjects];
    [closedList setObject:currentNode forKey:currentNode.position];
    
    while (!find && !unattainable) {
        [self updateOpenList:[colisionMap adjacentCases:currentNode arrived:arrivedPosition]];
        currentNode = [self findBestNode];
        [closedList setObject:currentNode forKey:currentNode.position];
        [openList removeObjectForKey:currentNode.position];
        
        if ([currentNode.position isEqual:arrivedPosition]) {
            find = YES;
        }
        else if ([closedList count] == [colisionMap nbCase]) {
            unattainable = YES;
        }
    }
    
    [self computePath:arrivedPosition];
}


- (void)updateOpenList:(NSArray *)newNodes {
    
    for (Node *node in newNodes) {
        if ([closedList objectForKey:node.position] == nil) {
            if ([openList objectForKey:node.position] != nil) {
                if ([node totalCost] < [[openList objectForKey:node.position] totalCost]) {
                    [openList setObject:node forKey:node.position];
                }
            }
            else {
                [openList setObject:node forKey:node.position];
            }
        }
    }
}


- (Node *)findBestNode {
    NSEnumerator *enumerator = [openList objectEnumerator];
    Node *bestNode = [enumerator nextObject];
    
    for (Position *currentPosition in openList) {
        if ([[openList objectForKey:currentPosition] totalCost] < [bestNode totalCost]) {
            bestNode = [openList objectForKey:currentPosition];
        }
    }
    
    return bestNode;
}


- (void)computePath:(Position *)arrived {
    BOOL find = NO;
    Node *currentNode = [closedList objectForKey:arrived];
    Node *parent;
    
    [path addObject:currentNode];
    parent = currentNode.parent;
    
    while (!find) {
        currentNode = [closedList objectForKey:((Node *)[closedList objectForKey:parent.position]).position];
        [path insertObject:currentNode atIndex:0];
        parent = currentNode.parent;
        
        if (parent == nil) {
            find = YES;
        }
    }
}


- (void)computeDicrection {
    Node *nextNode = [path objectAtIndex:1];
    Position *nextPosition = [[Position alloc] initWithX:(nextNode.position.x * ressource.tileSize) y:(nextNode.position.y * ressource.tileSize)];
    
//    NSLog(@"%@: %@", color, nextNode);
    
    if (nextPosition.x < position.x) {
        if (nextPosition.y < position.y) {
            action = [NSString stringWithString:@"leftTop"];
        }
        else if (nextPosition.y > position.y) {
            action = [NSString stringWithString:@"leftDown"];
        }
        else {
            action = [NSString stringWithString:@"left"];
        }
    }
    else if (nextPosition.x > position.x) {
        if (nextPosition.y < position.y) {
            action = [NSString stringWithString:@"rightTop"];
        }
        else if (nextPosition.y > position.y) {
            action = [NSString stringWithString:@"rightDown"];
        }
        else {
            action = [NSString stringWithString:@"right"];
        }
    }
    else {
        if (nextPosition.y < position.y) {
            action = [NSString stringWithString:@"top"];
        }
        else if (nextPosition.y > position.y) {
            action = [NSString stringWithString:@"down"];
        }
        else {
            action = [NSString stringWithString:@"stop"];
        }
    }
}


- (void)makeAction {
    Node *nextNode = [path objectAtIndex:1];
    Position *nextPosition = [[Position alloc] initWithX:(nextNode.position.x * ressource.tileSize) y:(nextNode.position.y * ressource.tileSize)];
    
    if ([position isEqual:nextPosition]) {
        [self pathFinding:[[Position alloc] initWithX:5 y:5]];
        [self computeDicrection];
    }
}


- (id)copyWithZone:(NSZone *)zone {
    BotPlayer *copy = [super copyWithZone:zone];

    difficulty = @"Hard";
    openList = [[NSMutableDictionary alloc] init];
    closedList = [[NSMutableDictionary alloc] init];
    path = [[NSMutableArray alloc] init];
    
	return copy;
}


@end
