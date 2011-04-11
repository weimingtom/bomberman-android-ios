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
		engine = [[Engine alloc] initWithGame:[[Game alloc] init]];
		self.gameView = [[GameView alloc] initWithMap: engine.game.map];	
		gameView.players = engine.game.players;
		[self.view addSubview:gameView];
		[gameView release];
	}
	return self;
}


- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	NSLog(@"TOUCHES : %@", [touches anyObject] );
	UITouch * touch = [touches anyObject];
	CGPoint pt = [touch locationInView:self.view];
	lastPosition.x = currentPosition.x;
	lastPosition.y = currentPosition.y;
	currentPosition.x = pt.x;
	currentPosition.y = pt.y;
	[self.gameView setNeedsDisplay];
	
}
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	CGPoint pt = [[touches anyObject] locationInView:self.view];

	lastPosition.x = currentPosition.x;
	lastPosition.y = currentPosition.y;
	currentPosition.x = pt.x;
	currentPosition.y = pt.y;
	if (lastPosition.x > currentPosition.x) {
		engine.moveLeft;
	}
	if (lastPosition.x < currentPosition.x) {
		engine.moveRight;
	}
	if (lastPosition.y > currentPosition.y) {
		engine.moveTop;
	}
	if (lastPosition.y < currentPosition.y) {
		engine.moveDown;
	}
	[self.gameView setNeedsDisplay];


}
- (void) touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event{

}
- (void) touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event{

}

@end
