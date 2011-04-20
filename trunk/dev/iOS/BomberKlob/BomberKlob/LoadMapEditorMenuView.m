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
#import "DBUser.h"


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
    NSLog(@"%@", loadMapEditorMenuViewController.mapsNotOfficial);
    selectedMap = 0;
    NSInteger currentMap = selectedMap;
    
    maps = [[NSMutableArray alloc] initWithObjects:map1, map2, map3, nil];
    
    currentMap = [self previousMap:currentMap];
    map1.image = [UIImage imageNamed:[NSString stringWithFormat:@"Maps/%@.png", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:currentMap]).name]];
    
    currentMap = [self nextMap:currentMap];
    map2.image = [UIImage imageNamed:[NSString stringWithFormat:@"Maps/%@.png", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:currentMap]).name]];
    
    currentMap = [self nextMap:currentMap];
    map3.image = [UIImage imageNamed:[NSString stringWithFormat:@"Maps/%@.png", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:currentMap]).name]];
    
    loadMapEditorMenuViewController.mapName.text = [NSString stringWithFormat:@"%@", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:selectedMap]).name];
    loadMapEditorMenuViewController.owner.text = [NSString stringWithFormat:@"%@", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:selectedMap]).owner.pseudo];
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


- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {    
    UITouch *touch = [touches anyObject];
    
    CGPoint touchPoint = [touch locationInView:self];
    startLocation = touchPoint.x;
}


- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {	
    UITouch *touch = [touches anyObject];	
    CGPoint touchPoint = [touch locationInView:self];
    UIImageView *middleMap = [maps objectAtIndex:1];
    
    [UIView beginAnimations:@"Move" context:nil];
    [UIView setAnimationDuration:0.3];
    [UIView setAnimationBeginsFromCurrentState:YES];
    
    if([touches count] == 1) {
        [self changeMap:(touchPoint.x - startLocation)];
        [self moveMaps:(160.0 - middleMap.center.x)]; // TODO: Changer les nombres par des constantes
        [self replace];
    }
    
    [UIView commitAnimations];	
}


- (void)moveMaps:(CGFloat)movement {        
    map1.center = CGPointMake(map1.center.x + (movement), map1.center.y);
    map2.center = CGPointMake(map2.center.x + (movement), map2.center.y);
    map3.center = CGPointMake(map3.center.x + (movement), map3.center.y);
}


- (void)changeMap:(CGFloat)movement {
    UIImageView *middleMap = [maps objectAtIndex:1];
    
    if (movement > 0) { // TODO: Changer les nombres par des constantes
        selectedMap = [self nextMap:selectedMap];
        
        [maps replaceObjectAtIndex:1 withObject:[maps objectAtIndex:0]];
        [maps replaceObjectAtIndex:0 withObject:[maps objectAtIndex:2]];
        [maps replaceObjectAtIndex:2 withObject:middleMap];
        
        [self bringSubviewToFront:[maps objectAtIndex:0]];
        [self bringSubviewToFront:[maps objectAtIndex:2]];
        [self bringSubviewToFront:[maps objectAtIndex:1]];
    }
    else if (movement < 0) { // TODO: Changer les nombres par des constantes
        selectedMap = [self previousMap:selectedMap];
        
        [maps replaceObjectAtIndex:1 withObject:[maps objectAtIndex:2]];
        [maps replaceObjectAtIndex:2 withObject:[maps objectAtIndex:0]];
        [maps replaceObjectAtIndex:0 withObject:middleMap];
        
        
        [self bringSubviewToFront:[maps objectAtIndex:2]];
        [self bringSubviewToFront:[maps objectAtIndex:0]];
        [self bringSubviewToFront:[maps objectAtIndex:1]];
    }
    
    loadMapEditorMenuViewController.mapName.text = [NSString stringWithFormat:@"%@", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:selectedMap]).name];
    NSLog(@"%@", loadMapEditorMenuViewController.mapsNotOfficial);
//    loadMapEditorMenuViewController.owner.text = [NSString stringWithFormat:@"%@", ((DBMap *) [loadMapEditorMenuViewController.mapsNotOfficial objectAtIndex:selectedMap]).owner.pseudo];
}


- (void)replace {
    ((UIImageView *) [maps objectAtIndex:0]).frame = CGRectMake(-82, 10, 314, 205);
    ((UIImageView *) [maps objectAtIndex:1]).frame = CGRectMake(52, 0, 375, 225);
    ((UIImageView *) [maps objectAtIndex:2]).frame = CGRectMake(348, 10, 314, 205);
}

@end
