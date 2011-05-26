//
//  MultiPlayerMenuViewController.h
//  BomberKlob
//
//  Created by Kilian Coubo on 18/05/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface MultiplayerRegisterMenuViewController : UIViewController {
	
	UIBarButtonItem *done;
	UITextField *userName;
	UITextField *password;
	UITextField *secondPassword;
	UISwitch *autoLogin;
	UISwitch *rememberPassword;
	
	NSString * idCookie;
}

@property (nonatomic, retain) IBOutlet UIBarButtonItem *done;
@property (nonatomic, retain) IBOutlet UITextField *userName;
@property (nonatomic, retain) IBOutlet UITextField *password;
@property (nonatomic, retain) IBOutlet UITextField *secondPassword;
@property (nonatomic, retain) IBOutlet UISwitch *autoLogin;
@property (nonatomic, retain) IBOutlet UISwitch *rememberPassword;
@property (nonatomic, retain) NSString * idCookie;


- (IBAction)doneAction:(id)sender;

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)theData;

+ (NSString *)md5:(NSString *)str;

@end
