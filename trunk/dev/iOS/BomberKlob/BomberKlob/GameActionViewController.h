//
//  GameActionViewController.h
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class GameActionView, GlobalGameViewControllerSingle, Position;

@interface GameActionViewController : NSObject {
	GlobalGameViewControllerSingle * globalController;
	GameActionView * actionView;
    
}

@property (nonatomic,retain) GameActionView * actionView;
@property (nonatomic,retain) GlobalGameViewControllerSingle * globalController;


- (id) initWithFrame:(CGRect)dimensionValue Controller:(GlobalGameViewControllerSingle *)controllerValue;

- (void)plantingBomb;

@end
