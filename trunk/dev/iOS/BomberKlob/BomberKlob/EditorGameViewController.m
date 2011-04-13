//
//  EditorGameViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 11/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorGameViewController.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "EditorGameZoneViewController.h"


@implementation EditorGameViewController


- (id)init {
    self = [super init];
    
    if (self) {
        editorGameZoneViewController = [[EditorGameZoneViewController alloc] init];        
    }
    
    return self;
}


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        // Custom initialization
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

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [self.navigationController setNavigationBarHidden:YES];
}


- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}


@end
