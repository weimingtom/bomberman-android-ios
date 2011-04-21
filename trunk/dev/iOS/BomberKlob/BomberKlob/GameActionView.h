//
//  GameActionView.h
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class Engine, GameActionViewController;


@interface GameActionView : UIView {
    GameActionViewController * controller;
}

@property (nonatomic,retain) GameActionViewController * controller;

- (id) initWithFrame:(CGRect)frame;
- (void) initComponents;
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;

@end
