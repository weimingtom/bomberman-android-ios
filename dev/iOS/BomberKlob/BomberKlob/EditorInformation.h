#import <Foundation/Foundation.h>

@class EditorInformationView, EditorViewController;


@interface EditorInformation : NSObject {
    
    EditorInformationView *editorInformationView;
    EditorViewController *editorViewController;
    
    NSString *colorPlayer;
}

@property (nonatomic, retain) EditorInformationView *editorInformationView;
@property (nonatomic, retain) EditorViewController *editorViewController;
@property (nonatomic, retain) NSString *colorPlayer;

- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController;
- (void)dealloc;

- (void)displayBlocks:(BOOL)display;
- (void)pauseAction;

- (void)changeTool:(NSString *)tool;

@end
