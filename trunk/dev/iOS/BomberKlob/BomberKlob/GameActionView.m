//
//  GameActionView.m
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameActionView.h"
#import "Engine.h"


@implementation GameActionView

- (id) initWithFrame:(CGRect)frame Engine:(Engine *) engineValue{
	self = [super initWithFrame:frame];
	
	if (self){
		engine = engineValue;
		[self initComponents];
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextSetFillColorWithColor(context, [UIColor purpleColor].CGColor);
	
	CGContextFillRect(context, rect);
}

- (void) initComponents{
	UIButton *  bombButton = [[UIButton alloc] initWithFrame:CGRectMake(0,self.bounds.size.height-( self.bounds.size.height/10), self.bounds.size.width, self.bounds.size.height/10)];
	[bombButton setTitle:@"BOOM" forState:UIControlStateNormal];
	[bombButton addTarget:self action:@selector(bombButtonClicked) forControlEvents:UIControlEventTouchUpInside];
	//[bombButton setBackgroundImage: forState:UIControlStateNormal
	[self addSubview:bombButton];
}

- (void) bombButtonClicked{
	NSLog(@"BOOM");
}

@end
