//
//  GameInformationViewController.h
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class GameInformationView, Engine, GlobalGameViewControllerSingle, Player;

@interface GameInformationViewController : NSObject {
    GameInformationView * informationView;
	GlobalGameViewControllerSingle * globalController;
}

@property (nonatomic,retain) GameInformationView * informationView;
@property (nonatomic,retain) GlobalGameViewControllerSingle * globalController;


- (id) initWithFrame:(CGRect)dimensionValue Controller:(GlobalGameViewControllerSingle *)controllerValue;
- (Player *) getHumanPlayer;
- (NSInteger) nbPlayers;

@end
