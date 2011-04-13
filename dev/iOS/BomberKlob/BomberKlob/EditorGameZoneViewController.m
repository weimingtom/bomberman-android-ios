//
//  EditorGameZoneViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 11/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorGameZoneViewController.h"
#import "MapEditor.h"
#import "EditorGameZoneView.h"


@implementation EditorGameZoneViewController

- (id)init {
    self = [super init];
    
    if (self) {
        EditorGameZoneView *editorGameZoneView = [[EditorGameZoneView alloc] initWithFrame:CGRectMake(0, 0, 400, 400)];
        self.view = editorGameZoneView;
        [editorGameZoneView release];
        mapEditor = [[MapEditor alloc] init];
        [(EditorGameZoneView *) self.view test];
    }
    
    return self;
}


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        NSLog(@"fregdrsgdgddgdrgdgrt");
//        mapEditor = [[MapEditor alloc] init];
//        [(EditorGameZoneView *)r self.view test];
    }
    
    return self;
}


- (void)dealloc {
    [super dealloc];
}


- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}


#pragma mark - View lifecycle

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}


- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}

@end
