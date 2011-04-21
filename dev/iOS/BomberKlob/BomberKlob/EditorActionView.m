//
//  EditorActionView.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorActionView.h"
#import "EditorAction.h"
#import "RessourceManager.h"


@implementation EditorActionView

@synthesize editorAction;

- (id)initWithFrame:(CGRect)frame controller:(EditorAction *)myController {
    self = [super initWithFrame:frame];
    
    if (self) {
        self.editorAction = myController;
        self.backgroundColor = [UIColor yellowColor];
        
        resource = [RessourceManager sharedRessource];

        [self initUserInterface];
    }
    
    return self;
}

- (void)dealloc {
    [editorAction release];
    [super dealloc];
}


- (void)initItems {
    // TODO: A compléter...
}


- (void)initUserInterface {
    
    [self initImages];
    
    remove = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    remove.frame = CGRectMake(DELETE_X, DELETE_Y, DELETE_SIZE_WIDTH, DELETE_SIZE_HEIGHT);
    [remove setTitle:@"X" forState:UIControlStateNormal];
    [remove addTarget:self action:@selector(removeAction) forControlEvents:UIControlEventTouchDown];
    
    [self addSubview:image1];
    [self addSubview:image2];
    [self addSubview:image3];
    [self addSubview:image4];
    [self addSubview:image5];
    [self addSubview:remove];
}


- (void)initImages {
    image1 = [[UIImageView alloc] initWithImage:[resource.bitmapsInanimates valueForKey:@"bloc"]];
    image1.frame = CGRectMake(ITEM_X, FIRST_ITEM_Y, ITEM_SIZE, ITEM_SIZE);
    
    image2 = [[UIImageView alloc] initWithImage:[resource.bitmapsInanimates valueForKey:@"block2"]];
    image2.frame = CGRectMake(ITEM_X, FIRST_ITEM_Y + ITEM_SIZE + MARGE_ITEM, ITEM_SIZE, ITEM_SIZE);
    
    image3 = [[UIImageView alloc] initWithImage:[resource.bitmapsInanimates valueForKey:@"block3"]];
    image3.frame = CGRectMake(ITEM_X, FIRST_ITEM_Y + ((ITEM_SIZE + MARGE_ITEM) * 2), ITEM_SIZE, ITEM_SIZE);
    
    image4 = [[UIImageView alloc] initWithImage:[resource.bitmapsInanimates valueForKey:@"stone2"]];
    image4.frame = CGRectMake(ITEM_X, FIRST_ITEM_Y + ((ITEM_SIZE + MARGE_ITEM) * 3), ITEM_SIZE, ITEM_SIZE);
    
    image5 = [[UIImageView alloc] initWithImage:[resource.bitmapsInanimates valueForKey:@"stone"]];
    image5.frame = CGRectMake(ITEM_X, FIRST_ITEM_Y + ((ITEM_SIZE + MARGE_ITEM) * 4), ITEM_SIZE, ITEM_SIZE);
}


- (void)removeAction {
    [editorAction changeTool:@"delete"];
}

@end
