//
//  GameControllerSingle.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameViewControllerSingle.h"
#import "Engine.h"
#import "RessourceManager.h"


@implementation GameViewControllerSingle
@synthesize dimension;

- (id) initWithFrame:(CGRect)dimensionValue Engine:(Engine *)engineValue{
	self = [super init];
	
	if (self){
		RessourceManager * resource = [RessourceManager sharedRessource];
		engine = engineValue;
		dimension = dimensionValue;
		self.gameView = [[GameView alloc] initWithMap: engine.game.map frame:dimension];	

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
	[[NSTimer scheduledTimerWithTimeInterval: 0.03125 target: self selector: @selector(timerTick:) userInfo:nil repeats: YES] retain];	
	
	[runLoop run];
	[pool release];
	
}

- (void)timerTick:(NSTimer *)timer {
	NSLog(@"timer : %@",timer );
	if (run) {
		if (currentDirection == @"right") {
			engine.moveRight;
		}
		else if (currentDirection == @"left") {
			engine.moveLeft;
		}
		else if (currentDirection == @"down") {
			engine.moveDown;
		}
		else if (currentDirection == @"top") {
			engine.moveTop;
		}
		else if (currentDirection == @"rightTop") {
			engine.moveRightTop;
		}
		else if (currentDirection == @"leftTop") {
			engine.moveLeftTop;
		}
		else if (currentDirection == @"rightDown") {
			engine.moveRightDown;
		}
		else if (currentDirection == @"leftDown") {
			engine.moveLeftDown;
		}
		//[self.gameView setNeedsDisplay];
		//[self.gameView threadUpdate];
	}
	else {
		NSLog(@"Arret du thread");
		[NSThread exit];
	}
}


- (void)touchesBegan:(CGPoint) pt{
	NSLog(@" SINGLE pt.X : %f , pt.Y : %f",pt.x,pt.y);
	lastPosition.x = currentPosition.x;
	lastPosition.y = currentPosition.y;
	currentPosition.x = pt.x;
	currentPosition.y = pt.y;
	[self startTimer];
	run = true;
	currentDirection = @"";
	
}




- (void)touchesMoved: (CGPoint) pt {
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
- (void) touchesCancelled:(CGPoint) pt{
	run = NO;
}
- (void) touchesEnded:(CGPoint) pt{
	run = NO;

}

@end
