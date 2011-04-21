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
#import "Application.h"
#import "BomberKlobAppDelegate.h"
#import "DBMap.h"


@implementation EditorMenuViewController
@synthesize errorView;

@synthesize create, mapName;

// TODO: Finir l'implémentation...
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {

    }
    
    return self;
}


- (void)dealloc {
    [create release];
    [mapName release];
    [errorView release];
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
    
    self.title = NSLocalizedString(@"Create map", @"Title of 'Create map' page");
    
    [mapName becomeFirstResponder];
    
    self.navigationItem.rightBarButtonItem = create;
}


- (void)viewDidUnload {
    [self setCreate:nil];
    [self setMapName:nil];
    [self setErrorView:nil];
    [super viewDidUnload];
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}


- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [self goToEditor];

    return YES;
}


- (BOOL)checkMapName {
    
    if ([mapName.text isEqual:@""]) {
        UILabel *errorText = (UILabel *)[errorView.subviews objectAtIndex:0]; 
        errorText.text = NSLocalizedString(@"Please enter a map name.", @"Error message when creating a map");
        errorView.hidden = NO;
        
        return NO;
    }
    else if ([self mapNameIsAlreadyExist]) {
        UILabel *errorText = (UILabel *)[errorView.subviews objectAtIndex:0]; 
        errorText.text = [NSString stringWithFormat: NSLocalizedString(@"'%@' is already used.", @"Error message when creating a map" ), mapName.text];
        errorView.hidden = NO;
        
        return NO;
    }
    
    errorView.hidden = YES;
    return YES;
}


- (BOOL)mapNameIsAlreadyExist {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    DBMap *map;
    NSArray *maps = application.maps;
    
    for (int i = 0; i < [maps count]; i++) {
        map = [maps objectAtIndex:i];

        if ([map.name isEqual:mapName.text]) {
            return YES;
        }
    }
    
    return NO;
}


- (void)goToEditor {

    if ([self checkMapName]) {
        EditorViewController *editorViewController = [[EditorViewController alloc] initWithMapName:mapName.text];
        self.navigationController.navigationBarHidden = YES;
        [self.navigationController pushViewController:editorViewController animated:NO];
        [editorViewController release];
    }
}


- (void)goToLoadMapEditor {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    
    if ([application hasUnofficialMaps]) {
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
