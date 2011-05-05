#import "MapMenu.h"
#import "MenuRect.h"
#import "DBMap.h"
#import "DBUser.h"


@implementation MapMenu

@synthesize controller;


- (id)initWithFrame:(CGRect)frame controller:(id<ManageMapMenu>)myController imageWidth:(NSInteger)imageWidthValue imageHeight:(NSInteger)imageHeightValue imageMargin:(NSInteger)imageMarginValue reductionPercentage:(NSInteger)reductionPercentageValue items:(NSArray *)itemsValue images:(NSArray *)imagesValue displayNameOwner:(BOOL)isDisplayNameOwner {
    self = [super initButNotBuild:frame horizontal:YES imageWidth:imageWidthValue imageHeight:imageHeightValue imageMargin:imageMarginValue reductionPercentage:reductionPercentageValue items:itemsValue images:imagesValue];
    
    if (self) {
        self.controller = myController;
        displayOwnerName = isDisplayNameOwner;
        
        [self buildUserInterface];
    }
    
    return self;
}


- (void)dealloc {
    [mapName release];
    [ownerName release];
    [super dealloc];
}


- (void)buildUserInterface {
    [super buildUserInterface];

    mapName = [[UILabel alloc] initWithFrame:CGRectMake(0, (self.imageHeight + NAME_MARGIN), self.frame.size.width, NAME_HEIGHT)];
    mapName.text = ((DBMap *)[items objectAtIndex:selectedImageIndex]).name;
    mapName.textAlignment = UITextAlignmentCenter;
    mapName.font = [UIFont fontWithName:@"Helvetica-Bold" size:18];   
    [self addSubview:mapName];
    [controller changeMap:mapName.text];
    
    if (displayOwnerName) {
        ownerName = [[UILabel alloc] initWithFrame:CGRectMake(0, (self.imageHeight + (NAME_MARGIN + NAME_HEIGHT) + OWNER_MARGIN), self.frame.size.width, OWNER_HEIGHT)];
        ownerName.text = ((DBMap *)[items objectAtIndex:selectedImageIndex]).owner.pseudo;
        ownerName.textAlignment = UITextAlignmentCenter;
        ownerName.font = [UIFont fontWithName:@"Helvetica" size:17];
        [self addSubview:ownerName];
    }
}


- (void)computeImagesSquare {
    
    if ([images count] > 0) {
        MenuRect *rect;
        
        NSInteger currentImageWidth = imageWidth;
        NSInteger currentImageHeight = imageHeight;
        NSInteger currentImageMargin = imageMargin;
        
        CGPoint currentImagePositionLeft = CGPointMake(((self.frame.size.width / 2) - (imageWidth / 2)), 0);
        CGPoint currentImagePositionRight = CGPointMake(((self.frame.size.width / 2) - (imageWidth / 2)), 0);
        
        NSInteger oldImageWidth = currentImageWidth;
        NSInteger oldImageHeight = currentImageHeight;
        NSInteger oldImageMargin = currentImageMargin;
        
        // builds the middle image
        rect = [[MenuRect alloc] initWithPoint:currentImagePositionLeft width:currentImageWidth height:currentImageHeight];
        [imagesPosition addObject:rect];
        [rect release];
        
        // builds the left and right images
        for (int i = ((nbImageDisplay - 1) / 2); i < (nbImageDisplay - 1); i++) { // the number of images left/top
            currentImageWidth = ((currentImageWidth * (100 - reductionPercentage)) / 100);
            currentImageHeight = ((currentImageHeight * (100 - reductionPercentage)) / 100);
            currentImageMargin = ((currentImageMargin * (100 - reductionPercentage)) / 100);
            
            if (horizontal) {
                currentImagePositionLeft = CGPointMake((currentImagePositionLeft.x - (currentImageWidth + oldImageMargin)), (currentImagePositionLeft.y + ((oldImageHeight - currentImageHeight) / 2)));
                currentImagePositionRight = CGPointMake((currentImagePositionRight.x + (oldImageWidth + oldImageMargin)), (currentImagePositionRight.y + ((oldImageHeight - currentImageHeight) / 2)));
            }
            else {                
                currentImagePositionLeft = CGPointMake((currentImagePositionLeft.x + ((oldImageWidth - currentImageWidth) / 2)), (currentImagePositionLeft.y - (currentImageHeight + oldImageMargin)));
                currentImagePositionRight = CGPointMake((currentImagePositionRight.x + ((oldImageWidth - currentImageWidth) / 2)), (currentImagePositionRight.y + (oldImageHeight + oldImageMargin)));
            }
            
            oldImageWidth = currentImageWidth;
            oldImageHeight = currentImageHeight;
            oldImageMargin = currentImageMargin;
            
            rect = [[MenuRect alloc] initWithPoint:currentImagePositionLeft width:currentImageWidth height:currentImageHeight];
            [imagesPosition addObject:rect];
            [rect release];
            
            rect = [[MenuRect alloc] initWithPoint:currentImagePositionRight width:currentImageWidth height:currentImageHeight];
            [imagesPosition addObject:rect];
            [rect release];
        }
        
        [self sortImagesPosition];
    }
}


- (void)moveImages {        
    [super moveImages];
    
    mapName.text = ((DBMap *)[items objectAtIndex:selectedImageIndex]).name;
    [controller changeMap:mapName.text];
    
    if (displayOwnerName) {
        ownerName.text = ((DBMap *)[items objectAtIndex:selectedImageIndex]).owner.pseudo;
    }
}

@end
