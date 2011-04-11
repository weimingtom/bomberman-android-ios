//
//  SinglePlayerMenuViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 10/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SinglePlayerMenuViewController.h"
#import "GameViewControllerSingle.h"


@implementation SinglePlayerMenuViewController
@synthesize play;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}


- (void)dealloc {
    [play release];
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
    
    self.title = NSLocalizedString(@"Single player", @"Title of 'Single player' page");
    
    self.navigationItem.rightBarButtonItem = play;
}


- (void)viewDidUnload {
    [self setPlay:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)playAction:(id)sender {
	GameViewControllerSingle * gameViewController = [[GameViewControllerSingle alloc] init];
	[self.navigationController pushViewController:gameViewController animated:YES];
	[gameViewController release];
}
@end
