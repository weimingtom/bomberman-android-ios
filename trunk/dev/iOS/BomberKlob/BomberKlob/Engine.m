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
	}
	return self;
}


- (void)dealloc {
    [game release];
    [super dealloc];
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
					if([[[[[game.map.blocks objectAtIndex:i] objectAtIndex:j] class] description] isEqual:@"Bomb"])
						return false;
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
		if (object.position.y+yValue + resource.tileSize*2 > resource.screenWidth){
			return true;
		}
		if (object.position.y+yValue < 0){
			return true;
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

- (void) updateBombs{

	NSMutableArray * bombsDeleted = [[NSMutableArray alloc] init];
	for (Player * player in game.players) {
		for (Bomb * bomb in player.bombsPlanted) {
			[bomb update];
			if ([bomb hasAnimationFinished]) {
				[bombsDeleted addObject:bomb];
				
				Undestructible * fire = [[resource.bitmapsAnimates objectForKey:@"firecenter"] copy];
				fire.position = [[Position alloc] initWithPosition:bomb.position];
				
				[game.map addBlock:fire position:[[Position alloc] initWithX:bomb.position.x/resource.tileSize y:bomb.position.y/resource.tileSize]];
				[fire release];
				
				Position * firePosition;
				Position * firePositionMap;
				for (int i=1; i <= bomb.power; i++) {
					//Down
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
						if (![self isInCollision:fire :0 :0]){
							[game.map addBlock:fire position:firePositionMap];
							[fire release];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Bomb"]){
							[((Bomb *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]) destroyable];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Undestructible"]){
							if ([((Undestructible *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]).imageName isEqual:@"firedown"]) {
								fire = [[resource.bitmapsAnimates objectForKey:@"firevertical"] copy];
								fire.position = firePosition;
								[game.map addBlock:fire position:firePositionMap];
								[fire release];
							}
						}
						else {
							[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] destroyable];
						}
					}
					//UP
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
						if (![self isInCollision:fire :0 :0]){
							[game.map addBlock:fire position:firePositionMap];
							[fire release];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Bomb"]){
							[((Bomb *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]) destroyable];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Undestructible"]){
							if ([((Undestructible *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]).imageName isEqual:@"fireup"]) {
								fire = [[resource.bitmapsAnimates objectForKey:@"firevertical"] copy];
								fire.position = firePosition;
								[game.map addBlock:fire position:firePositionMap];
								[fire release];
							}
						}
						else {
							[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] destroyable];
						}
					}
					
					//Left
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
						if (![self isInCollision:fire :0 :0]){
							[game.map addBlock:fire position:firePositionMap];
							[fire release];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Bomb"]){
							[((Bomb *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]) destroyable];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Undestructible"]){
							if ([((Undestructible *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]).imageName isEqual:@"fireleft"]) {
								fire = [[resource.bitmapsAnimates objectForKey:@"firehorizontal"] copy];
								fire.position = firePosition;
								[game.map addBlock:fire position:firePositionMap];
								[fire release];
							}
						}
						else {
							[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] destroyable];
						}
					}
					
					//Right
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
						if (![self isInCollision:fire :0 :0]){
							[game.map addBlock:fire position:firePositionMap];
							[fire release];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Bomb"]){
							[((Bomb *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]) destroyable];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Undestructible"]){
							if ([((Undestructible *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]).imageName isEqual:@"fireright"]) {
								fire = [[resource.bitmapsAnimates objectForKey:@"firehorizontal"] copy];
								fire.position = firePosition;
								[game.map addBlock:fire position:firePositionMap];
								[fire release];
							}
						}
						else {
							[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] destroyable];
						}
					}
				}
			}
		}
		for (Bomb * bomb in bombsDeleted) {
			[player.bombsPlanted removeObject:bomb];
		}
		[bombsDeleted removeAllObjects];
	}
	[bombsDeleted release];
}

- (void) startTimerBombs
{
	NSThread * updateBombsThread = [[[NSThread alloc] initWithTarget:self selector:@selector(startTimerBombsThread) object:nil]autorelease]; //Create a new thread
	[updateBombsThread start]; //start the thread
}

- (void) startTimerBombsThread {
	
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	
	[[NSTimer scheduledTimerWithTimeInterval: 1 target: self selector: @selector(updateBombs) userInfo:self repeats: YES] retain];	
	[runLoop run];
	[pool release];
}


@end
