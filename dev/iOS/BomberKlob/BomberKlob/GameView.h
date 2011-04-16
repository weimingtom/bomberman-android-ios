//
//  View.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class RessourceManager, Map;

@interface GameView : UIView {
	NSDictionary * bitmapsInanimates;
	RessourceManager* ressource;
	Map * map;
	NSMutableArray * players;
	
}

@property (nonatomic,copy) NSDictionary * bitmapsInanimates;
@property (nonatomic, retain) RessourceManager* ressource;
@property (nonatomic, retain) Map* map;
@property (nonatomic, retain) NSMutableArray * players;

- (id) initWithMap: (Map * )value;
- (id) initWithFrame:(CGRect)frame;
- (void)drawAll: (CGContextRef) context;
- (void) startTimer;
- (void) startTimerThread;


-(id) getPng:(NSString *)path;
@end
