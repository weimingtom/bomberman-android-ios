//
//  Engine.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Engine.h"
#import "Player.h"
#import "RessourceManager.h"
#import "Game.h"
#import "Map.h"
#import "Bomb.h"
#import "AnimationSequence.h"
#import "Undestructible.h"
#import "Single.h"
#import "GameMap.h"
#import "ColisionMap.h"

@implementation Engine

@synthesize game;

- (id) initWithMapName:(NSString *)mapName {
	self = [super init];
    
	if (self){
		resource = [RessourceManager sharedRessource];
		game = [[Single alloc] initWithMapName:mapName];
		[self startTimerBombs];
		[self startTimerPlayers];
		[game initGame];
	}
	return self;
}

- (id) initWithGame:(Game *) gameValue{
	self = [super init];
	if (self){
		resource = [RessourceManager sharedRessource];
		self.game = gameValue;
		[self startTimerBombs];
		[self startTimerPlayers];
		[game initGame];
	}
	return self;
}


- (void)dealloc {
	[updateBombsThread release];
	[updatePlayersThread release];
	[updatePlayersCondition release];
	[updateBombsCondition release];
    [game release];
    [super dealloc];
}
- (void) collisionWithPlayer: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue bomb:(Bomb *) aBomb{
	int marge = 5;
	for (Player * player in game.players) {
		CGRect rectObject = CGRectMake(object.position.x+xValue, object.position.y+yValue, resource.tileSize-marge,resource.tileSize-marge);
		CGRect rectPlayer = CGRectMake(player.position.x, player.position.y,resource.tileSize-marge,resource.tileSize-marge);
		if (CGRectIntersectsRect(rectObject, rectPlayer)) {
			if (aBomb.owner == player){
				if (!player.istouched && !player.isInvincible){
					player.lifeNumber--;
				}
			}
			else {
				aBomb.owner.lifeNumber++;
			}
			[player hurt];
		}
	}
}

- (BOOL) isInCollisionWithABomb: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue{
	for (Position * position in game.bombsPlanted) {
		CGRect rectBomb = CGRectMake(position.x, position.y, resource.tileSize, resource.tileSize);
		CGRect rectObj = CGRectMake(object.position.x+xValue, object.position.y+yValue, resource.tileSize, resource.tileSize);
        
		if (CGRectIntersectsRect(rectObj, rectBomb)) {
			return YES;
		}
	}
	if ([[[object class] description] isEqualToString:@"Player"]) {
		if (((Player *)object).bombPosed) {
			((Player *)object).bombPosed = NO;
		}
	}
    return NO;
}


- (BOOL) isInCollision: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue{

	// If the object is a player
	if ([[[object class] description] isEqualToString:@"Player"] ) {

		//We first checks if there is a collision with sides of the screen
		if (object.position.x+xValue < 0) {
			return YES;
		}
		if (object.position.x+xValue + resource.tileSize > game.map.width * resource.tileSize){
			return YES;
		}
		if (object.position.y+yValue + (resource.tileSize) > game.map.height * resource.tileSize){
				return YES;
		}
		if (object.position.y+yValue < 0){
			return YES;
		}
		
		//We calculates the smallest and largest object's coordinates
		int marge = 5; //Margin movement
		
		int xmin = floor((object.position.x+xValue+marge) / resource.tileSize);
		int ymin = floor((object.position.y+yValue+marge) / resource.tileSize);
		
		int xmax = ceil((object.position.x+xValue + resource.tileSize-marge) / resource.tileSize);
		int ymax = ceil(((object.position.y+yValue + (resource.tileSize)-marge) / resource.tileSize));

		
		//For each cell of the object
		for (int i=xmin; i <= xmax; i++) {
			for (int j =ymin; j <= ymax; j++) {
				if ((xmin >= 0 && ymin >=0) && (xmax < game.map.width && ymax < game.map.height)) {
					//If it is another thing nonempty
					if (![game.map.colisionMap isTraversableByPlayer:i j:j]) {
						return YES;
					}
				}
				else 
					return YES;	
			}
		}
		
		//If we just plant the bomb, we can cross it, not otherwise
		if ([self isInCollisionWithABomb:object :xValue :yValue]) {
			if (((Player *) object).bombPosed) {
				return NO;
			}
			else
				return YES;
		}
	}
	// If it's not a player
	else {		
		//We first checks if there is a collision with sides of the screen
		if (object.position.x+xValue < 0) {
			return YES;
		}
		if (object.position.x+xValue + resource.tileSize > resource.screenHeight){
			return YES;
		}
		if (object.position.y+yValue + resource.tileSize > resource.screenWidth){
			return YES;
		}
		if (object.position.y+yValue < 0){
			return YES;
		}
		
		//We look at in all the animated object of the map
		for (Position * position in game.map.animatedObjects) {
			CGRect rect = CGRectMake(position.x, position.y, resource.tileSize, resource.tileSize);
			CGRect rectObj = CGRectMake(object.position.x+xValue, object.position.y+yValue, resource.tileSize, resource.tileSize);
			if (CGRectIntersectsRect(rectObj, rect)) {
				return YES;
			} 
		}
		
		//We calculates the smallest and largest object's coordinates
		int marge = 5; //Margin movement
		int xmin = floor((object.position.x+xValue) / resource.tileSize);
		int ymin = floor((object.position.y+yValue) / resource.tileSize);
		
		int xmax = ceil((object.position.x+xValue + resource.tileSize-marge) / resource.tileSize);
		int ymax = ceil(((object.position.y+yValue + (resource.tileSize)-marge) / resource.tileSize));
		
		//For each cell of the object
		for (int i=xmin; i <= xmax; i++) {
			for (int j =ymin; j <= ymax; j++) {
				if ((xmin >= 0 && ymin >=0) && (xmax < game.map.width && ymax < game.map.height)) {
					//If it is something nonempty
                    if (![game.map.colisionMap isTraversableByFire:i j:j]) {
                        return YES;
                    }
				}
				else
					return YES;
			}
		}
	}
	return NO;   
}


- (void) moveTop{
	Player * player = [game getHumanPlayer];
	if (![self isInCollision:player :0 :-player.speed]){
		[player moveTop];
	}
	
}

- (void) moveDown{
	Player * player = [game getHumanPlayer];
	if (![self isInCollision:player :0 :player.speed]){
		[player moveDown];
	}
}

- (void) moveLeft{
	Player * player = [game getHumanPlayer];
	if (![self isInCollision:player :-player.speed :0]){
		[player moveLeft];
	}
}

- (void) moveRight{
	Player * player = [game getHumanPlayer];
	if (![self isInCollision:player :player.speed :0]){
		[player moveRight];
	}
}

- (void) moveLeftTop{
	Player * player = [game getHumanPlayer];
	if (![self isInCollision:player :-player.speed :-player.speed]){
		[player moveLeftTop];
	}
}

- (void) moveLeftDown{
	Player * player = [game getHumanPlayer];
	if (![self isInCollision:player :-player.speed :player.speed]){
		[player moveLeftDown];
	}
}

- (void) moveRightDown{
	Player * player = [game getHumanPlayer];
	if (![self isInCollision:player :player.speed :player.speed]){
		[player moveRightDown];
	}
}

- (void) moveRightTop{
	Player * player = [game getHumanPlayer];
	if (![self isInCollision:player :player.speed :-player.speed]){
		[player moveRightTop];
	}
    
}

- (void) stopTop{
	Player * player = [game getHumanPlayer];
	[player stopTop];
}

- (void) stopDown{
	Player * player = [game getHumanPlayer];
	[player stopDown];
}

- (void) stopLeft{
	Player * player = [game getHumanPlayer];
	[player stopLeft];
}

- (void) stopRight{
	Player * player = [game getHumanPlayer];
	[player stopRight];
}


- (void) stopLeftTop{
	Player * player = [game getHumanPlayer];
	[player stopLeftTop];
}

- (void) stopRightTop{
	Player * player = [game getHumanPlayer];
	[player stopRightTop];
}

- (void) stopLeftDown{
	Player * player = [game getHumanPlayer];
	[player stopLeftDown];
}

- (void) stopRightDown{
	Player * player = [game getHumanPlayer];
	[player stopRightDown];
}

- (void) startTimerBombs{
//	@synchronized (self) {
	updateBombsCondition = [[NSCondition alloc] init];
	updateBombsPause = NO;
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	updateBombsThread = [[[NSThread alloc] initWithTarget:self selector:@selector(startTimerBombsThread) object:nil]autorelease];
	[updateBombsThread start];
	[pool release];
//	}
}

- (void) startTimerBombsThread {
	
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	
	[[NSTimer scheduledTimerWithTimeInterval:1 target: self selector: @selector(updateBombs) userInfo:self repeats: YES] autorelease];	
	[runLoop run];
	[pool release];
}


- (void) updateBombs{
	if (![updateBombsThread isCancelled]) {
		[updateBombsCondition lock];
		while (updateBombsPause) {
			[updateBombsCondition wait];
		}
		
		NSMutableArray * bombsDeleted = [[NSMutableArray alloc] init];
		for (Position * position in game.bombsPlanted) {
			Bomb * bomb = [game.bombsPlanted objectForKey:position];
			[bomb update];
		}
		while ([self thereAreBombToExplode]) {
			for (Position * position in game.bombsPlanted) {
				Bomb * bomb = [game.bombsPlanted objectForKey:position];
				if ([bomb hasAnimationFinished]) {
					[bomb destroy];
					[self displayFire:bomb];
					[bombsDeleted addObject:position];
					break;
				}
			}
			for (Position * position in bombsDeleted) {
				[game bombExplode:position];
			}
			
			[bombsDeleted removeAllObjects];
		}
		[bombsDeleted release];
		[updateBombsCondition unlock];
	}
}

- (BOOL) thereAreBombToExplode {
	for (Position * position in game.bombsPlanted){
		if ([((Bomb *)[game.bombsPlanted objectForKey:position]) hasAnimationFinished]) {
			return YES;
		}
	}
	return NO;
}

- (void) displayFire:(Bomb *) bomb{
	BOOL stopFireDown = NO;
	BOOL stopFireUp = NO;
	BOOL stopFireLeft = NO;
	BOOL stopFireRight = NO;
	Undestructible * fire = [[resource.bitmapsAnimates objectForKey:@"firecenter"] copy];
	fire.position = [[Position alloc] initWithPosition:bomb.position];
	[self collisionWithPlayer:fire :0 :0 bomb:bomb];
	
    [game.map addAnimatedObject:fire];
	
	[fire release];
	
	Position * firePosition;
	Position * firePositionMap;
	for (int i=1; i <= bomb.power; i++) {
		//Down
		if (!stopFireDown) {
			firePosition = [[Position alloc] initWithX:bomb.position.x y:bomb.position.y+(resource.tileSize*i)];
			firePositionMap = [[Position alloc] initWithX:(bomb.position.x/resource.tileSize) y:(bomb.position.y/resource.tileSize)+i ];
			if (firePositionMap.y < game.map.height) {
				if (i != bomb.power) {
					fire = [[resource.bitmapsAnimates objectForKey:@"firevertical"] copy];
					fire.position = firePosition;
				}
				else{
					fire = [[resource.bitmapsAnimates objectForKey:@"firedown"] copy];
					fire.position = firePosition;
				}
				[self collisionWithPlayer:fire :0 :0 bomb:bomb];
				if([self isInCollisionWithABomb:fire :0 :0]){
					[[game.bombsPlanted objectForKey:firePosition] destroy];
					stopFireDown = YES;
				}
				else if (![self isInCollision:fire :0 :0]){
                    [game.map addAnimatedObject:fire];
					[fire release];
				}
				else if([((Objects *)[game.map.animatedObjects objectForKey:firePosition]).imageName isEqualToString:@"firedown"]){
					fire = [[resource.bitmapsAnimates objectForKey:@"firevertical"] copy];
					fire.position = firePosition;
                    [game.map addAnimatedObject:fire];
					[fire release];
				}
				else {
					[(Objects *)[game.map.animatedObjects objectForKey:firePosition] destroy];
					stopFireDown = YES;
				}
			}
			[firePosition release];
			[firePositionMap release];
		}
		//UP
		if (!stopFireUp) {
			firePosition = [[Position alloc] initWithX:bomb.position.x y:bomb.position.y-(resource.tileSize*i)];
			firePositionMap = [[Position alloc] initWithX:(bomb.position.x/resource.tileSize) y:(bomb.position.y/resource.tileSize)-i ];
			if (firePositionMap.y >= 0) {
				if (i != bomb.power) {
					fire = [[resource.bitmapsAnimates objectForKey:@"firevertical"] copy];
					fire.position = firePosition;
				}
				else{
					fire = [[resource.bitmapsAnimates objectForKey:@"fireup"] copy];
					fire.position = firePosition;
				}
				[self collisionWithPlayer:fire :0 :0 bomb:bomb];
				if([self isInCollisionWithABomb:fire :0 :0]){
					[[game.bombsPlanted objectForKey:firePosition] destroy];
					stopFireUp = YES;	
				}
				else if (![self isInCollision:fire :0 :0]){
                    [game.map addAnimatedObject:fire];
					[fire release];
				}
				else if([((Objects *)[game.map.animatedObjects objectForKey:firePosition]).imageName isEqualToString:@"fireup"]){
					fire = [[resource.bitmapsAnimates objectForKey:@"firevertical"] copy];
					fire.position = firePosition;
                    [game.map addAnimatedObject:fire];
					[fire release];
				}
				else {
					[(Objects *)[game.map.animatedObjects objectForKey:firePosition] destroy];
					stopFireUp = YES;	
				}
			}
			[firePosition release];
			[firePositionMap release];
		}
		//Left
		if (!stopFireLeft) {
			firePosition = [[Position alloc] initWithX:bomb.position.x-(resource.tileSize*i) y:bomb.position.y];
			firePositionMap = [[Position alloc] initWithX:(bomb.position.x/resource.tileSize)-i y:(bomb.position.y/resource.tileSize) ];
			if (firePositionMap.x >=0) {
				if (i != bomb.power) {
					fire = [[resource.bitmapsAnimates objectForKey:@"firehorizontal"] copy];
					fire.position = firePosition;
				}
				else{
					fire = [[resource.bitmapsAnimates objectForKey:@"fireleft"] copy];
					fire.position = firePosition;
				}
				[self collisionWithPlayer:fire :0 :0 bomb:bomb];
				if([self isInCollisionWithABomb:fire :0 :0]){
					[[game.bombsPlanted objectForKey:firePosition ] destroy];
					stopFireLeft = YES;
				}
				else if (![self isInCollision:fire :0 :0]){
                    [game.map addAnimatedObject:fire];
					[fire release];
				}
				else if([((Objects *)[game.map.animatedObjects objectForKey:firePosition]).imageName isEqualToString:@"fireleft"]){
					fire = [[resource.bitmapsAnimates objectForKey:@"firehorizontal"] copy];
					fire.position = firePosition;
                    [game.map addAnimatedObject:fire];
					[fire release];
				}
				else {
					[(Objects *)[game.map.animatedObjects objectForKey:firePosition] destroy];
					stopFireLeft = YES;
				}
			}
			[firePosition release];
			[firePositionMap release];
		}
		//Right
		if (!stopFireRight) {
			firePosition = [[Position alloc] initWithX:bomb.position.x+(resource.tileSize*i) y:bomb.position.y];
			firePositionMap = [[Position alloc] initWithX:(bomb.position.x/resource.tileSize)+i y:(bomb.position.y/resource.tileSize) ];
			if (firePositionMap.x < game.map.width) {
				if (i != bomb.power) {
					fire = [[resource.bitmapsAnimates objectForKey:@"firehorizontal"] copy];
					fire.position = firePosition;
				}
				else{
					fire = [[resource.bitmapsAnimates objectForKey:@"fireright"] copy];
					fire.position = firePosition;
				}
				[self collisionWithPlayer:fire :0 :0 bomb:bomb];
				if([self isInCollisionWithABomb:fire :0 :0]){
					[[game.bombsPlanted objectForKey:firePosition ] destroy];
					stopFireRight = YES;
				}
				else if (![self isInCollision:fire :0 :0]){
                    [game.map addAnimatedObject:fire];
					[fire release];
				}
				else if([((Objects *)[game.map.animatedObjects objectForKey:firePosition]).imageName isEqualToString:@"fireright"]){
					fire = [[resource.bitmapsAnimates objectForKey:@"firehorizontal"] copy];
					fire.position = firePosition;
                    [game.map addAnimatedObject:fire];
					[fire release];
				}
				else {
					[(Objects *)[game.map.animatedObjects objectForKey:firePosition] destroy];
					stopFireRight = YES;
				}
			}
			[firePosition release];
			[firePositionMap release];
		}
	}
}

- (void)plantingBomb:(Bomb *)bomb {
	if (game.isStarted) {
		Player *owner = [game getHumanPlayer];
		[owner plantingBomb:bomb];
		
		NSInteger bx = (owner.position.x + (resource.tileSize / 2)) / resource.tileSize;
		NSInteger by = (owner.position.y + (resource.tileSize / 2)) / resource.tileSize;
		
		Position *bombPosition = [[Position alloc] initWithX:(bx * resource.tileSize) y:(by * resource.tileSize)];
		bomb.owner = owner;
		bomb.position = bombPosition;
		[bombPosition release];
		
		if (![self isInCollision:bomb :0 :0] && ![self isInCollisionWithABomb:bomb :0 :0]) {
			[game plantingBombByPlayer:bomb];
		}
	}
}

-(void) pauseThread:(BOOL) enable{
	if (!enable) {
		[updateBombsCondition lock];
		[updateBombsCondition signal];
		[updateBombsCondition unlock];
		[updatePlayersCondition lock];
		[updatePlayersCondition signal];
		[updatePlayersCondition unlock];
	}
	updateBombsPause = enable;
	updatePlayersPause = enable;
	[game pauseGame:enable];
	[game pauseGame:enable];


}

- (void) stopThread {
	[updateBombsThread cancel];
	[updatePlayersThread cancel];
}

- (void) startTimerPlayers{
	updatePlayersCondition = [[NSCondition alloc] init];
	updatePlayersPause = NO;
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	updatePlayersThread = [[[NSThread alloc] initWithTarget:self selector:@selector(startTimerPlayersThread) object:nil]autorelease];
	[updatePlayersThread start];
	[pool release];
}

- (void) startTimerPlayersThread {
	
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	[[NSTimer scheduledTimerWithTimeInterval:0.02 target: self selector: @selector(updatePlayers) userInfo:self repeats: YES] autorelease];	
	[runLoop run];
	[pool release];
}


- (void) updatePlayers{
	if (![updatePlayersThread isCancelled]) {
		[updatePlayersCondition lock];
		while (updatePlayersPause) {
			[updatePlayersCondition wait];
		}
		while (!game.isStarted) {}
		for (int i = 1; i < [game nbPlayers]; i++) {
			[(Player *)[game.players objectAtIndex:i] update];

		}
		[updatePlayersCondition unlock];
	}
}

- (BOOL) gameIsStarted {
	return game.isStarted;
}

- (void) updateMap {
	[game updateMap];
}

- (Player *) getHumanPlayer {
	return [game getHumanPlayer];
}

- (NSInteger) nbPlayers {
	return [game nbPlayers];
}

@end
