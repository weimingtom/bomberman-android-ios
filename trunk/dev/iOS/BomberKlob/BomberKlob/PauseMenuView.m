#import "PauseMenuView.h"
#import "PauseMenu.h"
#import "RessourceManager.h"


@implementation PauseMenuView

@synthesize pauseMenu, resume, save, quit;


- (id)initWithFrame:(CGRect)frame controller:(PauseMenu *)myController {
    self = [super initWithFrame:frame];
    
    if (self) {
        resource = [RessourceManager sharedRessource];
        
        self.pauseMenu = myController;
        self.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.65];
        
        [self initUserInterface]; 
    }
    
    return self;
}


- (void)dealloc {
    [pauseMenu release];
    [resume release];
    [save release];
    [reset release];
    [quit release];
    [super dealloc];
}


- (void)initUserInterface {
    
    resume = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    resume.frame = CGRectMake(0, 0, 80, 37);
    resume.center = CGPointMake(resource.screenHeight / 2, 70);
    [resume setTitle:@"Resume" forState:UIControlStateNormal];
    [resume addTarget:self action:@selector(resumeAction) forControlEvents:UIControlEventTouchDown];
    
    save = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    save.frame = CGRectMake(0, 0, 60, 37);
    save.center = CGPointMake(resource.screenHeight / 2, 120);
    [save setTitle:@"Save" forState:UIControlStateNormal];
    [save addTarget:self action:@selector(saveAction) forControlEvents:UIControlEventTouchDown];
    
    reset = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    reset.frame = CGRectMake(0, 0, 64, 37);
    reset.center = CGPointMake(resource.screenHeight / 2, 170);
    [reset setTitle:@"Reset" forState:UIControlStateNormal];
    [reset addTarget:self action:@selector(resetAction) forControlEvents:UIControlEventTouchDown];
    
    quit = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    quit.frame = CGRectMake(0, 0, 56, 37);
    quit.center = CGPointMake(resource.screenHeight / 2, 220);
    [quit setTitle:@"Quit" forState:UIControlStateNormal];
    [quit addTarget:self action:@selector(quitAction) forControlEvents:UIControlEventTouchDown];
    
    [self addSubview:resume];
    [self addSubview:save];
    [self addSubview:reset];
    [self addSubview:quit];
}


- (void)resumeAction {
    [pauseMenu resumeAction];
}


- (void)saveAction {
    [pauseMenu saveAction];
}


- (void)resetAction {
    [pauseMenu reset];
}


- (void)quitAction {
    [pauseMenu quitAction];
}

@end
