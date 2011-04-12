//
//  SinglePlayerMenuView.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 10/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SinglePlayerMenuView.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "DBMap.h"

// TODO: Finir l'implementation
@implementation SinglePlayerMenuView

@synthesize map1;
@synthesize map2;
@synthesize map3;
@synthesize map4;
@synthesize map5;
@synthesize map6;
@synthesize map7;
@synthesize mapName;


- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    
    if (self) {
        // Initialization code
    }
    
    return self;
}


/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

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


- (void)initMap {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    selectedMap = 0;
    NSUInteger nextMap = selectedMap;
    NSUInteger previousMap = selectedMap;
    
    maps = [[NSMutableArray alloc] initWithObjects:map1, map2, map3, map4, map5, map6, map7, nil];
    
    
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
    
    mapName.text = [NSString stringWithFormat:@"%@", ((DBMap *) [application.maps objectAtIndex:selectedMap]).name];
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


- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {    
    UITouch *touch = [touches anyObject];
    
    CGPoint touchPoint = [touch locationInView:self];
    startLocation = touchPoint.x;
}


- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {	
    UITouch *touch = [touches anyObject];	
    CGPoint touchPoint = [touch locationInView:self];
    UIImageView *middleMap = [maps objectAtIndex:3];
    
    [UIView beginAnimations:@"Move" context:nil];
    [UIView setAnimationDuration:0.3];
    [UIView setAnimationBeginsFromCurrentState:YES];
    NSLog(@"%f", touchPoint.x - startLocation);
    if([touches count] == 1) {
//        [self moveMaps:(touchPoint.x - startLocation)];
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
    map4.center = CGPointMake(map4.center.x + (movement), map4.center.y); 
    map5.center = CGPointMake(map5.center.x + (movement), map5.center.y);
    map6.center = CGPointMake(map6.center.x + (movement), map6.center.y);
    map7.center = CGPointMake(map7.center.x + (movement), map7.center.y);
}


- (void)changeMap:(CGFloat)movement {
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    UIImageView *middleMap = [maps objectAtIndex:3];
    
    if (movement > 0) { // TODO: Changer les nombres par des constantes
        UIImageView *firstMap = [maps objectAtIndex:0];
        NSInteger nextMap;
        
        [maps replaceObjectAtIndex:3 withObject:[maps objectAtIndex:2]];
        [maps replaceObjectAtIndex:2 withObject:[maps objectAtIndex:1]];
        [maps replaceObjectAtIndex:1 withObject:[maps objectAtIndex:0]];
        [maps replaceObjectAtIndex:0 withObject:[maps objectAtIndex:6]];
        [maps replaceObjectAtIndex:6 withObject:[maps objectAtIndex:5]];
        [maps replaceObjectAtIndex:5 withObject:[maps objectAtIndex:4]];
        [maps replaceObjectAtIndex:4 withObject:middleMap];
        
        firstMap.center = CGPointMake(-35.0, firstMap.center.y);
        
        [self bringSubviewToFront:[maps objectAtIndex:0]];
        [self bringSubviewToFront:[maps objectAtIndex:6]];
        [self bringSubviewToFront:[maps objectAtIndex:1]];
        [self bringSubviewToFront:[maps objectAtIndex:5]];
        [self bringSubviewToFront:[maps objectAtIndex:2]];
        [self bringSubviewToFront:[maps objectAtIndex:4]];
        [self bringSubviewToFront:[maps objectAtIndex:3]];
        
        selectedMap = [self previousMap:selectedMap];
        
        nextMap = selectedMap - 3;        
        if (nextMap < 0)
            nextMap = [application.maps count] + nextMap;
        
        ((UIImageView *) [maps objectAtIndex:0]).image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:nextMap]).name]];
        
        //small - normal - power - my map - aléatoire
    }
    else if (movement < 0) { // TODO: Changer les nombres par des constantes
        UIImageView *lastMap = [maps lastObject];
        NSInteger nextMap;
        
        [maps replaceObjectAtIndex:3 withObject:[maps objectAtIndex:4]];
        [maps replaceObjectAtIndex:4 withObject:[maps objectAtIndex:5]];
        [maps replaceObjectAtIndex:5 withObject:[maps objectAtIndex:6]];
        [maps replaceObjectAtIndex:6 withObject:[maps objectAtIndex:0]];
        [maps replaceObjectAtIndex:0 withObject:[maps objectAtIndex:1]];
        [maps replaceObjectAtIndex:1 withObject:[maps objectAtIndex:2]];
        [maps replaceObjectAtIndex:2 withObject:middleMap];
        
        lastMap.center = CGPointMake(355.0, lastMap.center.y);
        
        [self bringSubviewToFront:[maps objectAtIndex:0]];
        [self bringSubviewToFront:[maps objectAtIndex:6]];
        [self bringSubviewToFront:[maps objectAtIndex:1]];
        [self bringSubviewToFront:[maps objectAtIndex:5]];
        [self bringSubviewToFront:[maps objectAtIndex:2]];
        [self bringSubviewToFront:[maps objectAtIndex:4]];
        [self bringSubviewToFront:[maps objectAtIndex:3]];
        
        selectedMap = [self nextMap:selectedMap];
        
        nextMap = selectedMap + 3;        
        if (nextMap > (((NSInteger) [application.maps count]) - 1))
            nextMap = nextMap - [application.maps count];
        
        ((UIImageView *) [maps lastObject]).image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png", ((DBMap *) [application.maps objectAtIndex:nextMap]).name]];
    }

    mapName.text = [NSString stringWithFormat:@"%@", ((DBMap *) [application.maps objectAtIndex:selectedMap]).name];
}


- (void)replace {
    ((UIImageView *) [maps objectAtIndex:0]).frame = CGRectMake(-77.0, 15.0, 84.0, 70.0);
    ((UIImageView *) [maps objectAtIndex:1]).frame = CGRectMake(-27.0, 10.0, 94.0, 80.0);
    ((UIImageView *) [maps objectAtIndex:2]).frame = CGRectMake(33.0, 5.0, 104.0, 90.0);
    ((UIImageView *) [maps objectAtIndex:3]).frame = CGRectMake(103.0, 0.0, 114.0, 100.0);
    ((UIImageView *) [maps objectAtIndex:4]).frame = CGRectMake(183.0, 5.0, 104.0, 90.0);
    ((UIImageView *) [maps objectAtIndex:5]).frame = CGRectMake(253.0, 10.0, 94.0, 80.0);
    ((UIImageView *) [maps objectAtIndex:6]).frame = CGRectMake(313.0, 15.0, 84.0, 70.0);
}

@end
