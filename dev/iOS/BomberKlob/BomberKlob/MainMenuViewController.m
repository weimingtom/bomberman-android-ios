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
#import "DBUser.h"
#import "GameViewControllerSingle.h"
#import "OptionsMenuViewController.h"
#import "SinglePlayerMenuViewController.h"
#import "EditorViewController.h"
#import "EditorMenuViewController.h"


@implementation MainMenuViewController

@synthesize pseudo, newProfil;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        
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
    
    NSArray *visibleViewController = [[NSArray alloc] initWithObjects:self, nil];
    self.navigationController.viewControllers = visibleViewController;
    [visibleViewController release];
    
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
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}


#pragma mark - My methods

- (void)goToSinglePlayerMenu {
    SinglePlayerMenuViewController *singlePlayerMenuViewController = [[SinglePlayerMenuViewController alloc] initWithNibName:@"SinglePlayerMenuViewController" bundle:nil];
    [self.navigationController pushViewController:singlePlayerMenuViewController animated:YES];
    [singlePlayerMenuViewController release];
}


- (void)goToMultiPlayerMenu {
    NSLog(@"Multiplayer");
}


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


- (void)goToEditorMenu {
    EditorMenuViewController *editorMenuViewController = [[EditorMenuViewController alloc] initWithNibName:@"EditorMenuViewController" bundle:nil];
    [self.navigationController pushViewController:editorMenuViewController animated:YES];
    [editorMenuViewController release];
}


- (void)goToHelpMenu {
    NSLog(@"Help");
}


#pragma mark Action

- (IBAction)singlePlayerAction:(id)sender {    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
    
    [self goToSinglePlayerMenu];
}


- (IBAction)multiplayerAction:(id)sender {    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
    
    [self goToMultiPlayerMenu];
}


- (IBAction)optionsAction:(id)sender {    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
    
    [self goToOptionsMenu];
}


- (IBAction)createMapAction:(id)sender {    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
    
    [self goToEditorMenu];
}


- (IBAction)helpAction:(id)sender {    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
    
    [self goToHelpMenu];
}

- (IBAction)pseudoAction:(id)sender {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
    
    [self goToChangePlayerMenu];
}

- (IBAction)newProfilAction:(id)sender {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application playSoundButton];
    
    [self goToCreateAccountOfflineMenu];
}

@end
