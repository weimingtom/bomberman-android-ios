#import <Foundation/Foundation.h>
#import "Menu.h"
#import "ManageItemMenu.h"


@interface ItemMenu : Menu {
    
    id<ManageItemMenu> controller;
}

@property (nonatomic, retain) id<ManageItemMenu> controller;

- (id)initWithFrame:(CGRect)frame controller:(id<ManageItemMenu>)myController imageWidth:(NSInteger)imageWidthValue imageHeight:(NSInteger)imageHeightValue imageMargin:(NSInteger)imageMarginValue reductionPercentage:(NSInteger)reductionPercentageValue items:(NSArray *)itemsValue;

- (NSArray *)initItemsImage:(NSArray *)itemsValue;

@end
