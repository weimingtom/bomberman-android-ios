//
//  GameInformationView.h
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class RessourceManager, Engine, GameInformationViewController;


@interface GameInformationView : UIView {
	GameInformationViewController * controller;
	NSThread * updateThread;
}
@property (nonatomic,retain) GameInformationViewController * controller;

- (id) initWithFrame:(CGRect)frame Controller:(GameInformationViewController *) controllerValue;

- (void) initComponents;

- (void)pauseAction;
- (void) initUpdateThread;
- (void) startTimerUpdate;

@end
