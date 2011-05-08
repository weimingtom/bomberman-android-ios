#import "ColisionMap.h"
#import "EditorMap.h"
#import "Objects.h"
#import "RessourceManager.h"
#import "Bomb.h"
#import "Position.h"
#import "ColisionCase.h"
#import "Undestructible.h"


@implementation ColisionMap

- (id)initWithMap:(EditorMap *)mapValue {
    self = [super init];
    
    if (self) {
        bombs = [[NSMutableArray alloc] init];
        [self initMap:mapValue];
    }
    
    return self;
}


- (void)dealloc {
    [map release];
    [bombs release];
    [super dealloc];
}


- (void)initMap:(EditorMap *)mapValue {
    NSMutableArray *mapTmp;
    map = [[NSMutableArray alloc] initWithCapacity:mapValue.width];
    Objects *ground;
    Objects *block;
    ColisionCase *colisionCase;
    
    for (int i = 0; i < mapValue.width; i++) {
        mapTmp = [[NSMutableArray alloc] initWithCapacity:mapValue.height];
        [map addObject:mapTmp];
        [mapTmp release];
        
        for (int j = 0; j < mapValue.height; j++) {
            ground = [[mapValue.grounds objectAtIndex:i] objectAtIndex:j];
            block = [[mapValue.blocks objectAtIndex:i] objectAtIndex:j];            

            if (![block isEqual:@"empty"]) { //|| block.hit) { // TODO: Vérifier le '"||" du if.
                if (!block.fireWall) {
                    colisionCase = [[ColisionCase alloc] initWithType:GAPE];
                }
                else {
                    colisionCase = [[ColisionCase alloc] initWithType:BLOCK];
                }
            }
            else {
                colisionCase = [[ColisionCase alloc] initWithType:EMPTY];
            }
            [[map objectAtIndex:i] addObject:colisionCase];
            

            [colisionCase release];
        }
    }
}


- (void)draw:(CGContextRef)context {    
    for (int i = 0; i < [map count]; i++) {
        for (int j = 0; j < [[map objectAtIndex:i] count]; j++) {
            [[[map objectAtIndex:i] objectAtIndex:j] draw:context x:i y:j];
        }
    }
}


- (void)bombPlanted:(Bomb *)bomb {
    RessourceManager *resource = [RessourceManager sharedRessource];
    Position *bombPosition = [[Position alloc] initWithX:(bomb.position.x / resource.tileSize)  y:(bomb.position.y / resource.tileSize)];
    
    [[[map objectAtIndex:bombPosition.x] objectAtIndex:bombPosition.y] addValue:BOMB];
    [self addDangerousArea:bomb];
}


- (void)bombExploded:(Bomb *)bomb {
    RessourceManager *resource = [RessourceManager sharedRessource];
    Position *bombPosition = [[Position alloc] initWithX:(bomb.position.x / resource.tileSize)  y:(bomb.position.y / resource.tileSize)];
    
    BOOL top = YES;
    BOOL right = YES;
    BOOL down = YES;
    BOOL left = YES;
    
    [[[map objectAtIndex:bombPosition.x] objectAtIndex:bombPosition.y] removeValue:BOMB];
    [[[map objectAtIndex:bombPosition.x] objectAtIndex:bombPosition.y] removeValue:DANGEROUS_AREA];
    
    for (int i = 1; i <= bomb.power; i++) {
        if (left) {
            if ([[[map objectAtIndex:(bombPosition.x - i)] objectAtIndex:bombPosition.y] isDangerousArea]) {
                [[[map objectAtIndex:(bombPosition.x - i)] objectAtIndex:bombPosition.y] removeValue:DANGEROUS_AREA];
            }
            else {
                left = 0;
            }
        }
        
        if (right) {
            if ([[[map objectAtIndex:(bombPosition.x + i)] objectAtIndex:bombPosition.y] isDangerousArea]) {
                [[[map objectAtIndex:(bombPosition.x + i)] objectAtIndex:bombPosition.y] removeValue:DANGEROUS_AREA];
            }
            else {
                right = 0;
            }
        }
        
        if (top) {
            if ([[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y - i)] isDangerousArea]) {
                [[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y - i)] removeValue:DANGEROUS_AREA];
            }
            else {
                top = 0;
            }
        }
        
        if (down) {
            if ([[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y + i)] isDangerousArea]) {
                [[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y + i)] removeValue:DANGEROUS_AREA];        
            }
            else {
                down = 0;
            }
        }
    }
    
    
    if ([bombs indexOfObject:bomb] != NSNotFound) {
        [bombs removeObject:bomb];
    }
}


- (void)deleteObject:(Objects *)object {
    RessourceManager *resource = [RessourceManager sharedRessource];
    Position *objectPosition = [[Position alloc] initWithX:(object.position.x / resource.tileSize)  y:(object.position.y / resource.tileSize)];
    
    if (![object isEqual:@"empty"]) { // TODO: Vérifier le '"||" du if.
        if ([object isKindOfClass:[Bomb class]]) {
            [self bombExploded:(Bomb *)object];
        }
        else if ([object isKindOfClass:[Undestructible class]]) {
            if (!object.hit && object.damage != 0) {
                [[[map objectAtIndex:(objectPosition.x)] objectAtIndex:objectPosition.y] removeValue:FIRE];
            }
        }
        else if ([object isKindOfClass:[Destructible class]]){
            if (object.hit && object.fireWall) {
                [self updateDangerousArea:YES];
                [[[map objectAtIndex:(objectPosition.x)] objectAtIndex:objectPosition.y] removeValue:BLOCK];
                [self updateDangerousArea:NO];
            }
        }
    }
}


- (void)updateDangerousArea:(BOOL)remove {
    NSMutableArray *bombsCopy = [bombs copy];

    if (!remove) {
        [bombs removeAllObjects];
    }
    
    for (Bomb *bomb in bombsCopy) {
        if (remove) {
            [self deleteDangerousArea:bomb];
        }
        else {
            [self addDangerousArea:bomb];
        }
    }
    
    [bombsCopy release];
}


- (void)addDangerousArea:(Bomb *)bomb {
    RessourceManager *resource = [RessourceManager sharedRessource];
    Position *bombPosition = [[Position alloc] initWithX:(bomb.position.x / resource.tileSize)  y:(bomb.position.y / resource.tileSize)];
    
    BOOL top = YES;
    BOOL right = YES;
    BOOL bottom = YES;
    BOOL left = YES;
    
    [[[map objectAtIndex:bombPosition.x] objectAtIndex:bombPosition.y] addValue:DANGEROUS_AREA];
    
    for (int i = 1; i <= bomb.power; i++) {        
        if (left) {
            if (![[[map objectAtIndex:(bombPosition.x - i)] objectAtIndex:bombPosition.y] isTraversableByFire]) {
                left = NO;
            }
            else {
                [[[map objectAtIndex:(bombPosition.x - i)] objectAtIndex:bombPosition.y] addValue:DANGEROUS_AREA];
            }
        }
        
        if (right) {
            if (![[[map objectAtIndex:(bombPosition.x + i)] objectAtIndex:bombPosition.y] isTraversableByFire]) {
                right = NO;
            }
            else {
                [[[map objectAtIndex:(bombPosition.x + i)] objectAtIndex:bombPosition.y] addValue:DANGEROUS_AREA];
            }
        }

        if (top) {
            if (![[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y - i)] isTraversableByFire]) {
                top = NO;
            }
            else {
                [[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y - i)] addValue:DANGEROUS_AREA];
            }
        }
        
        if (bottom) {
            if (![[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y + i)] isTraversableByFire]) {
                bottom = NO;
            }
            else {
                [[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y + i)] addValue:DANGEROUS_AREA];
            }
        }
    }
    
    if (!top || !right || !bottom || !left) {
        [bombs addObject:bomb];
    }
}


- (void)deleteDangerousArea:(Bomb *)bomb {
    RessourceManager *resource = [RessourceManager sharedRessource];
    Position *bombPosition = [[Position alloc] initWithX:(bomb.position.x / resource.tileSize)  y:(bomb.position.y / resource.tileSize)];
    
    BOOL top = YES;
    BOOL right = YES;
    BOOL down = YES;
    BOOL left = YES;
    
    [[[map objectAtIndex:bombPosition.x] objectAtIndex:bombPosition.y] removeValue:DANGEROUS_AREA];
    
    for (int i = 1; i <= bomb.power; i++) {        
        if (left) {
            if (![[[map objectAtIndex:(bombPosition.x - i)] objectAtIndex:bombPosition.y] isTraversableByFire]) {
                left = NO;
            }
            else {
                if (![[[map objectAtIndex:(bombPosition.x - i)] objectAtIndex:bombPosition.y] isBomb]) {
                    [[[map objectAtIndex:(bombPosition.x - i)] objectAtIndex:bombPosition.y] removeValue:DANGEROUS_AREA];
                }
            }
        }
        
        if (right) {
            if (![[[map objectAtIndex:(bombPosition.x + i)] objectAtIndex:bombPosition.y] isTraversableByFire]) {
                right = NO;
            }
            else {
                if (![[[map objectAtIndex:(bombPosition.x + i)] objectAtIndex:bombPosition.y] isBomb]) {
                    [[[map objectAtIndex:(bombPosition.x + i)] objectAtIndex:bombPosition.y] removeValue:DANGEROUS_AREA];
                }
            }
        }
        
        if (top) {
            if (![[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y - i)] isTraversableByFire]) {
                top = NO;
            }
            else {
                if (![[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y - i)] isBomb]) {
                    [[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y - i)] removeValue:DANGEROUS_AREA];
                }
            }
        }
        
        if (down) {
            if (![[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y + i)] isTraversableByFire]) {
                down = NO;
            }
            else {
                if (![[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y + i)] isBomb]) {
                    [[[map objectAtIndex:bombPosition.x] objectAtIndex:(bombPosition.y + i)] removeValue:DANGEROUS_AREA];
                }
            }
        }
    }
}


- (BOOL)isTraversableByPlayer:(NSInteger)i j:(NSInteger)j {
    return [[[map objectAtIndex:i] objectAtIndex:j] isTraversableByPlayer];
}


- (BOOL)isTraversableByFire:(NSInteger)i j:(NSInteger)j {
    return [[[map objectAtIndex:i] objectAtIndex:j] isTraversableByFire];
}


- (BOOL)isBomb:(NSInteger)i j:(NSInteger)j {
    return [[[map objectAtIndex:i] objectAtIndex:j] isBomb];
}


- (BOOL)isFire:(NSInteger)i j:(NSInteger)j {
    return [[[map objectAtIndex:i] objectAtIndex:j] isFire];
}


- (BOOL)isDangerousArea:(NSInteger)i j:(NSInteger)j; {
    return [[[map objectAtIndex:i] objectAtIndex:j] isDangerousArea];
}


- (void)addFire:(Position *)position {
    RessourceManager *resource = [RessourceManager sharedRessource];
    Position *p = [[Position alloc] initWithX:(position.x / resource.tileSize) y:(position.y / resource.tileSize)];

    [[[map objectAtIndex:p.x] objectAtIndex:p.y] addValue:FIRE];
}


- (NSString *)description {
    NSMutableString *desc = [[NSMutableString alloc] init];
    
    for (int i = 0; i < [map count]; i++) {
        for (int j = 0; j < [[map objectAtIndex:i] count]; j++) {
            [desc appendFormat:@"%@", [[map objectAtIndex:i] objectAtIndex:j]];
        }
        
        [desc appendString:@"\n"];
    }
    
    return desc;
}

@end
