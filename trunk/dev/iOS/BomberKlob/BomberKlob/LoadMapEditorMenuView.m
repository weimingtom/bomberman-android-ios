//
//  LoadMapEditorMenuView.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 18/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "LoadMapEditorMenuView.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "LoadMapEditorMenuViewController.h"
#import "DBMap.h"


@implementation LoadMapEditorMenuView

@synthesize loadMapEditorMenuViewController, map1, map2, map3;


- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    
    if (self) {
    }
    
    return self;
}


- (void)initWithController:(LoadMapEditorMenuViewController *)myController {
    self.loadMapEditorMenuViewController = myController;
    [self initMap];
}


- (void)dealloc {
    [super dealloc];
}


- (void)initMap {
    selectedMap = 0;
    NSInteger currentMap = selectedMap;
    
    maps = [[NSMutableArray alloc] initWithObjects:map1, map2, map3, nil];
    
    currentMap = [self previousMap:currentMap];
    map1.image = [UIImage imageNamed:[NSString stringWithFormat:@"Maps/%@.png", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:currentMap]).name]];
    
    currentMap = [self nextMap:currentMap];
    map2.image = [UIImage imageNamed:[NSString stringWithFormat:@"Maps/%@.png", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:currentMap]).name]];
    
    
    currentMap = [self nextMap:currentMap];
    map3.image = [UIImage imageNamed:[NSString stringWithFormat:@"Maps/%@.png", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:currentMap]).name]];
}


- (NSInteger)nextMap:(NSInteger)currentMap {
    
    if (currentMap == ([loadMapEditorMenuViewController.mapsNotOfficial count] - 1))
        return 0;
    else 
        return (currentMap + 1);
}


- (NSInteger)previousMap:(NSInteger)currentMap {
    
    if (currentMap == 0)
        return ([loadMapEditorMenuViewController.mapsNotOfficial count] - 1);
    else 
        return (currentMap - 1);
}

@end
