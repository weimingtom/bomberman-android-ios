//
//  OptionsMenuViewController.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface OptionsMenuViewController : UITableViewController {
    
    UITableViewCell *volumeCell;
    UITableViewCell *muteCell;
    UITableViewCell *languageCell;
    UITableViewCell *accountManagerCell;
    
    UISwitch *mute;
    UISlider *soundSlider;
    UILabel *language;
}

@property (nonatomic, retain) IBOutlet UISlider *soundSlider;
@property (nonatomic, retain) IBOutlet UILabel *language;
@property (nonatomic, retain) IBOutlet UITableViewCell *volumeCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *muteCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *languageCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *accountManagerCell;
@property (nonatomic, retain) IBOutlet UISwitch *mute;

- (IBAction)muteActivedAction:(id)sender;
- (IBAction)soundModificationAction:(id)sender;

- (void)muteActived;
- (void)goToAccountManagement;

@end
