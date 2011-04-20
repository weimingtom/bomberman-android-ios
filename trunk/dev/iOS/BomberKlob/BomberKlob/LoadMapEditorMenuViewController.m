//
//  LoadMapEditorMenuViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 18/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "LoadMapEditorMenuViewController.h"
#import "LoadMapEditorMenuView.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "Map.h"
#import "DataBase.h"
#import "DBMap.h"
#import "EditorViewController.h"


@implementation LoadMapEditorMenuViewController
@synthesize load;
@synthesize mapName;
@synthesize owner;
@synthesize mapsNotOfficial;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        // Custom initialization
    }
    
    return self;
}


- (void)dealloc {
    [mapName release];
    [owner release];
    [load release];
    [super dealloc];
}


- (void)initMapNotOfficial {
    NSMutableArray *officialMapsTmp = [[NSMutableArray alloc] init];
    
    DBMap *map;
    sqlite3_stmt *statement = [[DataBase sharedDataBase] select:@"*" from:@"Map" where:@"official = 0"];
    
    while (sqlite3_step(statement) == SQLITE_ROW) {
        map = [[DBMap alloc] initWithName:[NSString stringWithUTF8String:(char *) sqlite3_column_text(statement, 0)] owner:sqlite3_column_int(statement, 1) official:sqlite3_column_int(statement, 1)];
        [officialMapsTmp addObject:map];
        [map release];
    }
    
    sqlite3_finalize(statement);
    
    if ([officialMapsTmp count] > 0) {
        self.mapsNotOfficial = officialMapsTmp;
        [officialMapsTmp release];
    }
}


- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}


#pragma mark - View lifecycle

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = NSLocalizedString(@"Load map", @"Title of 'Load map' page");
    
    self.navigationItem.rightBarButtonItem = load;
    [self initMapNotOfficial];
    // TODO: A modifier le '0'
    [((LoadMapEditorMenuView *) [self.view.subviews objectAtIndex:0]) initWithController:self];
}


- (void)viewDidUnload {
    [self setMapName:nil];
    [self setOwner:nil];
    [self setLoad:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}


- (void)goToEditor {
    EditorViewController *editorViewController = [[EditorViewController alloc] initWithMapName:mapName.text];
    self.navigationController.navigationBarHidden = YES;
    [self.navigationController pushViewController:editorViewController animated:NO];
    [editorViewController release];
}


- (IBAction)loadAction:(id)sender {
    NSLog(@"Load");
    [self goToEditor];
}

@end
