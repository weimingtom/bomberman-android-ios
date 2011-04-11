//
//  SelectMapViewController.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 10/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SelectMapViewController : UIViewController {
    
    UIImageView *map1;
    UIImageView *map2;
    UIImageView *map3;
    UIImageView *map4;
    UIImageView *map5;
    UIImageView *map6;
    UIImageView *map7;
    
    NSMutableSet *maps;
    
    NSUInteger selectedMap;
}

@property (nonatomic, retain) IBOutlet UIImageView *map1;
@property (nonatomic, retain) IBOutlet UIImageView *map2;
@property (nonatomic, retain) IBOutlet UIImageView *map3;
@property (nonatomic, retain) IBOutlet UIImageView *map4;
@property (nonatomic, retain) IBOutlet UIImageView *map5;
@property (nonatomic, retain) IBOutlet UIImageView *map6;
@property (nonatomic, retain) IBOutlet UIImageView *map7;

- (void)initMap;
- (NSUInteger)nextMap:(NSUInteger)currentMap;
- (NSUInteger)previousMap:(NSUInteger)currentMap;

@end
