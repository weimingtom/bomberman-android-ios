//
//  GameActionView.h
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class Engine;


@interface GameActionView : UIView {
    Engine * engine;
}

- (id) initWithFrame:(CGRect)frame;
- (void) initComponents;

@end
