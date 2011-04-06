//
//  AccountManagementMenuViewController.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface AccountManagementMenuViewController : UITableViewController {
    
    UITableViewCell *userNameCell;
    UITableViewCell *autoLoginCell;
    UITableViewCell *rememberPasswordCell;
    UITableViewCell *colorCell;
    UITableViewCell *passwordCell;
    UITableViewCell *pseudoCell;
    UITableViewCell *changeAccountCell;
    UITableViewCell *typeCell;
    
    UILabel *playerColor;
    UILabel *type;
    UILabel *userName;
    UITextField *pseudo;
    UISwitch *rememberPassword;
    UIButton *modifyPassword;
    UISwitch *autoLogin;
}
@property (nonatomic, retain) IBOutlet UITableViewCell *userNameCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *autoLoginCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *rememberPasswordCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *colorCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *passwordCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *pseudoCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *changeAccountCell;
@property (nonatomic, retain) IBOutlet UITableViewCell *typeCell;
@property (nonatomic, retain) IBOutlet UILabel *playerColor;
@property (nonatomic, retain) IBOutlet UILabel *type;
@property (nonatomic, retain) IBOutlet UILabel *userName;
@property (nonatomic, retain) IBOutlet UITextField *pseudo;
@property (nonatomic, retain) IBOutlet UISwitch *rememberPassword;

- (IBAction)changeAccountAction:(id)sender;
- (IBAction)connectionAutoActived:(id)sender;
@property (nonatomic, retain) IBOutlet UIButton *modifyPassword;
- (IBAction)rememberPasswordActived:(id)sender;

- (void)loadData;
- (void)changeColorPlayer;
- (void)changeType;

@end
