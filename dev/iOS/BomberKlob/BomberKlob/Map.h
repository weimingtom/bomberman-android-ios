//
//  Map.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Map : NSObject {
	NSString *name;
	NSUInteger ** grounds;
	NSUInteger ** blocks;
}
- (void) save;
- (void) load:(NSString*)path;
- (void) addGround:(NSInteger) ground;
- (void) addBlock:(NSInteger) block;
- (void) deleteGround: (NSInteger) ground;
- (void) deleteBlock: (NSInteger)block;
- (void) destroyBlock: (NSInteger)block;
- (void) draw;

@end
