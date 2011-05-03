#import "ItemMenu.h"
#import "Objects.h"


@implementation ItemMenu

@synthesize controller;


- (id)initWithFrame:(CGRect)frame controller:(id<ManageItemMenu>)myController imageWidth:(NSInteger)imageWidthValue imageHeight:(NSInteger)imageHeightValue imageMargin:(NSInteger)imageMarginValue reductionPercentage:(NSInteger)reductionPercentageValue items:(NSArray *)itemsValue {
    self = [super initWithFrame:frame horizontal:NO imageWidth:imageWidthValue imageHeight:imageHeightValue imageMargin:imageMarginValue reductionPercentage:reductionPercentageValue items:itemsValue images:[self initItemsImage:itemsValue]];
    
    if (self) {
        self.controller = myController;
    }
    
    return self;
}


- (NSArray *)initItemsImage:(NSArray *)itemsValue {
    NSMutableArray *imagesTemp = [[NSMutableArray alloc] initWithCapacity:[itemsValue count]];
    
    for (int i = 0; i < [itemsValue count]; i++) {
        [imagesTemp addObject:((Objects *)[itemsValue objectAtIndex:i]).idle];
    }
    
    return imagesTemp;
}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {	
    [super touchesEnded:touches withEvent:event];
    
    [controller selectedItemChange:((Objects *) [self.items objectAtIndex:self.selectedImageIndex]).imageName];
}

@end
