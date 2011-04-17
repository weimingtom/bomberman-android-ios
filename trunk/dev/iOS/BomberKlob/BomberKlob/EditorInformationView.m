//
//  EditorInformationView.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorInformationView.h"
#import "EditorInformation.h"


@implementation EditorInformationView

@synthesize editorInformation;

- (id)initWithFrame:(CGRect)frame controller:(EditorInformation *)myController {
    self = [super initWithFrame:frame];
    
    if (self) {
        self.editorInformation = myController;
        self.backgroundColor = [UIColor blueColor];
    }
    
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

- (void)dealloc {
    [editorInformation release];
    [super dealloc];
}

@end
