#import "Menu.h"
#import "MenuRect.h"


@implementation Menu

@synthesize imageWidth, imageHeight, imageMargin, items, images, imagesPosition;


- (id)initWithFrame:(CGRect)frame horizontal:(BOOL)isHorizontal imageWidth:(NSInteger)imageWidthValue imageHeight:(NSInteger)imageHeightValue imageMargin:(NSInteger)imageMarginValue reductionPercentage:(NSInteger)reductionPercentageValue items:(NSArray *)itemsValue images:(NSArray *)imagesValue {
    self = [super initWithFrame:frame];
    
    if (self) {        
        horizontal = isHorizontal;
        self.imageWidth = imageWidthValue;
        self.imageHeight = imageHeightValue;
        self.imageMargin = imageMarginValue;
        reductionPercentage = reductionPercentageValue;
        
        self.items = itemsValue;
        self.images = imagesValue;
        
        selectedImageIndex = 0;
        [self computeNumberImageDisplay];
        imagesDisplayed = [[NSMutableArray alloc] initWithCapacity:nbImageDisplay];
        imagesPosition = [[NSMutableArray alloc] initWithCapacity:nbImageDisplay];
        [self computeImagesSquare];
        
        NSString *errorDescription = [[NSString alloc] initWithFormat:@"The number of objects elements (%d) is different to the number of images elements (%d).", [items count], [images count]];
        NSAssert([self.items count] == [self.images count], errorDescription);
        [self buildUserInterface];
        
        [errorDescription release];
    }
    
    return self;
}


- (id)initButNotBuild:(CGRect)frame horizontal:(BOOL)isHorizontal imageWidth:(NSInteger)imageWidthValue imageHeight:(NSInteger)imageHeightValue imageMargin:(NSInteger)imageMarginValue reductionPercentage:(NSInteger)reductionPercentageValue items:(NSArray *)itemsValue images:(NSArray *)imagesValue {
    self = [super initWithFrame:frame];
    
    if (self) {        
        horizontal = isHorizontal;
        self.imageWidth = imageWidthValue;
        self.imageHeight = imageHeightValue;
        self.imageMargin = imageMarginValue;
        reductionPercentage = reductionPercentageValue;
        
        self.items = itemsValue;
        self.images = imagesValue;
        
        selectedImageIndex = 0;
        [self computeNumberImageDisplay];
        imagesDisplayed = [[NSMutableArray alloc] initWithCapacity:nbImageDisplay];
        imagesPosition = [[NSMutableArray alloc] initWithCapacity:nbImageDisplay];
        [self computeImagesSquare];
        
        NSString *errorDescription = [[NSString alloc] initWithFormat:@"The number of objects elements (%d) is different to the number of images elements (%d).", [items count], [images count]];
        NSAssert([self.items count] == [self.images count], errorDescription);
        
        [errorDescription release];
    }
    
    return self;
}


- (void)dealloc {
    [items release];
    [images release];
    [imagesDisplayed release];
    [imagesPosition release];
    [super dealloc];
}


- (void)buildUserInterface {
    NSInteger currentImageIndex = selectedImageIndex;
    UIImageView *currentImage;

    for (int i = 0; i < (([imagesPosition count] - 1) / 2); i++) {
        currentImageIndex = [self previousImage:currentImageIndex];
    }
    
    for (int i = 0; i < [imagesPosition count]; i++) {
        currentImage = [[UIImageView alloc] initWithImage:[images objectAtIndex:currentImageIndex]];
        currentImage.frame = [[imagesPosition objectAtIndex:i] getCGRect];
        [imagesDisplayed addObject:currentImage];
        [currentImage release];
        
        [self addSubview:[imagesDisplayed objectAtIndex:i]];
        currentImageIndex = [self nextImage:currentImageIndex];
    }

    [self replaceImages:0];
}


- (void)sortImagesPosition {
    NSInteger index = (([imagesPosition count] - 1) - 1);
    NSMutableArray *newArray = [[NSMutableArray alloc] initWithCapacity:[imagesDisplayed count]];
    
    while (index > 0) {
        [newArray addObject:[imagesPosition objectAtIndex:index]];
        index -= 2;
    }
    
    [newArray addObject:[imagesPosition objectAtIndex:0]];    
    index = 2;
    
    while (index < ([imagesPosition count] - 1)) {
        [newArray addObject:[imagesPosition objectAtIndex:index]];
        index += 2;
    }
    
    [newArray addObject:[imagesPosition lastObject]];
    
    self.imagesPosition = newArray;
    [newArray release];
}


- (void)computeNumberImageDisplay {
    NSInteger margin = imageMargin;
    
    nbImageDisplay = 0;
    
    if (horizontal) { 
        NSInteger leftSideCenterImage = (self.frame.size.width / 2) + (imageWidth / 2);
        NSInteger width = imageWidth;
        NSInteger currentX = (leftSideCenterImage - (width + margin));
        
        while (currentX > 0) {
            nbImageDisplay++;
            
            width = ((width * (100 - reductionPercentage)) / 100);
            margin = ((margin * (100 - reductionPercentage)) / 100);
            currentX -= (width + margin);
        }
    }
    else {
        NSInteger topSideCenterImage = (self.frame.size.height / 2) + (imageHeight / 2);
        NSInteger height = imageHeight;
        NSInteger currentY = (topSideCenterImage - (height + margin));
        
        while (currentY > 0) {
            nbImageDisplay++;
            
            height = ((height * (100 - reductionPercentage)) / 100);
            margin = ((margin * (100 - reductionPercentage)) / 100);
            currentY -= (height + margin);
        }
    }
    
    nbImageDisplay *= 2; // there are the same number of images on each side
    nbImageDisplay++; // it must count the middle image
    nbImageDisplay += 2; // for the two images that are invisibles
}


- (void)computeImagesSquare {
    
    if ([images count] > 0) {
        MenuRect *rect;
        
        NSInteger currentImageWidth = imageWidth;
        NSInteger currentImageHeight = imageHeight;
        NSInteger currentImageMargin = imageMargin;
        
        CGPoint currentImagePositionLeft = CGPointMake(((self.frame.size.width / 2) - (imageWidth / 2)), ((self.frame.size.height / 2) - (imageHeight / 2)));
        CGPoint currentImagePositionRight = CGPointMake(((self.frame.size.width / 2) - (imageWidth / 2)), ((self.frame.size.height / 2) - (imageHeight / 2)));
        
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


- (NSInteger)previousImage:(NSInteger)currentImage {
    currentImage--;
    
    return (currentImage < 0)? ([images count] - 1) : currentImage;
}


- (NSInteger)nextImage:(NSInteger)currentImage {
    currentImage++;
    
    return (currentImage > ([images count] - 1))? 0 : currentImage;
}


- (NSInteger)previousImageDisplay:(NSInteger)currentImage {
    currentImage--;
    
    return (currentImage < 0)? ([imagesDisplayed count] - 1) : currentImage;
}


- (NSInteger)nextImageDisplay:(NSInteger)currentImage {
    currentImage++;
    
    return (currentImage > ([imagesDisplayed count] - 1))? 0 : currentImage;
}


- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {    
    UITouch *touch = [touches anyObject];
    
    CGPoint touchPoint = [touch locationInView:self];
    startLocation = touchPoint;
}


- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {	
    UITouch *touch = [touches anyObject];	
    CGPoint touchPoint = [touch locationInView:self];
    
    if([touches count] == 1) {
        NSInteger movement;
        
        if (horizontal) {
            movement = (touchPoint.x - startLocation.x);
        }
        else {
            movement = (touchPoint.y - startLocation.y);
        }
        
        [self changeImage:movement];
        
        [UIView beginAnimations:@"Move" context:nil];
        [UIView setAnimationDuration:0.3];
        [UIView setAnimationBeginsFromCurrentState:YES];
        
        [self moveImages];
        [self replaceImages:movement];
        
        [UIView commitAnimations];	
    }
}


- (void)moveImages {        
    UIImageView *currentImage;
    
    for (int i = 0; i < [imagesPosition count]; i++) {
        currentImage = [imagesDisplayed objectAtIndex:i];
        currentImage.frame = [[imagesPosition objectAtIndex:i] getCGRect];
    }
}


- (void)changeImage:(NSInteger)movement {
    if (movement < 0) {
        [self moveToNextImage];
    }
    else {
        [self moveToPreviousImage];
    }
}


- (void)moveToNextImage {
    selectedImageIndex = [self nextImage:selectedImageIndex];
    NSInteger index = selectedImageIndex;
    
    for (int i = 0; i < (([imagesDisplayed count] - 1) / 2); i++) {
        index = [self nextImage:index];
    }
    
    [((UIImageView *) [imagesDisplayed objectAtIndex:0]) removeFromSuperview];
    [imagesDisplayed removeObjectAtIndex:0];
    
    UIImageView *currentImage = [[UIImageView alloc] initWithImage:[images objectAtIndex:index]];
    currentImage.frame = [[imagesPosition objectAtIndex:0] getCGRect];
    [imagesDisplayed addObject:currentImage];
    [self insertSubview:currentImage atIndex:0];
    [currentImage release];
}


- (void)moveToPreviousImage {
    selectedImageIndex = [self previousImage:selectedImageIndex];
    NSInteger index = selectedImageIndex;
    
    for (int i = 0; i < (([imagesDisplayed count] - 1) / 2); i++) {
        index = [self previousImage:index];
    }
    
    [((UIImageView *) [imagesDisplayed lastObject]) removeFromSuperview];
    [imagesDisplayed removeLastObject];
    
    UIImageView *currentImage = [[UIImageView alloc] initWithImage:[images objectAtIndex:index]];
    currentImage.frame = [[imagesPosition lastObject] getCGRect];
    [imagesDisplayed insertObject:currentImage atIndex:0];
    [self insertSubview:currentImage atIndex:0];
    [currentImage release];
}


- (void)replaceImages:(NSInteger)movement {

    for (int i = 0; i < (([imagesDisplayed count] - 1) / 2); i++) {
        if (movement == 0 || movement > 0) {
            [self bringSubviewToFront:[imagesDisplayed objectAtIndex:i]];
            [self bringSubviewToFront:[imagesDisplayed objectAtIndex:(([imagesPosition count] - 1) - i)]];
        }
        else {
            [self bringSubviewToFront:[imagesDisplayed objectAtIndex:(([imagesPosition count] - 1) - i)]];
            [self bringSubviewToFront:[imagesDisplayed objectAtIndex:i]];
        }
    }
    
    [self bringSubviewToFront:[imagesDisplayed objectAtIndex:(([imagesPosition count] - 1) / 2)]];
}

@end
