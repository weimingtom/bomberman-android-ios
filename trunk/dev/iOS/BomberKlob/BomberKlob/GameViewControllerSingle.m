//
//  GameControllerSingle.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameViewControllerSingle.h"
#import "Engine.h"


@implementation GameViewControllerSingle

- (id) init{
	self = [super init];
	
	if (self){
		//[[UIDevice currentDevice] setOrientation:UIInterfaceOrientationLandscapeRight];
		engine = [[Engine alloc] initWithGame:[[Game alloc] init]];
		self.gameView = [[GameView alloc] initWithMap: engine.game.map];	
		gameView.players = engine.game.players;
		[self.view addSubview:gameView];
		[gameView release];
	}
	return self;
}

-(void) startTimer
{
	movementThread = [[[NSThread alloc] initWithTarget:self selector:@selector(startTimerThread) object:nil]autorelease]; //Create a new thread
	[movementThread start]; //start the thread
}

//the thread starts by sending this message
-(void) startTimerThread {

	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	[[NSTimer scheduledTimerWithTimeInterval: 1/3 target: self selector: @selector(timerTick:) userInfo:nil repeats: YES] retain];	
	[runLoop run];
	[pool release];
}

- (void)timerTick:(NSTimer *)timer {
	NSLog(@"Direction : %@",currentDirection);
	
	if (run) {
		if (currentDirection == @"right") {
			engine.moveRight;
		}
		if (currentDirection == @"left") {
			engine.moveLeft;
		}
		if (currentDirection == @"down") {
			engine.moveDown;
		}
		if (currentDirection == @"top") {
			engine.moveTop;
		}
		if (currentDirection == @"rightTop") {
			engine.moveRightTop;
		}
		if (currentDirection == @"leftTop") {
			engine.moveLeftTop;
		}
		if (currentDirection == @"rightDown") {
			engine.moveRightDown;
		}
		if (currentDirection == @"rightTop") {
			engine.moveRightTop;
		}
		[self.gameView setNeedsDisplay];
	}
	else {
		NSLog(@"Arret du thread");
		//[NSThread exit];
	}
}


- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	UITouch * touch = [touches anyObject];
	CGPoint pt = [touch locationInView:self.view];
	lastPosition.x = currentPosition.x;
	lastPosition.y = currentPosition.y;
	currentPosition.x = pt.x;
	currentPosition.y = pt.y;
	[self.gameView setNeedsDisplay];
	[self startTimer];
	
}




- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	NSUInteger marge = 10;
	run = YES;
	CGPoint pt = [[touches anyObject] locationInView:self.view];

	lastPosition.x = currentPosition.x;
	lastPosition.y = currentPosition.y;
	currentPosition.x = pt.x;
	currentPosition.y = pt.y;
	
	if (lastPosition.x > currentPosition.x && lastPosition.y > currentPosition.y-marge && lastPosition.y < currentPosition.y+marge) {
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
	
	else if (lastPosition.x > currentPosition.x && lastPosition.y > currentPosition.y) {
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
	
	/*if (lastPosition.x > currentPosition.x) {
	 if (lastPosition.x > currentPosition.x && lastPosition.y > currentPosition.y) {
	 engine.moveLeftTop;
	 }
	 else if (lastPosition.x > currentPosition.x && lastPosition.y < currentPosition.y) {
	 engine.moveLeftDown;
	 }
	 else
	 engine.moveLeft;
	 }
	 
	 if (lastPosition.x < currentPosition.x) {
	 if (lastPosition.x < currentPosition.x && lastPosition.y < currentPosition.y) {
	 engine.moveRightDown;
	 }
	 else if (lastPosition.x < currentPosition.x && lastPosition.y > currentPosition.y) {
	 engine.moveRightTop;
	 }
	 else
	 engine.moveRight;
	 }
	 if (lastPosition.y > currentPosition.y) {
	 if (lastPosition.x > currentPosition.x && lastPosition.y > currentPosition.y) {
	 engine.moveLeftTop;
	 }
	 else if (lastPosition.x < currentPosition.x && lastPosition.y > currentPosition.y) {
	 engine.moveRightTop;
	 }
	 else
	 engine.moveTop;
	 }
	 
	 if (lastPosition.y < currentPosition.y) {
	 if (lastPosition.x > currentPosition.x && lastPosition.y < currentPosition.y) {
	 engine.moveLeftDown;
	 }
	 else if (lastPosition.x < currentPosition.x && lastPosition.y < currentPosition.y) {
	 engine.moveRightDown;
	 }
	 else
	 engine.moveDown;
	 }*/

	
	[self.gameView setNeedsDisplay];


}
- (void) touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event{
	NSLog(@"cancel");

	run = NO;
}
- (void) touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event{
	NSLog(@"ended");
	run = NO;

}

@end
