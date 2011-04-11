//
//  RessourceManager.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface RessourceManager : NSObject {
	NSUInteger tileHeight;
	NSUInteger tileWidth;
	NSMutableDictionary * bitmapsInanimates;
	NSMutableDictionary * bitmapsPlayer;
}

@property (nonatomic, copy) NSMutableDictionary * bitmapsInanimates;
@property (nonatomic, copy) NSMutableDictionary * bitmapsPlayer;
@property (nonatomic) NSUInteger tileHeight;
@property (nonatomic) NSUInteger tileWidth;

+(RessourceManager*)sharedRessource;

+ (id) alloc;

- (id) init;

- (void) loadProperty;

- (void) loadBitmapInanimates;

- (void) loadBitmapPlayer;

- (void) update;

- (void) hasAnimationFinished;

- (void) destroy;

@end
