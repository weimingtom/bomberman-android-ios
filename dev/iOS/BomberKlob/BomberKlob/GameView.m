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
	NSLog(@"INIT GameView");
	
	
	self = [self initWithFrame:CGRectMake(0, 0,320 , 480)];
	
	if (self){
		map = value;
		/*CGAffineTransform transform = CGAffineTransformMakeRotation(3.14159);
		self.transform = transform;*/
	
	}
	
	return self;
}


- (id) initWithFrame:(CGRect)frame{
	NSLog(@"INITWITHFRAME GameView");
	
	self = [super initWithFrame:frame];
	
	if (self){
		ressource = [RessourceManager sharedRessource];
		bitmapsInanimates = ressource.bitmapsInanimates;
//		[self setNeedsDisplay];

		NSLog(@"ressource actived");
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	
	CGContextRef context = UIGraphicsGetCurrentContext();	
	
	
	CGImageRef image ;
	
	[map draw:context];
	
	for (Player * player in players) {
		[player draw:context];
	}
	
}


-(CGImageRef) getPng:(NSString *) path{
	
	UIImage* image = [UIImage imageNamed:path];
	CGImageRef imageRef = image.CGImage;
	
    return imageRef;
	
}

@end
