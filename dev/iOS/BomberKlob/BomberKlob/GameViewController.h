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


@end
