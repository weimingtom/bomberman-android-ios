#import "BotPlayer.h"
#import "Node.h"
#import "ColisionMap.h"
#import "RessourceManager.h"


@implementation BotPlayer

@synthesize colisionMap, movement, plantingBomb, enemies, path;


- (id)initWithImageName:(NSString *)imageNameValue position:(Position *)positionValue colisionMap:(ColisionMap *)colisionMapValue {
    self = [super initWithImageName:imageNameValue position:positionValue];
    
    if (self) {
        difficulty = [[NSString alloc] initWithString:@"Hard"];
        path = [[NSMutableArray alloc] init];
        self.colisionMap = colisionMapValue;
        self.movement = [NSString stringWithString:@"left"];
        self.plantingBomb = NO;
        beInDanger = NO;

        self.path = [self pathFinding:[[Position alloc] initWithX:5 y:5]];
        [self computeDicrection];
    }
    
    return self;
}


- (void)dealloc {
    [difficulty release];
    [super dealloc];
}


- (NSArray *)pathFinding:(Position *)arrivedPosition {
    Position *currentPosition = [[Position alloc] initWithX:(position.x / ressource.tileSize) y:(position.y / ressource.tileSize)];
    
    if (![currentPosition isEqual:arrivedPosition]) {
        BOOL find = NO;
        BOOL unattainable = NO;
        Node *currentNode = [[Node alloc] initWithPosition:currentPosition parent:nil costSinceStart:0 costUntilArrived:[colisionMap heuristicManhattan:currentPosition arrived:arrivedPosition]];
        NSMutableDictionary *openList = [[NSMutableDictionary alloc] init];
        NSMutableDictionary *closedList = [[NSMutableDictionary alloc] init];
        
        [closedList setObject:currentNode forKey:currentNode.position];
        
        while (!find && !unattainable) {
            [self updateOpenList:openList closedList:closedList newNodes:[colisionMap adjacentCases:currentNode arrived:arrivedPosition]];
            currentNode = [self findBestNode:openList];
            [closedList setObject:currentNode forKey:currentNode.position];
            [openList removeObjectForKey:currentNode.position];
            
            if ([currentNode.position isEqual:arrivedPosition]) {
                find = YES;
            }
            else if ([closedList count] == [colisionMap nbCase]) {
                unattainable = YES;
            }
        }
        
        return [self computePath:closedList arrived:arrivedPosition];
    }
    else {
        return nil;
    }
}


- (void)updateOpenList:(NSMutableDictionary *)openList closedList:(NSMutableDictionary *)closedList newNodes:(NSArray *)newNodes {
    
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


- (Node *)findBestNode:(NSDictionary *)openList {
    NSEnumerator *enumerator = [openList objectEnumerator];
    Node *bestNode = [enumerator nextObject];
    
    for (Position *currentPosition in openList) {
        if ([[openList objectForKey:currentPosition] totalCost] < [bestNode totalCost]) {
            bestNode = [openList objectForKey:currentPosition];
        }
    }
    
    return bestNode;
}


- (NSArray *)computePath:(NSDictionary *)closedList arrived:(Position *)arrived {
    BOOL find = NO;
    Node *currentNode = [closedList objectForKey:arrived];
    Node *parent;
    NSMutableArray *computePath = [[NSMutableArray alloc] init];
    
    [computePath addObject:currentNode];
    parent = currentNode.parent;
    
    while (!find) {
        currentNode = [closedList objectForKey:((Node *)[closedList objectForKey:parent.position]).position];
        [computePath insertObject:currentNode atIndex:0];
        parent = currentNode.parent;
        
        if (parent == nil) {
            find = YES;
        }
    }
    
    return [computePath autorelease];
}


- (void)computeDicrection {
    Node *nextNode = [path objectAtIndex:1];
    Position *nextPosition = [[Position alloc] initWithX:(nextNode.position.x * ressource.tileSize) y:(nextNode.position.y * ressource.tileSize)];

    if (nextNode != nil) {
        if (nextPosition.x < position.x) {
            if (nextPosition.y < position.y) {
                movement = [NSString stringWithString:@"leftTop"];
            }
            else if (nextPosition.y > position.y) {
                movement = [NSString stringWithString:@"leftDown"];
            }
            else {
                movement = [NSString stringWithString:@"left"];
            }
        }
        else if (nextPosition.x > position.x) {
            if (nextPosition.y < position.y) {
                movement = [NSString stringWithString:@"rightTop"];
            }
            else if (nextPosition.y > position.y) {
                movement = [NSString stringWithString:@"rightDown"];
            }
            else {
                movement = [NSString stringWithString:@"right"];
            }
        }
        else {
            if (nextPosition.y < position.y) {
                movement = [NSString stringWithString:@"top"];
            }
            else if (nextPosition.y > position.y) {
                movement = [NSString stringWithString:@"down"];
            }
        }
    }
    else {
        if ([movement isEqualToString:@"leftTop"]) {
            movement = [NSString stringWithString:@"stopLeftTop"];
        }
        else if ([movement isEqualToString:@"leftDown"]) {
            movement = [NSString stringWithString:@"stopLeftDown"];
        }
        else if ([movement isEqualToString:@"left"]) {
            movement = [NSString stringWithString:@"stopLeft"];
        }
        else if ([movement isEqualToString:@"rightTop"]) {
            movement = [NSString stringWithString:@"stopRightTop"];
        }
        else if ([movement isEqualToString:@"rightDown"]) {
            movement = [NSString stringWithString:@"stopRightDown"];
        }
        else if ([movement isEqualToString:@"right"]) {
            movement = [NSString stringWithString:@"stopRight"];
        }
        else if ([movement isEqualToString:@"top"]) {
            movement = [NSString stringWithString:@"stopTop"];
        }
        else if ([movement isEqualToString:@"down"]) {
            movement = [NSString stringWithString:@"stopDown"];
        }
    }
}


- (void)makeDecision {
    // Escape
    
    
    // Attack
        // Choisir joueur le plus proche
        // Suivre joueur
}


- (void)makeAction {
    Node *nextNode = [path objectAtIndex:1];
    Position *nextPosition = [[Position alloc] initWithX:(nextNode.position.x * ressource.tileSize) y:(nextNode.position.y * ressource.tileSize)];

    if ([position isEqual:nextPosition]) {
        self.path = [self pathFinding:[[Position alloc] initWithX:([self findNearestEnemy].position.x / ressource.tileSize) y:([self findNearestEnemy].position.y / ressource.tileSize)]];
        [self computeDicrection];
        
        if ([colisionMap isDestructibleBlock:((Node *)[path objectAtIndex:1]).position.x j:((Node *)[path objectAtIndex:1]).position.y]) {
            plantingBomb = YES;
        }
    } 
    else if (nextNode == nil) {
        [self computeDicrection];
    }
}


- (Player *)findNearestEnemy {
    Player *currentEnemy;
    Player *nearestEnemy = [enemies objectAtIndex:0];
    Position *enemyPosition = [[Position alloc] initWithX:(nearestEnemy.position.x / ressource.tileSize) y:(nearestEnemy.position.y / ressource.tileSize)];
    NSArray *pathUntilEnemy = [self pathFinding:enemyPosition];
    NSInteger nearestEnemyCost = [self computeLenghtPath:pathUntilEnemy];
    [enemyPosition release];
    
    for (int i = 1; i < [enemies count]; i++) {
        currentEnemy = [enemies objectAtIndex:i];
        enemyPosition = [[Position alloc] initWithX:(currentEnemy.position.x / ressource.tileSize) y:(currentEnemy.position.y / ressource.tileSize)];
        pathUntilEnemy = [self pathFinding:enemyPosition];
        
        if ([self computeLenghtPath:pathUntilEnemy] < nearestEnemyCost) {
            nearestEnemyCost = [self computeLenghtPath:pathUntilEnemy];
            nearestEnemy = currentEnemy;
        }
        
        [enemyPosition release];
    }
    
    return nearestEnemy;
}


- (NSInteger)computeLenghtPath:(NSArray *)pathValue {
    return [pathValue count];
}


- (id)copyWithZone:(NSZone *)zone {
    BotPlayer *copy = [super copyWithZone:zone];

    difficulty = @"Hard";
    path = [[NSMutableArray alloc] init];
    
	return copy;
}


@end
