//
//  SelectMapViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 10/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SelectMapViewController.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "DBMap.h"
#import "SinglePlayerMenuView.h"


@implementation SelectMapViewController

@synthesize map1;
@synthesize map2;
@synthesize map3;
@synthesize map4;
@synthesize map5;
@synthesize map6;
@synthesize map7;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        // Custom initialization
    }
    
    return self;
}


- (void)dealloc {
    [map1 release];
    [map2 release];
    [map3 release];
    [map4 release];
    [map5 release];
    [map6 release];
    [map7 release];
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
    
    [((SinglePlayerMenuView *) self.view) initMap];
}


- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark - My methods

- (void)initMap {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    selectedMap = 0;
    NSUInteger nextMap = selectedMap;
    NSUInteger previousMap = selectedMap;
    
    
    previousMap = [self previousMap:previousMap];
    map3.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:previousMap]).name]];
    previousMap = [self previousMap:previousMap];
    
    map2.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:previousMap]).name]];
    previousMap = [self previousMap:previousMap];
    
    map1.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:previousMap]).name]];
    
    map4.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:nextMap]).name]];
    nextMap = [self nextMap:nextMap];
    
    map5.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:nextMap]).name]];
    nextMap = [self nextMap:nextMap];
    
    map6.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:nextMap]).name]];
    nextMap = [self nextMap:nextMap];
    
    map7.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:nextMap]).name]];
    nextMap = [self nextMap:nextMap];
    
    
}


- (NSUInteger)nextMap:(NSUInteger)currentMap {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    
    if (currentMap == ([application.maps count] - 1))
        return 0;
    else 
        return (currentMap + 1);
}


- (NSUInteger)previousMap:(NSUInteger)currentMap {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    
    if (currentMap == 0)
        return ([application.maps count] - 1);
    else 
        return (currentMap - 1);
}

@end
