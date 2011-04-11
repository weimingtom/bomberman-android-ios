//
//  SinglePlayerMenuView.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 10/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SinglePlayerMenuView : UIView {
    
    UIImageView *map1;
    UIImageView *map2;
    UIImageView *map3;
    UIImageView *map4;
    UIImageView *map5;
    UIImageView *map6;
    UIImageView *map7;
    UILabel *mapName;
    
    NSMutableArray *maps;
    NSInteger selectedMap;
    CGFloat startLocation;
}

@property (nonatomic, retain) IBOutlet UIImageView *map1;
@property (nonatomic, retain) IBOutlet UIImageView *map2;
@property (nonatomic, retain) IBOutlet UIImageView *map3;
@property (nonatomic, retain) IBOutlet UIImageView *map4;
@property (nonatomic, retain) IBOutlet UIImageView *map5;
@property (nonatomic, retain) IBOutlet UIImageView *map6;
@property (nonatomic, retain) IBOutlet UIImageView *map7;
@property (nonatomic, retain) IBOutlet UILabel *mapName;


- (void)initMap;
- (NSUInteger)nextMap:(NSUInteger)currentMap;
- (NSUInteger)previousMap:(NSUInteger)currentMap;
- (void)moveMaps:(CGFloat)movement;
- (void)changeMap:(CGFloat)movement;
- (void)replace;

@end
