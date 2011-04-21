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


- (BOOL) isInCollision: (Object *) object: (NSInteger) xValue: (NSInteger) yValue{
	
	// If the object is a player
	if ([[[object class] description] isEqualToString:@"Player"] ) {
		
		//We first checks if there is a collision with sides of the screen
		if (object.position.x+xValue < 0) {
			return true;
		}
		if (object.position.x+xValue + resource.tileSize > resource.screenHeight){
			return true;
		}
		if (object.position.y+yValue + (resource.tileSize*1.5) > resource.screenWidth){
				return true;
		}
		if (object.position.y+yValue < 0){
			return true;
		}

		//We calculates the smallest and largest object's coordinates
		int marge = 5; //Margin movement
		
		int xmin = floor((object.position.x+xValue+marge) / resource.tileSize);
		int ymin = floor((object.position.y+yValue+marge+resource.tileSize) / resource.tileSize);
		
		int xmax = ceil((object.position.x+xValue + resource.tileSize-marge) / resource.tileSize);
		int ymax = floor(((object.position.y+yValue + (resource.tileSize*1.5)-marge) / resource.tileSize));
		
		
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
	NSMutableDictionary * firecenter = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsAnimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"firecenter", nil]]];
	NSMutableDictionary * firehorizontal = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsAnimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"firehorizontal", nil]]];
	NSMutableDictionary * firevertical = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsAnimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"firevertical", nil]]];
	NSMutableDictionary * firedown = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsAnimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"firedown", nil]]];
	NSMutableDictionary * fireup = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsAnimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"fireup", nil]]];
	NSMutableDictionary * fireleft = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsAnimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"fireleft", nil]]];
	NSMutableDictionary * fireright = [[NSMutableDictionary alloc] initWithDictionary:[resource.bitmapsAnimates dictionaryWithValuesForKeys:[[NSArray alloc] initWithObjects:@"fireright", nil]]];

	NSMutableArray * bombsDeleted = [[NSMutableArray alloc] init];
	for (Player * player in game.players) {
		for (Bomb * bomb in player.bombsPlanted) {
			[bomb update];
			if ([bomb hasAnimationFinished]) {
				[bombsDeleted addObject:bomb];
				
				Undestructible * fire = [[Undestructible alloc]initWithImageName:@"firecenter" position:bomb.position animations:firecenter];
				
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
							fire = [[Undestructible alloc]initWithImageName:@"firevertical" position:firePosition animations:firevertical];
						}
						else{
							fire = [[Undestructible alloc]initWithImageName:@"firedown" position:firePosition animations:firedown];
						}
						if (![self isInCollision:fire :0 :0]){
							[game.map addBlock:fire position:firePositionMap];
							[fire release];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Bomb"]){
							[((Bomb *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]) destroy];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Undestructible"]){
							if ([((Undestructible *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]).imageName isEqual:@"firedown"]) {
								fire = [[Undestructible alloc]initWithImageName:@"firevertical" position:firePosition animations:firevertical];
								[game.map addBlock:fire position:firePositionMap];
								[fire release];
							}
						}
					}
					//UP
					firePosition = [[Position alloc] initWithX:bomb.position.x y:bomb.position.y-(resource.tileSize*i)];
					firePositionMap = [[Position alloc] initWithX:(bomb.position.x/resource.tileSize) y:(bomb.position.y/resource.tileSize)-i ];
					if (firePositionMap.y >= 0) {
						if (i != bomb.power) {
							fire = [[Undestructible alloc]initWithImageName:@"firevertical" position:firePosition animations:firevertical];
						}
						else{
							fire = [[Undestructible alloc]initWithImageName:@"fireup" position:firePosition animations:fireup];
						}
						if (![self isInCollision:fire :0 :0]){
							[game.map addBlock:fire position:firePositionMap];
							[fire release];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Bomb"]){
							[((Bomb *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]) destroy];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Undestructible"]){
							if ([((Undestructible *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]).imageName isEqual:@"fireup"]) {
								fire = [[Undestructible alloc]initWithImageName:@"firevertical" position:firePosition animations:firevertical];
								[game.map addBlock:fire position:firePositionMap];
								[fire release];
							}
						}
					}
					
					//Left
					firePosition = [[Position alloc] initWithX:bomb.position.x-(resource.tileSize*i) y:bomb.position.y];
					firePositionMap = [[Position alloc] initWithX:(bomb.position.x/resource.tileSize)-i y:(bomb.position.y/resource.tileSize) ];
					if (firePositionMap.x >=0) {
						if (i != bomb.power) {
							fire = [[Undestructible alloc]initWithImageName:@"firehorizontal" position:firePosition animations:firehorizontal];
						}
						else{
							fire = [[Undestructible alloc]initWithImageName:@"fireleft" position:firePosition animations:fireleft];
						}
						if (![self isInCollision:fire :0 :0]){
							[game.map addBlock:fire position:firePositionMap];
							[fire release];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Bomb"]){
							[((Bomb *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]) destroy];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Undestructible"]){
							if ([((Undestructible *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]).imageName isEqual:@"fireleft"]) {
								fire = [[Undestructible alloc]initWithImageName:@"firehorizontal" position:firePosition animations:firehorizontal];
								[game.map addBlock:fire position:firePositionMap];
								[fire release];
							}
						}
					}
					
					//Right
					firePosition = [[Position alloc] initWithX:bomb.position.x+(resource.tileSize*i) y:bomb.position.y];
					firePositionMap = [[Position alloc] initWithX:(bomb.position.x/resource.tileSize)+i y:(bomb.position.y/resource.tileSize) ];
					if (firePositionMap.x < game.map.width) {
						if (i != bomb.power) {
							fire = [[Undestructible alloc]initWithImageName:@"firehorizontal" position:firePosition animations:firehorizontal];
						}
						else{
							fire = [[Undestructible alloc]initWithImageName:@"fireright" position:firePosition animations:fireright];
						}
						if (![self isInCollision:fire :0 :0]){
							[game.map addBlock:fire position:firePositionMap];
							[fire release];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Bomb"]){
							[((Bomb *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]) destroy];
						}
						else if([[[[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y] class] description] isEqual:@"Undestructible"]){
							if ([((Undestructible *)[[game.map.blocks objectAtIndex:firePositionMap.x] objectAtIndex:firePositionMap.y]).imageName isEqual:@"fireright"]) {
								fire = [[Undestructible alloc]initWithImageName:@"firehorizontal" position:firePosition animations:firehorizontal];
								[game.map addBlock:fire position:firePositionMap];
								[fire release];
							}
						}
					}

					[firePosition release];
					[firePositionMap release];
				}
			}
		}
		for (Bomb * bomb in bombsDeleted) {
			[player.bombsPlanted removeObject:bomb];
		}
		[bombsDeleted removeAllObjects];
	}
	[bombsDeleted release];
	[firecenter release];
	[firehorizontal release];
	[firevertical release];
	[firedown release];
	[fireup release];
	[fireleft release];
	[fireright release];

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
