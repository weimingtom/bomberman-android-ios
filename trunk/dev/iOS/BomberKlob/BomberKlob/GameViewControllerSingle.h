//
//  GameControllerSingle.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameViewController.h"

@class Engine;

@interface GameViewControllerSingle : GameViewController {
    Engine * engine;
	NSTimer * refreshTimer;
	NSThread* movementThread;
	NSString * currentDirection;
	BOOL run ;
	CGRect dimension;
}
@property (nonatomic) CGRect dimension;

- (id) initWithFrame:(CGRect)dimensionValue Engine:(Engine *)engineValue;

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;

- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;

- (void) touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event;

- (void) touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;

@end