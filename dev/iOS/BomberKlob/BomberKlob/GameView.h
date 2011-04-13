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
	int cpt;
	
}

@property (nonatomic,copy) NSDictionary * bitmapsInanimates;
@property (nonatomic, retain) RessourceManager* ressource;
@property (nonatomic, retain) Map* map;
@property (nonatomic, retain) NSMutableArray * players;

- (id) initWithMap: (Map * )value;
- (id) initWithFrame:(CGRect)frame;


-(id) getPng:(NSString *)path;
@end
