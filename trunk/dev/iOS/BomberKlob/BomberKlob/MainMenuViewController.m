//
//  MainMenuViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "MainMenuViewController.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "CreateAccountOfflineMenuViewController.h"
#import "ChangePlayerMenuViewController.h"
#import "SinglePlayerMenuViewController.h"
#import "OptionsMenuViewController.h"
#import "DBUser.h"


@implementation MainMenuViewController

@synthesize pseudo;
@synthesize newProfil;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        // Custom initialization
    }
    return self;
}


- (void)dealloc {
    [pseudo release];
    [newProfil release];
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
    
    // TODO: Fixer pourquoi si on release visibleViewController ca plante...
    NSArray *visibleViewController = [[NSArray alloc] initWithObjects:self, nil];
    self.navigationController.viewControllers = visibleViewController;
//    [visibleViewController release];
    
    self.title = NSLocalizedString(@"Main menu", @"Title of 'Main menu' page");
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    
    [pseudo setTitle:application.user.pseudo forState:UIControlStateNormal];
    [pseudo sizeToFit];
    
    newProfil.frame = CGRectMake((pseudo.frame.origin.x + pseudo.frame.size.width + 5), newProfil.frame.origin.y, newProfil.frame.size.width, newProfil.frame.size.height);
}


- (void)viewDidUnload {
    [self setPseudo:nil];
    [self setNewProfil:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark - My methods

- (void)goToCreateAccountOfflineMenu {
    CreateAccountOfflineMenuViewController *createAccountOfflineMenuViewController = [[CreateAccountOfflineMenuViewController alloc] initWithNibName:@"CreateAccountOfflineMenuViewController" bundle:nil];
    [self.navigationController pushViewController:createAccountOfflineMenuViewController animated:YES];
    [createAccountOfflineMenuViewController release];
}


- (void)goToChangePlayerMenu {
    ChangePlayerMenuViewController *changePlayerMenuViewController = [[ChangePlayerMenuViewController alloc] initWithNibName:@"ChangePlayerMenuViewController" bundle:nil];
    [self.navigationController pushViewController:changePlayerMenuViewController animated:YES];
    [changePlayerMenuViewController release];
}


- (void)goToOptionsMenu {
    OptionsMenuViewController *optionsMenuViewController = [[OptionsMenuViewController alloc] initWithNibName:@"OptionsMenuViewController" bundle:nil];
    [self.navigationController pushViewController:optionsMenuViewController animated:YES];
    [optionsMenuViewController release];
}


#pragma mark Action

- (IBAction)singlePlayerAction:(id)sender {
    NSLog(@"Single player");
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
    
    SinglePlayerMenuViewController *singlePlayerMenuViewController = [[SinglePlayerMenuViewController alloc] initWithNibName:@"SinglePlayerMenuViewController" bundle:nil];
    [self.navigationController pushViewController:singlePlayerMenuViewController animated:YES];
    [singlePlayerMenuViewController release];
}


- (IBAction)multiplayerAction:(id)sender {
    NSLog(@"Multiplayer");
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
}


- (IBAction)optionsAction:(id)sender {
    NSLog(@"Options");
    [self goToOptionsMenu];
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
}


- (IBAction)createMapAction:(id)sender {
    NSLog(@"Create map");
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
}


- (IBAction)helpAction:(id)sender {
    NSLog(@"Help");
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
}

- (IBAction)pseudoAction:(id)sender {
    [self goToChangePlayerMenu];
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
}

- (IBAction)newProfilAction:(id)sender {
    [self goToCreateAccountOfflineMenu];
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
}

@end
