//
//  MultiplayerConnectionMenuViewController.h
//  BomberKlob
//
//  Created by Kilian Coubo on 19/05/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MultiplayerRegisterMenuViewController;
@interface MultiplayerConnectionMenuViewController : UIViewController {
    
	UIButton *registerButton;
	UILabel *offlineLogin;
	UITextField *username;
	UITextField *password;
	UISwitch *autoLogin;
	UISwitch *rememberPassword;
	UINavigationItem *returnButton;
	UIBarButtonItem *done;
	
	NSString * idCookie;
	MultiplayerRegisterMenuViewController * registerController;

}
@property (nonatomic, retain) IBOutlet UIButton *registerButton;

@property (nonatomic, retain) IBOutlet UILabel *offlineLogin;
@property (nonatomic, retain) IBOutlet UITextField *username;
@property (nonatomic, retain) IBOutlet UITextField *password;
@property (nonatomic, retain) IBOutlet UISwitch *autoLogin;
@property (nonatomic, retain) IBOutlet UISwitch *rememberPassword;
@property (nonatomic, retain) IBOutlet UINavigationItem *returnButton;
@property (nonatomic, retain) IBOutlet UIBarButtonItem *done;

- (IBAction)registerAction:(id)sender;
- (IBAction)doneAction:(id)sender;

@end
