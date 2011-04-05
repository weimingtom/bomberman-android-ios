//
//  BomberKlobAppDelegate.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//

#import <UIKit/UIKit.h>

@class Application;


@interface BomberKlobAppDelegate : NSObject <UIApplicationDelegate> {
    
    Application *app;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet UINavigationController *navigationController;
@property (nonatomic, retain) Application *app;

@end
