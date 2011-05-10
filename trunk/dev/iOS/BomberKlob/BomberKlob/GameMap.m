#import "GameMap.h"
#import "RessourceManager.h"
#import "Position.h"
#import "Objects.h"
#import "EditorMap.h"
#import "ColisionMap.h"
#import "Objects.h"
#import "Undestructible.h"
#import "BotPlayer.h"


@implementation GameMap

@synthesize bitmap, animatedObjects, animatedObjectsInitial, colisionMap;


- (id)initWithMapName:(NSString *)mapName {
    self = [super init];
    
    if (self) {  
        self.name = mapName;
        animatedObjects = [[NSMutableDictionary alloc] init];
        [self load];
    }
    
    return self;
}


- (void)dealloc {
    [bitmap release];
    [animatedObjects release];
    [animatedObjectsInitial release];
    [colisionMap release];
    [super dealloc];
}


- (void)load {
    EditorMap *myMap;
    NSArray *grounds;
    NSArray *blocks;
    Objects *ground;
    Objects *block;
    
    NSData *data;
    NSKeyedUnarchiver *unarchiver;
    NSString *mapPath = [[NSString alloc] initWithFormat:@"%@/Maps/%@.klob", [[NSBundle mainBundle] bundlePath], name];
    
    data = [[NSData alloc] initWithContentsOfFile:mapPath];
    
    if (data != nil) {
        unarchiver = [[NSKeyedUnarchiver alloc] initForReadingWithData:data];
        
        myMap = [unarchiver decodeObjectForKey:@"map"];
        [unarchiver finishDecoding];
        [unarchiver release];
        
        self.width = myMap.width;
        self.height = myMap.height;
        grounds = myMap.grounds;
        blocks = myMap.blocks;
        self.players = myMap.players;
        
		for (int i = 0; i < width; i++){			
			for (int j = 0; j < height; j++) {
                ground = [[grounds objectAtIndex:i] objectAtIndex:j];
                
                if (![[[blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"]) {
                    block = [[blocks objectAtIndex:i] objectAtIndex:j];
                }
                
                if (![ground isUnanimated]) {
                    [animatedObjects setObject:ground forKey:ground.position];
                }
                
                if (![block isUnanimated]) {
                    [animatedObjects setObject:block forKey:block.position];
                }
			}
		}
        
		[self makeBitmap:grounds blocks:blocks];
        
        colisionMap = [[ColisionMap alloc] initWithMap:myMap];
    }

    [mapPath release];
    [data release];
}


- (void) makeBitmap:(NSArray *)grounds blocks:(NSArray *)blocks {
    NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
    
	CGSize size = CGSizeMake(tileSize * height, tileSize * width);
    
    UIGraphicsBeginImageContext(CGSizeMake(size.height, size.width));
    
    [self drawBitmap:UIGraphicsGetCurrentContext() grounds:grounds blocks:blocks];
    bitmap = UIGraphicsGetImageFromCurrentImageContext();
	[bitmap retain];
    
    UIGraphicsEndImageContext();
}


- (void)draw:(CGContextRef)context {
	@synchronized (self) {
		NSInteger tileSize = [RessourceManager sharedRessource].tileSize;
		[bitmap drawInRect:CGRectMake(0, 0, width * tileSize, height * tileSize)];
        
		for (Position *position in animatedObjects) {
			[[animatedObjects objectForKey:position] draw:context];
		}
        
        [colisionMap draw:context];
	}
}


- (void)drawBitmap:(CGContextRef)context grounds:(NSArray *)grounds blocks:(NSArray *)blocks {
    
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            if ([[[grounds objectAtIndex:i] objectAtIndex:j] isUnanimated]) {
                [[[grounds objectAtIndex:i] objectAtIndex:j] draw:context];
            }
            
            if (![[[blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"] && [[[blocks objectAtIndex:i] objectAtIndex:j] isUnanimated]) {
                [[[blocks objectAtIndex:i] objectAtIndex:j] draw:context];
            }
        }
    }
}


- (void) update{
	@synchronized (self) {
		NSMutableArray *objectsDeleted = [[NSMutableArray alloc] init];
        
		for (Position *position in animatedObjects) {
			Objects *object = [animatedObjects objectForKey:position];
            
			if ([object hasAnimationFinished]) {
				[objectsDeleted addObject:object];
			}
            
			[object update];
		}
        
		for (Objects *object in objectsDeleted) {
            [colisionMap deleteObject:object];
			[animatedObjects removeObjectForKey:object.position];
		}
        
		[objectsDeleted release];
	}
}


- (void)restart {
    
}


- (void)addAnimatedObject:(Objects *)object {
    @synchronized (self) {
        [animatedObjects setObject:object forKey:object.position];
        
        if ([object isKindOfClass:[Undestructible class]]) {
            if (!object.hit && object.damage != 0) {
                [colisionMap addFire:object.position];
            }
        }
    }
}


- (void)deleteAnimatedObject:(Position *)position  {
    @synchronized (self) {
        [animatedObjects removeObjectForKey:position];
    }
}


- (void)bombPlanted:(Bomb *)bomb {
    [colisionMap bombPlanted:bomb];
}


- (void)bombExplode:(Bomb *)bomb {
    [colisionMap bombExploded:bomb];
}

@end
