//
//  MainMenuViewController.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface MainMenuViewController : UIViewController {
    
    UIButton *pseudo;
    UIButton *newProfil;
}

@property (nonatomic, retain) IBOutlet UIButton *pseudo;
@property (nonatomic, retain) IBOutlet UIButton *newProfil;

- (IBAction)singlePlayerAction:(id)sender;
- (IBAction)multiplayerAction:(id)sender;
- (IBAction)optionsAction:(id)sender;
- (IBAction)createMapAction:(id)sender;
- (IBAction)helpAction:(id)sender;
- (IBAction)pseudoAction:(id)sender;
- (IBAction)newProfilAction:(id)sender;

@end
