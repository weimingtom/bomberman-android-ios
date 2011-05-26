//
//  MultiplayerGameListController.h
//  BomberKlob
//
//  Created by Kilian Coubo on 25/05/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MultiplayerRegisterMenuViewController;
@interface MultiplayerGameListController : UITableViewController {
    NSMutableDictionary * gameList;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil gamesList:(NSDictionary *) gamesList;

@end
