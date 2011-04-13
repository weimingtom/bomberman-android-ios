//
//  RessourceManager.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface RessourceManager : NSObject {
	NSInteger tileSize;
	NSInteger screenHeight;
	NSInteger screenWidth;

	NSMutableDictionary * bitmapsInanimates;
	NSMutableDictionary * bitmapsPlayer;
}

@property (nonatomic, retain) NSMutableDictionary * bitmapsInanimates;
@property (nonatomic, copy) NSMutableDictionary * bitmapsPlayer;
@property (nonatomic) NSInteger tileSize;
@property (nonatomic) NSInteger screenHeight;
@property (nonatomic) NSInteger screenWidth;


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
