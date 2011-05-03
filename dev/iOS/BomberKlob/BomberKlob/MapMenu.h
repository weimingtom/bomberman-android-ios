#import <Foundation/Foundation.h>
#import "Menu.h"
#import "ManageMapMenu.h"

#define NAME_MARGIN 4
#define NAME_HEIGHT 23

#define OWNER_MARGIN 2
#define OWNER_HEIGHT 21


@interface MapMenu : Menu {
    
    id<ManageMapMenu> controller;
    
    BOOL displayOwnerName;
    
    UILabel *mapName;
    UILabel *ownerName;
}

@property (nonatomic, retain) id<ManageMapMenu> controller;

- (id)initWithFrame:(CGRect)frame controller:(id<ManageMapMenu>)myController imageWidth:(NSInteger)imageWidthValue imageHeight:(NSInteger)imageHeightValue imageMargin:(NSInteger)imageMarginValue reductionPercentage:(NSInteger)reductionPercentageValue items:(NSArray *)itemsValue images:(NSArray *)imagesValue displayNameOwner:(BOOL)isDisplayNameOwner;

@end
