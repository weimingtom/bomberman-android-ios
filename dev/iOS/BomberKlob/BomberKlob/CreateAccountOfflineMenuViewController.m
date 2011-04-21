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
#import "DBUser.h"
#import "DBSystem.h"


@implementation CreateAccountOfflineMenuViewController

@synthesize pseudo, errorView, done;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {

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
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}



#pragma mark - My methods

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [self goToMainMenu];
    
    return YES;
}


- (BOOL)vericationPseudo {
    
    // if the player doesn't provide his pseudo
    if ([pseudo.text isEqual:@""]) {
        UILabel *errorText = (UILabel *)[errorView.subviews objectAtIndex:0]; 
        errorText.text = NSLocalizedString(@"Please enter a pseudo.", @"Error message when creating an offline account");
        
        return NO;
    }
    // else if he provide his pseudo
    else {
        Application *application = ((BomberKlobAppDelegate *)[UIApplication sharedApplication].delegate).app;
        
        // if the player's name is already used
        if ([application pseudoAlreadyUsed:pseudo.text]) {
            UILabel *errorText = (UILabel *)[errorView.subviews objectAtIndex:0]; 
            errorText.text = [NSString stringWithFormat: NSLocalizedString(@"'%@' is already used.", @"Error message when creating an offline account" ), pseudo.text];
            
            return NO;
        }
        // else the player has provide a good pseudo
        else {
            NSMutableArray *pseudos;
            DBUser *newUser = [[DBUser alloc] initWithPseudo:pseudo.text];
            [newUser saveInDataBase];
            
            pseudos = [[NSMutableArray alloc] initWithArray:application.pseudos];
            [pseudos addObject:newUser.pseudo];
            
            application.pseudos = pseudos;
            application.user = newUser;
            
            [newUser release];
            [pseudos release];
            
            return YES;
         }
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
