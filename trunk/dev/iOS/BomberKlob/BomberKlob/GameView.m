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

- (id) initWithMap:(Map *) value frame:(CGRect) dimension{	
	ressource = [RessourceManager sharedRessource];
	self = [self initWithFrame:dimension];
	
	if (self){
		map = value;
		[self startTimer];
	}
	
	return self;
}


- (id) initWithFrame:(CGRect)frame{	
	self = [super initWithFrame:frame];
	
	if (self){
		ressource = [RessourceManager sharedRessource];
		bitmapsInanimates = ressource.bitmapsInanimates;
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	CGContextRef context = UIGraphicsGetCurrentContext();

	[self drawAll:context];	
}

- (void)drawAll: (CGContextRef) context{
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

-(void) startTimer
{
	NSThread * updateThread = [[[NSThread alloc] initWithTarget:self selector:@selector(startTimerThread) object:nil]autorelease]; //Create a new thread
	[updateThread start]; //start the thread
}

//the thread starts by sending this message
-(void) startTimerThread {
	
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	CGRect dimension = [self bounds];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];

	[[NSTimer scheduledTimerWithTimeInterval: 0.03125 target: self selector: @selector(setNeedsDisplay) userInfo:self repeats: YES] retain];	
	[runLoop run];
	[pool release];
}

@end
