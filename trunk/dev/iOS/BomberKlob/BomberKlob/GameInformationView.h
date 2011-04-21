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
}
@property (nonatomic,retain) GameInformationViewController * controller;

- (id) initWithFrame:(CGRect)frame Engine:(Engine *)engineValue;
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;

- (void) initComponents;

- (void)pauseAction;

@end
