//
//  GameInformationView.m
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameInformationView.h"


@implementation GameInformationView

- (id) initWithFrame:(CGRect)frame{
	self = [super initWithFrame:frame];
	
	if (self){
		
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextSetFillColorWithColor(context, [UIColor orangeColor].CGColor);
	
	CGContextFillRect(context, rect);
}

@end
