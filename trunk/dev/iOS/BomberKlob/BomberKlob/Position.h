//
//  Position.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Position : NSObject {
	NSInteger x;
	NSInteger y;
}

- (void) setX:(NSInteger) value;
- (void) setY:(NSInteger) value;
- (NSInteger) x;
- (NSInteger) y;

@end
