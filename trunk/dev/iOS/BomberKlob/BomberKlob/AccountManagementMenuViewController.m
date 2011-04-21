//
//  AccountManagementMenuViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AccountManagementMenuViewController.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "DBUser.h"


@implementation AccountManagementMenuViewController

@synthesize modifyPassword, userNameCell, autoLoginCell, rememberPasswordCell, colorCell, passwordCell, pseudoCell, changeAccountCell, typeCell, playerColor, type, userName, pseudo,  rememberPassword;


- (id)initWithStyle:(UITableViewStyle)style {
    self = [super initWithStyle:style];
    
    if (self) {

    }
    
    return self;
}


- (void)dealloc {
    [userNameCell release];
    [autoLoginCell release];
    [rememberPasswordCell release];
    [colorCell release];
    [passwordCell release];
    [pseudoCell release];
    [changeAccountCell release];
    [typeCell release];
    [playerColor release];
    [type release];
    [userName release];
    [modifyPassword release];
    [pseudo release];
    [autoLogin release];
    [rememberPassword release];
    [super dealloc];
}


- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}


#pragma mark - View lifecycle

- (void)viewDidLoad {
    [super viewDidLoad];

    self.title = NSLocalizedString(@"Account management", @"Title of 'Account management' page");
    
    [self loadData];
}


- (void)viewDidUnload {
    [self setUserNameCell:nil];
    [self setAutoLoginCell:nil];
    [self setRememberPasswordCell:nil];
    [self setColorCell:nil];
    [self setPasswordCell:nil];
    [self setPseudoCell:nil];
    [self setChangeAccountCell:nil];
    [self setTypeCell:nil];
    [self setPlayerColor:nil];
    [self setType:nil];
    [self setUserName:nil];
    [self setModifyPassword:nil];
    [self setPseudo:nil];
    [autoLogin release];
    autoLogin = nil;
    [self setRememberPassword:nil];
    [super viewDidUnload];
}


- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
}


- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
}


- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
}


- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 3;
}


- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
	
	NSString *sectionHeader = nil;
	
	switch (section) {
		case 0:
			sectionHeader = NSLocalizedString(@"Solo", @"Title of section 'Solo' in the account management menu");
			break;
		case 1:
			sectionHeader = NSLocalizedString(@"Multiplayer", @"Title of section 'Multiplayer' in the account management menu");
			break;
	}
	
	return sectionHeader;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    switch (section) {
		case 0:
			return 2;
			break;
		case 1:
			return 5;
			break;
		case 2:
			return 1;
			break;
			
		default:
			return 0;
			break;
	}
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    switch (indexPath.section) {
		case 0:
			if (indexPath.row == 0) {
				return pseudoCell;
			}
			else if (indexPath.row == 1) {
				playerColor.text = @"Red";
				playerColor.textColor = [UIColor redColor];
				
				return colorCell;
			}
			break;
		case 1:
			if (indexPath.row == 0) {
				return userNameCell;
			}
			else if (indexPath.row == 1) {
				return passwordCell;
			}
			else if (indexPath.row == 2) {
				return autoLoginCell;
			}
			else if (indexPath.row == 3) {
				return rememberPasswordCell;
			}
			if (indexPath.row == 4) {
				return changeAccountCell;
			}
			break;
		case 2:
			return typeCell;
			break;
            
		default:
			return nil;
			break;
	}
	
	return nil;
}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (indexPath.section == 0 && indexPath.row == 1) {
		[self changeColorPlayer];
	}
}


#pragma mark - My methods

- (void)loadData {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    
    pseudo.text = application.user.pseudo;
    userName.text = application.user.userName;
    
    if (application.user.userName == nil) {
        modifyPassword.enabled = NO;
        autoLogin.on = application.user.connectionAuto;
        autoLogin.enabled = NO;
        rememberPassword.on = application.user.rememberPassword;
        rememberPassword.enabled = NO;
    }
    
    // TODO: Gérer la couleur et la fonction droitier ou gaucher
}

- (void)changeColorPlayer {
    
}


- (void)changeType {
    
}


#pragma mark - Action

- (IBAction)changeAccountAction:(id)sender {
    
}


- (IBAction)connectionAutoActived:(id)sender {
    
}


- (IBAction)rememberPasswordActived:(id)sender {
    
}
@end
