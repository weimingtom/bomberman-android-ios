//
//  EditorMenuViewController.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 18/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface EditorMenuViewController : UIViewController {
    
    UIBarButtonItem *create;
    UITextField *mapName;
}

@property (nonatomic, retain) IBOutlet UIBarButtonItem *create;
@property (nonatomic, retain) IBOutlet UITextField *mapName;

- (BOOL)vericationMapName;

- (BOOL)hasOfficialMap;
- (void)goToEditor;
- (void)goToLoadMapEditor;

- (IBAction)loadMapAction:(id)sender;
- (IBAction)createAction:(id)sender;

@end
