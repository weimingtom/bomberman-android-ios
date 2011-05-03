#import "EditorInformation.h"
#import "EditorInformationView.h"
#import "EditorViewController.h"


@implementation EditorInformation

@synthesize editorInformationView, editorViewController, colorPlayer;


- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController {
    self = [super init];
    
    if (self) {
        editorViewController = myController;
        editorInformationView = [[EditorInformationView alloc] initWithFrame:frame controller:self];
    }
    
    return self;
}


- (void)dealloc {
    [editorInformationView release];
    [editorViewController release];
    [colorPlayer release];
    [super dealloc];
}


- (void)displayBlocks:(BOOL)display {
    [editorViewController displayBlocks:display];
}


- (void)pauseAction {
    [editorViewController pauseAction];
}

- (void)changeTool:(NSString *)tool {
    editorViewController.selectedTool = tool;
}

@end
