#import "EditorAction.h"
#import "EditorActionView.h"
#import "EditorViewController.h"


@implementation EditorAction

@synthesize editorViewAction, editorViewController, selectedObjectType, selectedObjectLevel0, selectedObjectLevel1;


- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController {
    self = [super init];
    
    if (self) {
        self.editorViewController = myController;
        editorViewAction = [[EditorActionView alloc] initWithFrame:frame controller:self];
        self.selectedObjectType = @"level0";
        self.selectedObjectLevel0 = @"grass1";
        self.selectedObjectLevel1 = @"block1";
    }
    
    return self;
}


- (void)dealloc {
    [editorViewAction release];
    [editorViewController release];
    [selectedObjectType release];
    [selectedObjectLevel0 release];
    [selectedObjectLevel1 release];
    [super dealloc];
}


- (void)changeTool:(NSString *)tool {
    
    if ([tool isEqualToString:@"delete"]) {        
        if ([editorViewController.selectedTool isEqualToString:@"delete"]) {            
            if ([selectedObjectType isEqualToString:@"level0"]) {
                tool = selectedObjectLevel0;
            }
            else if ([selectedObjectType isEqualToString:@"level1"]) {
                tool = selectedObjectLevel1;
            }
        }
    }
    else {        
        if ([selectedObjectType isEqualToString:@"level0"]) {
            selectedObjectLevel0 = tool;
        }
        else if ([selectedObjectType isEqualToString:@"level1"]) {
            selectedObjectLevel1 = tool;
        }
    }
    
    editorViewController.selectedTool = tool;
}


- (void)selectedItemChange:(id)tool {
    [self changeTool:(NSString *) tool];
}


- (NSString *)getSelectedObject {
    
    if ([selectedObjectType isEqualToString:@"level0"]) {
        return selectedObjectLevel0;
    }
    else if ([selectedObjectType isEqualToString:@"level1"]) {
        return selectedObjectLevel1;
    }
    
    return nil;
}

@end
