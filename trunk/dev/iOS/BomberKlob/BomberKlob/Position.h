//
//  Position.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Position : NSObject {
	NSUInteger x;
	NSUInteger y;
}

@property (nonatomic) NSUInteger x;
@property (nonatomic) NSUInteger y;

- (id) init;

- (id) initWithXAndY:(NSUInteger)xValue:(NSUInteger) yValue;

- (NSString *)description;

@end
