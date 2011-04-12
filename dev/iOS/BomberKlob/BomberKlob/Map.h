//
//  Map.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#define WIDTH 21
#define HEIGHT 15

@class RessourceManager;


@interface Map : NSObject {
    
	RessourceManager *ressource;
	NSString *name;
	NSMutableArray *grounds;
	NSMutableArray *blocks;
	NSMutableDictionary *bitmapsInanimates;
}

@property (nonatomic, retain) NSMutableArray * grounds;
@property (nonatomic, retain) NSMutableArray * blocks;

- (id) init;
- (id) initWithPath;

- (void) save;
- (void) load:(NSString*)path;
- (void) addGround:(NSInteger) ground;
- (void) addBlock:(NSInteger) block;
- (void) deleteGround: (NSInteger) ground;
- (void) deleteBlock: (NSInteger)block;
- (void) destroyBlock: (NSInteger)block;
- (void) draw;

@end
