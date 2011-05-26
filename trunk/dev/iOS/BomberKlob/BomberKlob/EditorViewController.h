#import <Foundation/Foundation.h>

@class Editor, EditorMapZone, EditorInformation, EditorAction, PauseMenu, Position, Objects;


@interface EditorViewController : UIViewController {
    
    Editor *mapEditor;
    
    EditorMapZone *editorMapZone;
    EditorInformation *editorInformation;
    EditorAction *editorAction;
    PauseMenu *pauseMenu;
    
    NSString *selectedTool;
}

@property (nonatomic, retain) Editor *mapEditor;
@property (nonatomic, retain) NSString *selectedTool;

- (id)initWithMapName:(NSString *)mapName;
- (void)dealloc;

- (void)clickOnPosition:(Position *)position;
- (void)displayBlocks:(BOOL)display;
- (void)pauseAction;
- (void)resumeAction;
- (void)reset;
- (void)saveAction;
- (void)quitAction;

@end
