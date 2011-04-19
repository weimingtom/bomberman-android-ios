//
//  LoadMapEditorMenuView.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 18/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class LoadMapEditorMenuViewController;

@interface LoadMapEditorMenuView : UIView {
    
    LoadMapEditorMenuViewController *loadMapEditorMenuViewController;
    
    UIImageView *map1;
    UIImageView *map2;
    UIImageView *map3;
    
    NSArray *maps;
    NSInteger selectedMap;
}

@property (nonatomic, retain) LoadMapEditorMenuViewController *loadMapEditorMenuViewController;
@property (nonatomic, retain) IBOutlet UIImageView *map1;
@property (nonatomic, retain) IBOutlet UIImageView *map2;
@property (nonatomic, retain) IBOutlet UIImageView *map3;

- (void)initWithController:(LoadMapEditorMenuViewController *)myController;
- (void)initMap;

- (NSInteger)nextMap:(NSInteger)currentMap;
- (NSInteger)previousMap:(NSInteger)currentMap;

@end
