#import <UIKit/UIKit.h>

#define ITEM_SIZE            30
#define ITEM_MENU_X          ((self.frame.size.width / 2) - (ITEM_MENU_WIDTH / 2))
#define ITEM_MENU_Y          ((self.frame.size.height / 2) - (ITEM_MENU_HEIGHT / 2))
#define ITEM_MENU_WIDTH      30
#define ITEM_MENU_HEIGHT     128
#define MARGE_ITEM           2
#define REDUCTION_PERCENTAGE 15

#define DELETE_X      (self.frame.size.width / 2) - (DELETE_WIDTH / 2)
#define DELETE_Y      (self.frame.size.height - DELETE_HEIGHT - 4)
#define DELETE_WIDTH  32
#define DELETE_HEIGHT 30

#define SHITCH_TOOL_X      (self.frame.size.width / 2) - (SHITCH_TOOL_WIDTH / 2)
#define SHITCH_TOOL_Y      4
#define SHITCH_TOOL_WIDTH  50
#define SHITCH_TOOL_HEIGHT 30

@class EditorAction, RessourceManager, ItemMenu;


@interface EditorActionView : UIView {
    
    EditorAction *editorAction;
    
    RessourceManager *resource;
    
    NSArray *itemsLevel0;
    NSArray *itemsLevel1;
    
    UIButton *shitchTool;
    UIButton *remove;
    ItemMenu *itemMenuLevel0;
    ItemMenu *itemMenuLevel1;
}

@property (nonatomic, retain) EditorAction *editorAction;

- (id)initWithFrame:(CGRect)frame controller:(EditorAction *)myController;

- (void)initItems;
- (void)buildUserInterface;

- (void)shitchToolTypeAction;
- (void)removeAction;

@end
