//
//  CreateAccountOfflineMenuViewController.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//

#import <UIKit/UIKit.h>


@interface CreateAccountOfflineMenuViewController : UIViewController <UITextFieldDelegate> {
    
    UITextField *pseudo;
    UIView *errorView;
    UIBarButtonItem *done;
}

@property (nonatomic, retain) IBOutlet UITextField *pseudo;
@property (nonatomic, retain) IBOutlet UIView *errorView;
@property (nonatomic, retain) IBOutlet UIBarButtonItem *done;

- (BOOL)vericationPseudo;
- (void)goToMainMenu;

- (IBAction)doneAction:(id)sender;

@end
