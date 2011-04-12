//
//  EditorGameZoneView.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 11/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorGameZoneView.h"


@implementation EditorGameZoneView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    
    if (self) {
        // Initialization code
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

- (void)dealloc
{
    [super dealloc];
}


- (void)drawRect:(CGRect)rect {
    
    CGContextRef context = UIGraphicsGetCurrentContext();	
    
    UIImage *img = [UIImage imageNamed:@"Power.png"];
    
    [img drawInRect:CGRectMake(50, 50, 150, 105)];
//    CGImageRef image = img.CGImage;
//	
//	CGContextDrawImage(context, CGRectMake(50.0, 50.0, 150.0, 150.0), image);
    
//    CGContextFillRect(context, CGRectMake(50, 50, 50, 50));
}

@end
