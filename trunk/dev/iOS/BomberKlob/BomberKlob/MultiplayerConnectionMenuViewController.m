//
//  MultiplayerConnectionMenuViewController.m
//  BomberKlob
//
//  Created by Kilian Coubo on 19/05/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "MultiplayerConnectionMenuViewController.h"
#import "MultiplayerRegisterMenuViewController.h"


@implementation MultiplayerConnectionMenuViewController
@synthesize registerButton;
@synthesize offlineLogin;
@synthesize username;
@synthesize password;
@synthesize autoLogin;
@synthesize rememberPassword;
@synthesize returnButton;
@synthesize done;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)dealloc
{
    [registerButton release];
    [offlineLogin release];
    [username release];
    [password release];
    [autoLogin release];
    [rememberPassword release];
    [returnButton release];
    [done release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = NSLocalizedString(@"Connection", @"Title of 'Connection' page");
	self.navigationItem.rightBarButtonItem = done;}

- (void)viewDidUnload
{
    [self setRegisterButton:nil];
    [self setOfflineLogin:nil];
    [self setUsername:nil];
    [self setPassword:nil];
    [self setAutoLogin:nil];
    [self setRememberPassword:nil];
    [self setReturnButton:nil];
    [self setDone:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}

- (IBAction)registerAction:(id)sender {
	MultiplayerRegisterMenuViewController * multiplayerRegisterMenuViewController = [[MultiplayerRegisterMenuViewController alloc] initWithNibName:@"MultiplayerMenuViewController" bundle:nil];
    [self.navigationController pushViewController:multiplayerRegisterMenuViewController animated:YES];
    [multiplayerRegisterMenuViewController release];
}

- (IBAction)doneAction:(id)sender {
}
@end
