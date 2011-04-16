//
//  GameActionViewController.h
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class GameActionView;

@interface GameActionViewController : UIViewController {
	GameActionView * actionView;
	CGRect dimension;
    
}

@property (nonatomic,retain) GameActionView * actionView;
@property (nonatomic) CGRect dimension;


- (id) init;

@end
