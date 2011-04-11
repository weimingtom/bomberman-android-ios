//
//  GameController.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameView.h"
@class Position;

@interface GameViewController : UIViewController {
    GameView * gameView;
	Position * currentPosition;
	Position * lastPosition;

}

@property (nonatomic, retain) GameView * gameView;

- (id) init;
- (void)load;
- (void)remove;
- (void) managementMovement;
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (void) touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event;
- (void) touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;


@end
