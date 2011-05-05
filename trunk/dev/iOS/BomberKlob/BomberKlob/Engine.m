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

@implementation Engine

@synthesize game;

- (id) initWithGame:(Game *) gameValue{
	self = [super init];
	if (self){
		resource = [RessourceManager sharedRessource];
		self.game = gameValue;
		[self startTimerBombs];
		[game startGame];
	}
	return self;
}


- (void)dealloc {
    [game release];
    [super dealloc];
}
- (void) collisionWithPlayer: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue{
	for (Player * player in game.players) {
		CGRect rectObject = CGRectMake(object.position.x+xValue, object.position.y+yValue, resource.tileSize,resource.tileSize);
		CGRect rectPlayer = CGRectMake(player.position.x, player.position.y,resource.tileSize,resource.tileSize);
		if (CGRectIntersectsRect(rectObject, rectPlayer)) {
			[player destroy];
		}
	}
}

- (BOOL) isInCollisionWithABomb: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue{
	for (Position * position in game.bombsPlanted) {
		CGRect rectBomb = CGRectMake(position.x, position.y, resource.tileSize, resource.tileSize);
		CGRect rectObj = CGRectMake(object.position.x+xValue, object.position.y+yValue, resource.tileSize, resource.tileSize);
		if (CGRectIntersectsRect(rectObj, rectBomb)) {
			return true;
		}
	}
}


- (BOOL) isInCollision: (Objects *) object: (NSInteger) xValue: (NSInteger) yValue{
	
	// If the object is a player
	if ([[[object class] description] isEqualToString:@"Player"] ) {
		
		//We first checks if there is a collision with sides of the screen
		if (object.position.x+xValue < 0) {
			return true;
		}
		if (object.position.x+xValue + resource.tileSize > game.map.width*resource.tileSize){
			return true;
		}
		if (object.position.y+yValue + (resource.tileSize) > game.map.height*resource.tileSize){
				return true;
		}
		if (object.position.y+yValue < 0){
			return true;
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
					//If it's a bomb
					if([[[[[game.map.blocks objectAtIndex:i] objectAtIndex:j] class] description] isEqual:@"Bomb"]) {
						return false;
					}
					
					//If it is another thing nonempty
					else if (![[[game.map.blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"]){
						return true;
					}
				}
				else
					return true;
				
			}
		}
	}
	// If it's not a player
	else {

		
		//We first checks if there is a collision with sides of the screen
		if (object.position.x+xValue < 0) {
			return true;
		}
		if (object.position.x+xValue + resource.tileSize > resource.screenHeight){
			return true;
		}
		if (object.position.y+yValue + resource.tileSize > resource.screenWidth){
			return true;
		}
		if (object.position.y+yValue < 0){
			return true;
		}
		
		for (Position * position in game.map.animates) {
			CGRect rect = CGRectMake(position.x, position.y, resource.tileSize, resource.tileSize);
			CGRect rectObj = CGRectMake(object.position.x+xValue, object.position.y+yValue, resource.tileSize, resource.tileSize);
			if (CGRectIntersectsRect(rectObj, rect)) {
				return true;
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
					if (![[[game.map.blocks objectAtIndex:i] objectAtIndex:j] isEqual:@"empty"])
						return true;
				}
				else
					return true;
			}
		}
	}
	return false;    
}


- (void) moveTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :0 :-player.speed]){
		player.moveTop;
	}
	
}

- (void) moveDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :0 :player.speed]){
		player.moveDown;
	}
}

- (void) moveLeft{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :-player.speed :0]){
		player.moveLeft;
	}
}

- (void) moveRight{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :player.speed :0]){
		player.moveRight;
	}
}

- (void) moveLeftTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :-player.speed :-player.speed]){
		player.moveLeftTop;
	}
}

- (void) moveLeftDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :-player.speed :player.speed]){
		player.moveLeftDown;
	}
}

- (void) moveRightDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :player.speed :player.speed]){
		player.moveRightDown;
	}
}

- (void) moveRightTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	if (![self isInCollision:player :player.speed :-player.speed]){
		player.moveRightTop;
	}
    
}

- (void) stopTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	[player stopTop];
}

- (void) stopDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	[player stopDown];
}

- (void) stopLeft{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	[player stopLeft];
}

- (void) stopRight{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	[player stopRight];
}


- (void) stopLeftTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	[player stopLeftTop];
}

- (void) stopRightTop{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	[player stopRightTop];
}

- (void) stopLeftDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	[player stopLeftDown];
}

- (void) stopRightDown{
	Player * player = ((Player *)[game.players objectAtIndex:0]);
	[player stopRightDown];
}

- (void) updateBombs{
	NSMutableArray * bombsDeleted = [[NSMutableArray alloc] init];
	for (Position * position in game.bombsPlanted) {
		Bomb * bomb = [game.bombsPlanted objectForKey:position];
		[bomb update];
	}
	while ([self thereAreBombToExplode]) {
		for (Position * position in game.bombsPlanted) {
			Bomb * bomb = [game.bombsPlanted objectForKey:position];
			if ([bomb hasAnimationFinished]) {
				[self displayFire:bomb];
				[bombsDeleted addObject:position];
				break;
			}
		}
		for (Position * position in bombsDeleted) {
			[game.bombsPlanted removeObjectForKey:position];
		}
		
		[bombsDeleted removeAllObjects];
	}
	[bombsDeleted release];
	
}

- (void) startTimerBombs{
	@synchronized (self) {
		NSThread * updateBombsThread = [[[NSThread alloc] initWithTarget:self selector:@selector(startTimerBombsThread) object:nil]autorelease]; //Create a new thread
		[updateBombsThread start]; //start the thread
	}
}

- (void) startTimerBombsThread {
	
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	
	[[NSTimer scheduledTimerWithTimeInterval: 2 target: self selector: @selector(updateBombs) userInfo:self repeats: YES] retain];	
	[runLoop run];
	[pool release];
}

- (BOOL) thereAreBombToExplode {
	for (Position * position in game.bombsPlanted){
		if ([[game.bombsPlanted objectForKey:position] hasAnimationFinished]) {
			return true;
		}
	}
	return false;
}

- (void) displayFire:(Bomb *) bomb{
	BOOL stopFireDown = false;
	BOOL stopFireUp = false;
	BOOL stopFireLeft = false;
	BOOL stopFireRight = false;
	Undestructible * fire = [[resource.bitmapsAnimates objectForKey:@"firecenter"] copy];
	fire.position = [[Position alloc] initWithPosition:bomb.position];
	[self collisionWithPlayer:fire :0 :0];
	
	[game.map addBlock:fire];
	
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
				[self collisionWithPlayer:fire :0 :0];
				if([self isInCollisionWithABomb:fire :0 :0]){
					[[game.bombsPlanted objectForKey:firePosition ] destroy];
				}
				else if (![self isInCollision:fire :0 :0]){
					[game.map addBlock:fire];
					[fire release];
				}
				else if([((Objects *)[game.map.animates objectForKey:firePosition]).imageName isEqualToString:@"firedown"]){
					fire = [[resource.bitmapsAnimates objectForKey:@"firevertical"] copy];
					fire.position = firePosition;
					[game.map addBlock:fire];
					[fire release];
				}
				else {
					[(Objects *)[game.map.animates objectForKey:firePosition] destroy];
					stopFireDown = true;
				}
			}
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
				[self collisionWithPlayer:fire :0 :0];
				if([self isInCollisionWithABomb:fire :0 :0]){
					[[game.bombsPlanted objectForKey:firePosition ] destroy];
				}
				else if (![self isInCollision:fire :0 :0]){
					[game.map addBlock:fire];
					[fire release];
				}
				else if([((Objects *)[game.map.animates objectForKey:firePosition]).imageName isEqualToString:@"fireup"]){
					fire = [[resource.bitmapsAnimates objectForKey:@"firevertical"] copy];
					fire.position = firePosition;
					[game.map addBlock:fire];
					[fire release];
				}
				else {
					[(Objects *)[game.map.animates objectForKey:firePosition] destroy];
					stopFireUp = true;	
				}
			}
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
				[self collisionWithPlayer:fire :0 :0];
				if([self isInCollisionWithABomb:fire :0 :0]){
					[[game.bombsPlanted objectForKey:firePosition ] destroy];
				}
				else if (![self isInCollision:fire :0 :0]){
					[game.map addBlock:fire];
					[fire release];
				}
				else if([((Objects *)[game.map.animates objectForKey:firePosition]).imageName isEqualToString:@"fireleft"]){
					fire = [[resource.bitmapsAnimates objectForKey:@"firehorizontal"] copy];
					fire.position = firePosition;
					[game.map addBlock:fire];
					[fire release];
				}
				else {
					[(Objects *)[game.map.animates objectForKey:firePosition] destroy];
					stopFireLeft = true;
				}
			}
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
				[self collisionWithPlayer:fire :0 :0];
				if([self isInCollisionWithABomb:fire :0 :0]){
					[[game.bombsPlanted objectForKey:firePosition ] destroy];
				}
				else if (![self isInCollision:fire :0 :0]){
					[game.map addBlock:fire];
					[fire release];
				}
				else if([((Objects *)[game.map.animates objectForKey:firePosition]).imageName isEqualToString:@"fireright"]){
					fire = [[resource.bitmapsAnimates objectForKey:@"firehorizontal"] copy];
					fire.position = firePosition;
					[game.map addBlock:fire];
					[fire release];
				}
				else {
					[(Objects *)[game.map.animates objectForKey:firePosition] destroy];
					stopFireRight = true;
				}
			}
		}
	}
}


@end
