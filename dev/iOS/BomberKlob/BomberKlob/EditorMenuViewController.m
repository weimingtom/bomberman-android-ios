//
//  EditorMenuViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 18/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorMenuViewController.h"
#import "EditorViewController.h"
#import "LoadMapEditorMenuViewController.h"
#import "DataBase.h"


@implementation EditorMenuViewController
@synthesize create;
@synthesize mapName;

// TODO: Finir l'implémentation...
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
    [create release];
    [mapName release];
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
    
    self.title = NSLocalizedString(@"Create map", @"Title of 'Create map' page");
    
    [mapName becomeFirstResponder];
    
    self.navigationItem.rightBarButtonItem = create;
}

- (void)viewDidUnload
{
    [self setCreate:nil];
    [self setMapName:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}


- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [self goToEditor];

    return YES;
}


- (BOOL)vericationMapName {
    return YES;
}


- (BOOL)hasOfficialMap {
    BOOL result = NO;
    sqlite3_stmt *statement = [[DataBase sharedDataBase] select:@"*" from:@"Map" where:@"official = 0"];
    
    if (sqlite3_step(statement) == SQLITE_ROW) {
        result = YES;
    }
    
    sqlite3_finalize(statement);
    
    return result;
}


- (void)goToEditor {
    
    if ([self vericationMapName]) {
        EditorViewController *editorViewController = [[EditorViewController alloc] initWithMapName:mapName.text];
        self.navigationController.navigationBarHidden = YES;
        [self.navigationController pushViewController:editorViewController animated:NO];
        [editorViewController release];
    }
}


- (void)goToLoadMapEditor {
    
    if ([self hasOfficialMap]) {
        LoadMapEditorMenuViewController *loadMapEditorMenuViewController = [[LoadMapEditorMenuViewController alloc] initWithNibName:@"LoadMapEditorMenuViewController" bundle:nil];
        [self.navigationController pushViewController:loadMapEditorMenuViewController animated:YES];
        [loadMapEditorMenuViewController release];
    }
}


- (IBAction)loadMapAction:(id)sender {
    [self goToLoadMapEditor];
}


- (IBAction)createAction:(id)sender {
    [self goToEditor];
}
@end
