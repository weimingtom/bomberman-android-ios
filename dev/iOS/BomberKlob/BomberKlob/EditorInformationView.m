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
        self.backgroundColor = [UIColor colorWithRed:0.9 green:0.9 blue:0.9 alpha:1.0];
        [self initUserInterface];
    }
    
    return self;
}


- (void)dealloc {
    [editorInformation release];
    [pause release];
    [playerWhite release];
    [playerBlue release];
    [playerRed release];
    [playerBlue release];
    [displayType release];
    [super dealloc];
}


- (void)initUserInterface {
    pause = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    pause.frame = CGRectMake(PAUSE_X, PAUSE_Y, PAUSE_WIDTH, PAUSE_HEIGHT);
    [pause setTitle:@"Pause" forState:UIControlStateNormal];
    [pause addTarget:self action:@selector(pauseAction) forControlEvents:UIControlEventTouchDown];
    
    playerWhite = [[UIButton alloc] init];
    [playerWhite setImage:[[[resource.bitmapsPlayer objectForKey:@"white"] copy] valueForKey:@"idle"] forState:UIControlStateNormal];
    playerWhite.frame = CGRectMake(FIRST_PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
    [playerWhite addTarget:self action:@selector(playerAction:) forControlEvents:UIControlEventTouchUpInside];
    
    playerBlue = [[UIButton alloc] init];
    [playerBlue setImage:[[[resource.bitmapsPlayer objectForKey:@"blue"] copy] valueForKey:@"idle"] forState:UIControlStateNormal];
    playerBlue.frame = CGRectMake(FIRST_PLAYER_X + (PLAYER_WIDTH + PLAYER_MARGE), PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
    [playerBlue addTarget:self action:@selector(playerAction:) forControlEvents:UIControlEventTouchUpInside];
    
    playerRed = [[UIButton alloc] init];
    [playerRed setImage:[[[resource.bitmapsPlayer objectForKey:@"red"] copy] valueForKey:@"idle"] forState:UIControlStateNormal];
    playerRed.frame = CGRectMake(FIRST_PLAYER_X + (2 * (PLAYER_WIDTH + PLAYER_MARGE)), PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
    [playerRed addTarget:self action:@selector(playerAction:) forControlEvents:UIControlEventTouchUpInside];
    
    playerBlack = [[UIButton alloc] init];
    [playerBlack setImage:[[[resource.bitmapsPlayer objectForKey:@"black"] copy] valueForKey:@"idle"] forState:UIControlStateNormal];
    playerBlack.frame = CGRectMake(FIRST_PLAYER_X + (3 * (PLAYER_WIDTH + PLAYER_MARGE)), PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
    [playerBlack addTarget:self action:@selector(playerAction:) forControlEvents:UIControlEventTouchUpInside];
    
    NSArray *segmentTextContent = [NSArray arrayWithObjects: @"Ground", @"All", nil];
    displayType = [[UISegmentedControl alloc] initWithItems:segmentTextContent];
    [displayType setFrame:CGRectMake(DISPLAY_TYPE_X, DISPLAY_TYPE_Y, DISPLAY_TYPE_WIDTH, DISPLAY_TYPE_HEIGHT)];
    [displayType addTarget:self action:@selector(displayTypeAction) forControlEvents:UIControlEventValueChanged];

    displayType.segmentedControlStyle = UISegmentedControlStyleBar;
    displayType.selectedSegmentIndex = 1;
    
    [self addSubview:pause];
    [self addSubview:playerWhite];
    [self addSubview:playerBlue];
    [self addSubview:playerRed];
    [self addSubview:playerBlack];
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


- (void)playerAction:(id)sender {
    
    if (sender == playerWhite) {
        self.editorInformation.colorPlayer = @"white";
    }
    else if (sender == playerBlue) {
        self.editorInformation.colorPlayer = @"blue";
    }
    else if (sender == playerRed) {
        self.editorInformation.colorPlayer = @"red";
    }
    else if (sender == playerBlack) {
        self.editorInformation.colorPlayer = @"black";
    }
    
    [editorInformation changeTool:@"player"];
}

@end
