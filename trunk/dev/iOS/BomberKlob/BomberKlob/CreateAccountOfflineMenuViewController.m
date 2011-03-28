//
//  CreateAccountOfflineMenuViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//

#import "CreateAccountOfflineMenuViewController.h"
#import "BomberKlobAppDelegate.h"
#import "MainMenuViewController.h"
#import "Application.h"
#import "User.h"


@implementation CreateAccountOfflineMenuViewController

@synthesize pseudo;
@synthesize errorView;
@synthesize done;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        // Custom initialization
    }
    return self;
}


- (void)dealloc {
    [errorView release];
    [pseudo release];
    [done release];
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
    
    self.title = NSLocalizedString(@"Create account", @"Title of 'Create account' page");
    
    [pseudo becomeFirstResponder];
    
    self.navigationItem.rightBarButtonItem = done;
}


- (void)viewDidUnload {
    [self setErrorView:nil];
    [self setPseudo:nil];
    [self setDone:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark - My methods

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [self goToMainMenu];
    
    return YES;
}


- (BOOL)vericationPseudo {
    
    if ([pseudo.text isEqual:@""]) {
        UILabel *errorText = (UILabel *)[errorView.subviews objectAtIndex:0]; 
        errorText.text = NSLocalizedString(@"Please enter a pseudo", @"Error message when creating an offline account");
        
        return NO;
    }
    else {
        Application *application = ((BomberKlobAppDelegate *)[UIApplication sharedApplication].delegate).app;
        
        if ([application pseudoAlreadyExists:pseudo.text]) {
            UILabel *errorText = (UILabel *)[errorView.subviews objectAtIndex:0]; 
            errorText.text = NSLocalizedString(@"Pseudo already used", @"Error message when creating an offline account");
            
            return NO;
        }
        
        return YES;
    }
}


- (void)goToMainMenu {
    
    if ([self vericationPseudo]) {
        errorView.hidden = YES;
        
        MainMenuViewController *mainMenuViewController = [[MainMenuViewController alloc] initWithNibName:@"MainMenuViewController" bundle:nil];
        [self.navigationController pushViewController:mainMenuViewController animated:YES];
        [mainMenuViewController release];
    }
    else {
        errorView.hidden = NO;
    }
}

#pragma mark Actions

- (IBAction)doneAction:(id)sender {
    [self goToMainMenu];
}
@end
