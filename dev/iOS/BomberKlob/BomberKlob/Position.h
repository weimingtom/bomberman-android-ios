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
- (id) initWithX:(NSInteger)aX y:(NSInteger)aY;
- (id) initWithPosition:(Position *) position;

- (BOOL)isEqual:(id)object;
- (NSString *)description;


@end
