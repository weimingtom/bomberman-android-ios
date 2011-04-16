//
//  SinglePlayerMenuViewController.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 09/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SinglePlayerMenuViewController : UIViewController {
    
    UIBarButtonItem *play;
    UILabel *mapName;
}

@property (nonatomic, retain) IBOutlet UIBarButtonItem *play;
@property (nonatomic, retain) IBOutlet UILabel *mapName;

- (IBAction)playAction:(id)sender;

@end
