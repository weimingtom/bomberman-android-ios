//
//  GameControllerSingle.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameViewController.h"

@class Engine, GlobalGameViewControllerSingle;

@interface GameViewControllerSingle : NSObject {
    GlobalGameViewControllerSingle * globalController;
	GameView * gameView;
}
@property (nonatomic,retain) GlobalGameViewControllerSingle * globalController;
@property (nonatomic,retain) GameView * gameView;

- (id) initWithFrame:(CGRect)dimensionValue Controller:(GlobalGameViewControllerSingle *)controllerValue;

-(BOOL) gameIsStarted;

- (void) updateMap;

@end
