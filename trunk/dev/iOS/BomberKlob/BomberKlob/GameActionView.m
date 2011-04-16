//
//  GameActionView.m
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameActionView.h"


@implementation GameActionView

- (id) initWithFrame:(CGRect)frame{
	self = [super initWithFrame:frame];
	
	if (self){
		
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextSetFillColorWithColor(context, [UIColor purpleColor].CGColor);
	
	CGContextFillRect(context, rect);
}

@end
