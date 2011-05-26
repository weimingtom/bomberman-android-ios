#import "PauseMenu.h"
#import "EditorViewController.h"
#import "PauseMenuView.h"


@implementation PauseMenu

@synthesize pauseMenuView, editorViewController;


- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController {
    self = [super init];
    
    if (self) {
        editorViewController = myController;
        pauseMenuView = [[PauseMenuView alloc] initWithFrame:frame controller:self];
    }
    
    return self;
}


- (void)dealloc {
    [pauseMenuView release];
    [editorViewController release];
    [super dealloc];
}


- (void)resumeAction {
    [editorViewController resumeAction];
}


- (void)saveAction {
    [editorViewController saveAction];
    [editorViewController quitAction];
}


- (void)reset {
    [editorViewController reset];
    [editorViewController resumeAction];
}


- (void)quitAction {
    [editorViewController quitAction];
}

@end
