//
//  LoadMapEditorMenuViewController.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 18/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface LoadMapEditorMenuViewController : UIViewController {
    
    UILabel *mapName;
    UILabel *owner;
    
    NSArray *mapsNotOfficial;
    NSArray *imageMapsNotOfficial;
    UIBarButtonItem *load;
    
    NSArray *maps;
}

@property (nonatomic, retain) IBOutlet UILabel *mapName;
@property (nonatomic, retain) IBOutlet UILabel *owner;
@property (nonatomic, retain) NSArray *mapsNotOfficial;
@property (nonatomic, retain) NSArray *imageMapsNotOfficial;
@property (nonatomic, retain) IBOutlet UIBarButtonItem *load;

- (void)initMapNotOfficial;

- (IBAction)loadAction:(id)sender;

@end
