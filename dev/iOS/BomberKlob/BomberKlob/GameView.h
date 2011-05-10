//
//  View.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class Position, GameViewControllerSingle, Map;

@interface GameView : UIView {
	GameViewControllerSingle * controller;
	Position * currentPosition;
	Position * lastPosition;
	
	NSString * currentDirection;
	NSThread * movementThread;
	NSThread * updateThread;
	NSCondition * movementCondition;
	NSCondition * updateCondition;
	BOOL movementPause;
	BOOL updatePause;
	BOOL run;
}

@property (nonatomic,retain) Position * currentPosition;
@property (nonatomic, retain) Position * lastPosition;
@property (nonatomic,retain) GameViewControllerSingle * controller;

- (id) initWithController:(GameViewControllerSingle *) controllerValue frame:(CGRect) dimensionValue;
- (id) initWithFrame:(CGRect)frame;

- (void)drawAll: (CGContextRef) context;

- (void)startTimerUpdateMap;
- (void)startTimerUpdateMapThread;
- (void)updateMap;

- (void)startTimerMovement;
- (void)startTimerMovementThread;
- (void)timerMovement:(NSTimer *)timer;

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;

-(void) stopThread;
-(void) pauseThread:(BOOL) enable;
-(void) runThread;

-(BOOL) gameIsStarted;

- (void) updateMap;

@end
