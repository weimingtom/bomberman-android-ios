#import <Foundation/Foundation.h>


@interface FrameInfo : NSObject {
    CGRect rect;
	NSUInteger nextFrameDelay;
}

@property (nonatomic) CGRect rect;
@property (nonatomic) NSUInteger nextFrameDelay;

@end
