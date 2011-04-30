//
//  FrameInfo.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface FrameInfo : NSObject {
    CGRect rect;
	NSUInteger nextFrameDelay;
}

@property (nonatomic) CGRect rect;
@property (nonatomic) NSUInteger nextFrameDelay;

@end
