//
//  EditorInformationView.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorInformationView.h"
#import "EditorInformation.h"
#import "RessourceManager.h"
#import "AnimationSequence.h"


@implementation EditorInformationView

@synthesize editorInformation;


- (id)initWithFrame:(CGRect)frame controller:(EditorInformation *)myController {
    self = [super initWithFrame:frame];
    
    if (self) {
        resource = [RessourceManager sharedRessource];
        
        self.editorInformation = myController;
        self.backgroundColor = [UIColor blueColor];
        [self initUserInterface];
    }
    
    return self;
}


- (void)dealloc {
    [editorInformation release];
    [super dealloc];
}


- (void)initUserInterface {
    pause = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    pause.frame = CGRectMake(PAUSE_X, PAUSE_Y, PAUSE_WIDTH, PAUSE_HEIGHT);
    [pause setTitle:@"Pause" forState:UIControlStateNormal];
    [pause addTarget:self action:@selector(pauseAction) forControlEvents:UIControlEventTouchDown];
    
    player = [[UIButton alloc] init];
    [player setImage:[((AnimationSequence *)[[resource.bitmapsPlayer objectForKey:@"white"] valueForKey:@"idle"]).sequences objectAtIndex:0] forState:UIControlStateNormal];
    player.frame = CGRectMake(PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
    [player addTarget:self action:@selector(playerAction) forControlEvents:UIControlEventTouchUpInside];
    
    NSArray *segmentTextContent = [NSArray arrayWithObjects: @"Ground", @"All", nil];
    displayType = [[UISegmentedControl alloc] initWithItems:segmentTextContent];
    [displayType setFrame:CGRectMake(DISPLAY_TYPE_X, DISPLAY_TYPE_Y, DISPLAY_TYPE_WIDTH, DISPLAY_TYPE_HEIGHT)];
    [displayType addTarget:self action:@selector(displayTypeAction) forControlEvents:UIControlEventValueChanged];

    displayType.segmentedControlStyle = UISegmentedControlStyleBar;
//    displayType.tintColor=[UIColor colorWithRed:36/255.0 green:61/255.0 blue:103/255.0 alpha:1];
    displayType.selectedSegmentIndex = 1;
    
    [self addSubview:pause];
    [self addSubview:player];
    [self addSubview:displayType];
}


#pragma mark - Action

- (void)displayTypeAction {
    
    if (displayType.selectedSegmentIndex == 0) {
        [editorInformation displayBlocks:NO];
    }
    else {
        [editorInformation displayBlocks:YES];
    }
}


- (void)pauseAction {
    NSLog(@"Pause");
    [editorInformation pauseAction];
}


- (void)playerAction {
    [editorInformation changeTool:@"player"];
}

@end
