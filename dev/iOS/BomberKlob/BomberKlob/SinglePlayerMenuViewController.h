#import <UIKit/UIKit.h>
#import "ManageMapMenu.h"


@interface SinglePlayerMenuViewController : UIViewController<ManageMapMenu> {
    
    UIBarButtonItem *play;
    UILabel *mapName;
    NSString *mapNameNew;
}

@property (nonatomic, retain) IBOutlet UIBarButtonItem *play;
@property (nonatomic, retain) IBOutlet UILabel *mapName;
@property (nonatomic, retain) NSString *mapNameNew;

- (IBAction)playAction:(id)sender;

@end
