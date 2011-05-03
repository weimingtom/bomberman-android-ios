#import <UIKit/UIKit.h>
#import "ManageMapMenu.h"


@interface LoadMapEditorMenuViewController : UIViewController<ManageMapMenu> {
    
    UILabel *mapName;
    UILabel *owner;
    
    NSString *mapNameNew;
    
    NSArray *mapsNotOfficial;
    NSArray *imageMapsNotOfficial;
    UIBarButtonItem *load;
    
    NSArray *maps;
}

@property (nonatomic, retain) IBOutlet UILabel *mapName;
@property (nonatomic, retain) IBOutlet UILabel *owner;
@property (nonatomic, retain) NSString *mapNameNew;
@property (nonatomic, retain) NSArray *mapsNotOfficial;
@property (nonatomic, retain) NSArray *imageMapsNotOfficial;
@property (nonatomic, retain) IBOutlet UIBarButtonItem *load;

- (void)initMapNotOfficial;

- (IBAction)loadAction:(id)sender;

@end
