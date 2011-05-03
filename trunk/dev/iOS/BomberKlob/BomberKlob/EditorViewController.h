#import <Foundation/Foundation.h>

@class MapEditor, EditorMapZone, EditorInformation, EditorAction, PauseMenu, Position, Objects;


@interface EditorViewController : UIViewController {
    
    MapEditor *mapEditor;
    
    EditorMapZone *editorMapZone;
    EditorInformation *editorInformation;
    EditorAction *editorAction;
    PauseMenu *pauseMenu;
    
    NSString *selectedTool;
}

@property (nonatomic, retain) MapEditor *mapEditor;
@property (nonatomic, retain) NSString *selectedTool;

- (id)initWithMapName:(NSString *)mapName;
- (void)dealloc;

- (void)clickOnPosition:(Position *)position;
- (void)displayBlocks:(BOOL)display;
- (void)pauseAction;
- (void)resumeAction;
- (void)saveAction;
- (void)quitAction;

@end
