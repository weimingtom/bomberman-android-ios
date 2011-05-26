#import <UIKit/UIKit.h>

@class PauseMenu, RessourceManager;


@interface PauseMenuView : UIView {

    PauseMenu *pauseMenu;
    
    RessourceManager *resource;
    
    UIButton *resume;
    UIButton *save;
    UIButton *reset;
    UIButton *quit;
    
    BOOL newMap;
}

@property (nonatomic, retain) PauseMenu *pauseMenu;
@property (nonatomic, retain) UIButton *resume;
@property (nonatomic, retain) UIButton *save;
@property (nonatomic, retain) UIButton *reset;
@property (nonatomic, retain) UIButton *quit;

- (id)initWithFrame:(CGRect)frame controller:(PauseMenu *)myController;
- (void)dealloc;

- (void)initUserInterface;

- (void)resumeAction;
- (void)saveAction;
- (void)resetAction;
- (void)quitAction;

@end
