//
//  View.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameView.h"
#import "RessourceManager.h"
#import "Player.h"
#import "Bomb.h"
#import "Position.h"
#import "GameViewControllerSingle.h"
#import "Game.h"
#import "GlobalGameViewControllerSingle.h"
#import "Engine.h"
#import "GameMap.h"


@implementation GameView
@synthesize  bitmapsInanimates, ressource,currentPosition,lastPosition,controller;

- (id) initWithController:(GameViewControllerSingle *) controllerValue frame:(CGRect) dimensionValue{	
	self = [self initWithFrame:dimensionValue];
	
	if (self){
		self.controller = controllerValue;
		ressource = [RessourceManager sharedRessource];
		lastPosition = [[Position alloc] init];
		currentPosition = [[Position alloc] init];
		[self startTimerUpdateMap];
		[self startTimerMovement];

	}
	
	return self;
}


- (id) initWithFrame:(CGRect)frame{	
	self = [super initWithFrame:frame];
	
	if (self){
		ressource = [RessourceManager sharedRessource];
		bitmapsInanimates = ressource.bitmapsInanimates;
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	CGContextRef context = UIGraphicsGetCurrentContext();

	[self drawAll:context];	
}

- (void)drawAll: (CGContextRef) context{
	Game * game = controller.globalController.engine.game;
	[game draw:context];
}

-(void) startTimerUpdateMap {
	updatePause = NO;
	updateCondition = [[NSCondition alloc] init];
	updateThread = [[NSThread alloc] initWithTarget:self selector:@selector(startTimerUpdateMapThread) object:nil]; //Create a new thread
	[updateThread start]; //start the thread
}

//the thread starts by sending this message
-(void) startTimerUpdateMapThread {
	
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];

	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];

	[[NSTimer scheduledTimerWithTimeInterval: 0.03 target: self selector: @selector(updateMap) userInfo:self repeats: YES] retain];	
	[runLoop run];
	[pool release];
}

- (void) updateMap{
	if (![updateThread isCancelled]) {
		[updateCondition lock];
		while (updatePause) {
			[updateCondition wait];
		}
		[controller.globalController.engine.game.map update];
		[self setNeedsDisplay];
		[updateCondition unlock];
	}
}

-(void) startTimerMovement
{
	movementCondition = [[NSCondition alloc] init];
	movementPause = NO;
	movementThread = [[NSThread alloc] initWithTarget:self selector:@selector(startTimerMovementThread) object:nil]; //Create a new thread
	[movementThread start]; //start the thread
}

-(void) startTimerMovementThread {
	
	while (!controller.globalController.engine.game.isStarted) {}
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	[[NSTimer scheduledTimerWithTimeInterval: 0.02 target: self selector: @selector(timerMovement:) userInfo:nil repeats: YES] retain];
	[runLoop run];	
	[pool release];

}

- (void)timerMovement:(NSTimer *)timer {
	if (![movementThread isCancelled]) {
		[movementCondition lock];
		while (movementPause) {
			[movementCondition wait];
		}
		
		Engine * engine = controller.globalController.engine;
		if (run) {
			if (currentDirection == @"right") {
				[engine moveRight];
			}
			else if (currentDirection == @"left") {
				[engine moveLeft];
			}
			else if (currentDirection == @"down") {
				[engine moveDown];
			}
			else if (currentDirection == @"top") {
				[engine moveTop];
			}
			else if (currentDirection == @"rightTop") {
				[engine moveRightTop];
			}
			else if (currentDirection == @"leftTop") {
				[engine moveLeftTop];
			}
			else if (currentDirection == @"rightDown") {
				[engine moveRightDown];
			}
			else if (currentDirection == @"leftDown") {
				[engine moveLeftDown];
			}
		}
		else {
			if ([currentDirection isEqualToString:@"stop_up"]) {
				[engine stopTop];
			}
			else if ([currentDirection isEqualToString:@"stop_down"]) {
				[engine stopDown];
			}
			else if ([currentDirection isEqualToString:@"stop_right"]) {
				[engine stopRight];
			}
			else if ([currentDirection isEqualToString:@"stop_left"]) {
				[engine stopLeft];
			}
			else if ([currentDirection isEqualToString:@"stop_up_right"]) {
				[engine stopRightTop];
			}
			else if ([currentDirection isEqualToString:@"stop_up_left"]) {
				[engine stopLeftTop];
			}
			else if ([currentDirection isEqualToString:@"stop_down_right"]) {
				[engine stopRightDown];
			}
			else if ([currentDirection isEqualToString:@"stop_down_left"]) {
				[engine stopLeftDown];
			}
		}
		if ([engine.game.players count] > 0 && ![lastPosition isEqual:currentPosition]) {
			[[engine.game.players objectAtIndex:0] update];
		}
		[movementCondition unlock];
	}
}


- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	CGPoint pt = [[touches anyObject] locationInView:self];
	lastPosition.x = currentPosition.x;
	lastPosition.y = currentPosition.y;
	currentPosition.x = pt.x;
	currentPosition.y = pt.y;
	run = YES;
	currentDirection = @"";
}


- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	CGPoint pt = [[touches anyObject] locationInView:self];
	NSUInteger marge = 20;
	run = YES;
    
	lastPosition.x = currentPosition.x;
	lastPosition.y = currentPosition.y;
	currentPosition.x = pt.x;
	currentPosition.y = pt.y;
	
	if (lastPosition.x > currentPosition.x && lastPosition.y > currentPosition.y) {
		currentDirection = @"leftTop";
	}
	else if (lastPosition.x > currentPosition.x && lastPosition.y < currentPosition.y) {
		currentDirection = @"leftDown";
	}
	else if (lastPosition.x < currentPosition.x && lastPosition.y < currentPosition.y) {
		currentDirection = @"rightDown";
	}
	else if (lastPosition.x < currentPosition.x && lastPosition.y > currentPosition.y) {
		currentDirection = @"rightTop";
	}
	
	else if (lastPosition.x > currentPosition.x && lastPosition.y > currentPosition.y-marge && lastPosition.y < currentPosition.y+marge) {
		currentDirection = @"left";
	}
	else if (lastPosition.x < currentPosition.x  && lastPosition.y > currentPosition.y-marge && lastPosition.y < currentPosition.y+marge) {
		currentDirection = @"right";
	}
	else if (lastPosition.y > currentPosition.y && lastPosition.x < currentPosition.x+marge  && lastPosition.x > currentPosition.x-marge) {
		currentDirection = @"top";
	}
	else if (lastPosition.y < currentPosition.y && lastPosition.x < currentPosition.x+marge  && lastPosition.x > currentPosition.x-marge) {
		currentDirection = @"down";
	}
}


- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event{
	run = NO;
}


- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event{
	run = NO;
	if ([currentDirection isEqualToString:@"top"]) {
		currentDirection = @"stop_up";
	}
	else if ([currentDirection isEqualToString:@"down"]) {
		currentDirection = @"stop_down";
	}
	else if ([currentDirection isEqualToString:@"right"]) {
		currentDirection = @"stop_right";
	}
	else if ([currentDirection isEqualToString:@"left"]) {
		currentDirection = @"stop_left";
	}
	else if ([currentDirection isEqualToString:@"rightTop"]) {
		currentDirection = @"stop_up_right";
	}
	else if ([currentDirection isEqualToString:@"leftTop"]) {
		currentDirection = @"stop_up_left";
	}
	else if ([currentDirection isEqualToString:@"rightDown"]) {
		currentDirection = @"stop_down_right";
	}
	else if ([currentDirection isEqualToString:@"leftDown"]) {
		currentDirection = @"stop_down_left";
	}
}


-(void) stopThread{
	[movementThread cancel];
	[updateThread cancel];
}

-(void) pauseThread:(BOOL) enable{
	if (enable) {
		updatePause = enable;
		movementPause = enable;
	}
	else {
		updatePause = enable;
		movementPause = enable;
		[updateCondition lock];
		[updateCondition signal];
		[updateCondition unlock];
		
		[movementCondition lock];
		[movementCondition signal];
		[movementCondition unlock];
	}
}

-(void) runThread{
	[movementThread start];
	[updateThread start];
}

@end
