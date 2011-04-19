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
    pause.frame = CGRectMake(10, 5, 60, 30);
    [pause setTitle:@"Pause" forState:UIControlStateNormal];
    [pause addTarget:self action:@selector(pauseAction) forControlEvents:UIControlEventTouchDown];
    
    player1 = [[UIImageView alloc] initWithImage:[((AnimationSequence *)[[resource.bitmapsPlayer objectForKey:@"white"] valueForKey:@"idle"]).sequences objectAtIndex:0]];
    player1.frame = CGRectMake(150, 4, 19, 34);
    
    player2 = [[UIImageView alloc] initWithImage:[((AnimationSequence *)[[resource.bitmapsPlayer objectForKey:@"blue"] valueForKey:@"idle"]).sequences objectAtIndex:0]];
    player2.frame = CGRectMake(150 + 4 + 19, 4, 19, 34);
    
    player3 = [[UIImageView alloc] initWithImage:[((AnimationSequence *)[[resource.bitmapsPlayer objectForKey:@"red"] valueForKey:@"idle"]).sequences objectAtIndex:0]];
    player3.frame = CGRectMake(150 + 4 + 19 + 4 + 19, 4, 19, 34);
    
    player4 = [[UIImageView alloc] initWithImage:[((AnimationSequence *)[[resource.bitmapsPlayer objectForKey:@"black"] valueForKey:@"idle"]).sequences objectAtIndex:0]];
    player4.frame = CGRectMake(150 + 4 + 19 + 4 + 19 + 4 + 19, 4, 19, 34);
    
    NSArray *segmentTextContent = [NSArray arrayWithObjects: @"Ground", @"All", nil];
    displayType = [[UISegmentedControl alloc] initWithItems:segmentTextContent];
    [displayType setFrame:CGRectMake(350, 5, 120, 30)];
    [displayType addTarget:self action:@selector(displayTypeAction) forControlEvents:UIControlEventValueChanged];

    displayType.segmentedControlStyle = UISegmentedControlStyleBar;
//    displayType.tintColor=[UIColor colorWithRed:36/255.0 green:61/255.0 blue:103/255.0 alpha:1];
    displayType.selectedSegmentIndex = 1;
    
    [self addSubview:pause];
    [self addSubview:player1];
    [self addSubview:player2];
    [self addSubview:player3];
    [self addSubview:player4];
    [self addSubview:displayType];
}


#pragma mark - Action

- (void)displayTypeAction {
    NSLog(@"DisplayType: %d", displayType.selectedSegmentIndex);
}


- (void)pauseAction {
    NSLog(@"Pause");
    [editorInformation pauseAction];
}

@end
