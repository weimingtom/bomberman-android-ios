//
//  ChangePlayerMenuView.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 05/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface ChangePlayerMenuViewController : UITableViewController {
    
    UILabel *pseudo;
}

@property (nonatomic, retain) IBOutlet UILabel *pseudo;

- (void)goToMainMenu;

@end
