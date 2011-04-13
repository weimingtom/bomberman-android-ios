//
//  View.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameView.h"
#import "RessourceManager.h"
#import "Map.h"
#import "Player.h"


@implementation GameView
@synthesize  bitmapsInanimates, ressource, map, players;

- (id) initWithMap:(Map *) value{	
	self = [self initWithFrame:CGRectMake(0, 0,[[UIScreen mainScreen] bounds].size.height , [[UIScreen mainScreen] bounds].size.width)];
	
	if (self){
		map = value;
		cpt = 0;
	}
	
	return self;
}


- (id) initWithFrame:(CGRect)frame{	
	self = [super initWithFrame:frame];
	
	if (self){
		ressource = [RessourceManager sharedRessource];
		bitmapsInanimates = ressource.bitmapsInanimates;
		cpt = 0;
		[self setNeedsDisplay];
	}
	
	return self;
}

- (void) threadUpdate{
	NSThread * updateThread = [[NSThread alloc] initWithTarget:self selector:@selector(updateView) object:nil];
	[updateThread start];
}

- (void) updateView{
	[self setNeedsDisplay];
	//[NSThread exit];
}

- (void)drawRect:(CGRect)rect{
	
	CGContextRef context = UIGraphicsGetCurrentContext();
	[map draw:context];
		
	for (Player * player in players) {
		[player draw:context];
	}	
	

	//[NSThread exit];
}



-(CGImageRef) getPng:(NSString *) path{
	
	UIImage* image = [UIImage imageNamed:path];
	CGImageRef imageRef = image.CGImage;
	
    return imageRef;
	
}

@end
