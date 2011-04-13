//
//  Position.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Position : NSObject <NSCoding> {
	NSInteger x;
	NSInteger y;
}

@property (nonatomic) NSInteger x;
@property (nonatomic) NSInteger y;

- (id) init;

- (id) initWithXAndY:(NSInteger)xValue:(NSInteger) yValue;

- (NSString *)description;

@end
