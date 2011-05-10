#import "ColisionCase.h"
#import "RessourceManager.h"


@implementation ColisionCase


- (id)initWithType:(CaseType)typeValue {
    self = [super init];
    
    if (self) {
        if (typeValue == EMPTY) {
            counters = [[NSMutableArray alloc] init];
            types = [[NSMutableArray alloc] init];
        }
        else {
            counters = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithInt:1], nil];
            types = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithInt:typeValue], nil];
        }
    }
    
    return self;
}


- (void)dealloc {
    [counters release];
    [types release];
    [super dealloc];
}


- (void)addValue:(CaseType)typeValue {
    NSInteger i = 0;
    BOOL changeMade = NO;
    CaseType type;
    NSInteger counter;
    
    if ([types count] <= 0) {
        [types addObject:[NSNumber numberWithInt:typeValue]];
        [counters addObject:[NSNumber numberWithInt:1]];
    }
    else {
        while (!changeMade && i < [types count]) {
            type = [[types objectAtIndex:i] intValue];
            
            if (type == typeValue) {
                counter = [[counters objectAtIndex:i] integerValue];
                counter++;
                [counters replaceObjectAtIndex:i withObject:[NSNumber numberWithInteger:counter]];
                
                changeMade = YES;
            }
            else if (typeValue < type) {
                [types insertObject:[NSNumber numberWithInt:typeValue] atIndex:i];
                [counters insertObject:[NSNumber numberWithInt:1] atIndex:i];
                
                changeMade = YES;
            }
            else {
                i++;
            }
        }
        
        if (!changeMade) {
            [types addObject:[NSNumber numberWithInt:typeValue]];
            [counters addObject:[NSNumber numberWithInt:1]];
        }
    }
}


- (void)removeValue:(CaseType)typeValue {
    NSInteger i = 0;
    BOOL changeMade = NO;
    CaseType type;
    NSInteger counter;
    
    while (!changeMade && i < [types count]) {
        type = [[types objectAtIndex:i] intValue];
        
        if (type == typeValue) {
            counter = [[counters objectAtIndex:i] integerValue];
            counter--;
            
            if (counter <= 0) {
                [types removeObjectAtIndex:i];
                [counters removeObjectAtIndex:i];
            }
            else {
                [counters replaceObjectAtIndex:i withObject:[NSNumber numberWithInteger:counter]];
            }
            
            changeMade = YES;
        }
        else {
            i++;
        }
    }
}


- (void)draw:(CGContextRef)context x:(NSInteger)x y:(NSInteger)y {
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    
    if ([types count] > 0) {
        NSInteger caseType = [[types lastObject] integerValue];
        
        if (caseType == UNDESTRUCTIBLE_BLOCK) {
            CGContextSetRGBFillColor(context, 0, 0, 255, 0.5);
            CGContextFillRect(context, CGRectMake(x * tileSize, y * tileSize, tileSize, tileSize));
        }
        else if (caseType == DESTRUCTIBLE_BLOCK) {
            CGContextSetRGBFillColor(context, 0, 255, 255, 0.5);
            CGContextFillRect(context, CGRectMake(x * tileSize, y * tileSize, tileSize, tileSize));
        }
        else if (caseType == GAPE) {
            CGContextSetRGBFillColor(context, 0, 255, 0, 0.5);
            CGContextFillRect(context, CGRectMake(x * tileSize, y * tileSize, tileSize, tileSize));
        }
        else if (caseType == BOMB) {
            CGContextSetRGBFillColor(context, 255, 255, 0, 0.5);
            CGContextFillRect(context, CGRectMake(x * tileSize, y * tileSize, tileSize, tileSize));
        }
        else if (caseType == FIRE) {
            CGContextSetRGBFillColor(context, 0, 0, 0, 0.5);
            CGContextFillRect(context, CGRectMake(x * tileSize, y * tileSize, tileSize, tileSize));
        }
        else if (caseType == DANGEROUS_AREA) {
            CGContextSetRGBFillColor(context, 255, 0, 0, 0.5);
            CGContextFillRect(context, CGRectMake(x * tileSize, y * tileSize, tileSize, tileSize));
        }
    }
}


- (BOOL)isTraversableByPlayer {
    NSInteger i = 0;
    BOOL result = YES;
    
    while (result && i < [types count]) {
        if ([[types objectAtIndex:i] intValue] == UNDESTRUCTIBLE_BLOCK) {
            result = NO;
        }
        else if ([[types objectAtIndex:i] intValue] == DESTRUCTIBLE_BLOCK) {
            result = NO;
        }
        else if ([[types objectAtIndex:i] intValue] == GAPE) {
            result = NO;
        }
        
        i++;
    }

    return result;
}


- (BOOL)isTraversableByFire {
    NSInteger i = 0;
    BOOL result = YES;
    
    while (result && i < [types count]) {
        if ([[types objectAtIndex:i] intValue] == UNDESTRUCTIBLE_BLOCK) {
            result = NO;
        }
        else if ([[types objectAtIndex:i] intValue] == DESTRUCTIBLE_BLOCK) {
            result = NO;
        }
        
        i++;
    }
    
    return result;
}


- (BOOL)isUndestructibleBlock {
    NSInteger i = 0;
    BOOL result = NO;
    
    while (!result && i < [types count]) {
        if ([[types objectAtIndex:i] intValue] == UNDESTRUCTIBLE_BLOCK) {
            result = YES;
        }
        
        i++;
    }
    
    return result;
}


- (BOOL)isDestructibleBlock {
    NSInteger i = 0;
    BOOL result = NO;
    
    while (!result && i < [types count]) {
        if ([[types objectAtIndex:i] intValue] == DESTRUCTIBLE_BLOCK) {
            result = YES;
        }
        
        i++;
    }
    
    return result;
}


- (BOOL)isBomb {
    NSInteger i = 0;
    BOOL result = NO;
    
    while (!result && i < [types count]) {
        if ([[types objectAtIndex:i] intValue] == BOMB) {
            result = YES;
        }
        
        i++;
    }
    
    return result;
}


- (BOOL)isFire {
    NSInteger i = 0;
    BOOL result = NO;
    
    while (!result && i < [types count]) {
        if ([[types objectAtIndex:i] intValue] == FIRE) {
            result = YES;
        }
        
        i++;
    }
    
    return result;
}


- (BOOL)isDangerousArea {
    NSInteger i = 0;
    BOOL result = NO;
    
    while (!result && i < [types count]) {
        if ([[types objectAtIndex:i] intValue] == BOMB) {
            result = YES;
        }
        else if ([[types objectAtIndex:i] intValue] == DANGEROUS_AREA) {
            result = YES;
        }
        
        i++;
    }
    
    return result;
}


- (NSString *)description {
    return [NSString stringWithFormat:@"%@", types];
}

@end
