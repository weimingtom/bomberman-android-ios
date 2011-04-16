//
//  GameInformationViewController.h
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class GameInformationView;

@interface GameInformationViewController : UIViewController {
    GameInformationView * informationView;
	CGRect dimension;
}

@property (nonatomic,retain) GameInformationView * informationView;
@property (nonatomic) CGRect dimension;

@end
