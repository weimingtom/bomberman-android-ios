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
#import "User.h"


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
    
    self.title = NSLocalizedString(@"Main menu", @"Title of 'Main menu' page");
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    
    [pseudo setTitle:application.user.pseudo forState:UIControlStateNormal];
    [pseudo sizeToFit];
    
    
    newProfil.frame = CGRectMake((pseudo.frame.origin.x + pseudo.frame.size.width + 5), newProfil.frame.origin.y, newProfil.frame.size.width, newProfil.frame.size.height);
    NSLog(@"%f", (pseudo.frame.origin.x + pseudo.frame.size.width));
    
    self.navigationItem.hidesBackButton = YES;
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


#pragma mark Action

- (IBAction)singlePlayerAction:(id)sender {
    NSLog(@"Single player");
}


- (IBAction)multiplayerAction:(id)sender {
    NSLog(@"Multiplayer");
}


- (IBAction)optionsAction:(id)sender {
    NSLog(@"Options");
}


- (IBAction)createMapAction:(id)sender {
    NSLog(@"Create map");
}


- (IBAction)helpAction:(id)sender {
    NSLog(@"Help");
}

- (IBAction)pseudoAction:(id)sender {
}

- (IBAction)newProfilAction:(id)sender {
}

@end
