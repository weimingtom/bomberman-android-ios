//
//  View.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameView.h"


@implementation GameView

- (id) init{
	NSLog(@"INIT GameView");
	
	self = [super initWithFrame:CGRectMake(0, 0, 280, 20)];
	
	if (self){
		UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(20, 50, 280, 20)];
		label.text = @"hello, world"; label.textAlignment = UITextAlignmentCenter;
		[self addSubview:label];
	}
	
	return self;
}

- (id) initWithFrame:(CGRect)frame{
	NSLog(@"INITWITHFRAME GameView");
	
	self = [super initWithFrame:frame];
	
	if (self){
		UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(20, 50, 280, 20)]; 
		label.text = @"hello, world"; label.textAlignment = UITextAlignmentCenter;
		[self addSubview:label];
	}
	
	return self;
}

@end
