//
//  ChangePlayerMenuView.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 05/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "ChangePlayerMenuViewController.h"
#import "BomberKlobAppDelegate.h"
#import "MainMenuViewController.h"
#import "Application.h"
#import "DBUser.h"


@implementation ChangePlayerMenuViewController

@synthesize pseudo;

- (id)initWithStyle:(UITableViewStyle)style {
    self = [super initWithStyle:style];
    
    if (self) {
        // Custom initialization
    }
    return self;
}


- (void)dealloc {
    [pseudo release];
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
    
    self.title = NSLocalizedString(@"Pseudos", @"Title of 'Pseudos' page");

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}


- (void)viewDidUnload {
    [self setPseudo:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
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
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    Application *application = ((BomberKlobAppDelegate *)[UIApplication sharedApplication].delegate).app;
    return [application.pseudos count];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    
    Application *application = ((BomberKlobAppDelegate *)[UIApplication sharedApplication].delegate).app;

    cell.textLabel.text = [application.pseudos objectAtIndex:indexPath.row]; // 3
    
    NSLog(@"%d", application.user.identifier);
    
    if ((indexPath.row + 1) == application.user.identifier) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    }
    // Configure the cell...
//    pseudo.text = [application.pseudos objectAtIndex:indexPath.row];
    
    return cell;
}


/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    Application *application = ((BomberKlobAppDelegate *)[UIApplication sharedApplication].delegate).app;
    
    DBUser *newUser = [[DBUser alloc] initWithId:(indexPath.row + 1)];
    application.user = newUser;
    [newUser release];
    
    [self goToMainMenu];
}


#pragma mark - My methods

- (void)goToMainMenu {
    MainMenuViewController *mainMenuViewController = [[MainMenuViewController alloc] initWithNibName:@"MainMenuViewController" bundle:nil];
    [self.navigationController pushViewController:mainMenuViewController animated:YES];
    [mainMenuViewController release];
}

@end
