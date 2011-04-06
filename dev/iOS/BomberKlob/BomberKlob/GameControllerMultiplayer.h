//
//  GameControllerMultiplayer.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameViewController.h"
#import "Multiplayer.h"


@interface GameControllerMultiplayer : GameViewController {
    Multiplayer * multiPlayer;
}

- (void) sendRequest;
- (void) analyseResponse;
@end
