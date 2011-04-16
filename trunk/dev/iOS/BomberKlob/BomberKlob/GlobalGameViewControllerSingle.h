//
//  GlobalGameViewControllerSingle.h
//  BomberKlob
//
//  Created by Kilian Coubo on 14/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class GameActionViewController, GameInformationViewController, GameViewControllerSingle;


@interface GlobalGameViewControllerSingle : UIViewController {
    GameActionViewController * actionViewController;
	GameInformationViewController * informationViewController;
	GameViewControllerSingle * gameViewController;
}

@property (nonatomic,retain) GameViewControllerSingle * gameViewController;
@property (nonatomic,retain) GameInformationViewController * informationViewController;
@property (nonatomic,retain) GameActionViewController * actionViewController;

- (id) init;

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (void) touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event;
- (void) touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;

@end
