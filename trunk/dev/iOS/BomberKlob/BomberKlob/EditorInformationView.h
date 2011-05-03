#import <UIKit/UIKit.h>

#define FIRST_PLAYER_X (self.frame.size.width / 3)
#define PLAYER_Y       ((self.frame.size.height / 2) - (PLAYER_HEIGHT / 2))
#define PLAYER_MARGE   4
#define PLAYER_WIDTH   19
#define PLAYER_HEIGHT  34

#define PAUSE_X      10
#define PAUSE_Y      ((self.frame.size.height / 2) - (PAUSE_HEIGHT / 2))
#define PAUSE_WIDTH  60
#define PAUSE_HEIGHT 30

#define DISPLAY_TYPE_X      (self.frame.size.width - DISPLAY_TYPE_WIDTH - 10)
#define DISPLAY_TYPE_Y      ((self.frame.size.height / 2) - (DISPLAY_TYPE_HEIGHT / 2))
#define DISPLAY_TYPE_WIDTH  120
#define DISPLAY_TYPE_HEIGHT 30


@class EditorInformation, RessourceManager;


@interface EditorInformationView : UIView {
    
    EditorInformation *editorInformation;
    
    RessourceManager *resource;
    
    UIButton *pause;
    UIButton *playerWhite;
    UIButton *playerBlue;
    UIButton *playerRed;
    UIButton *playerBlack;
    UISegmentedControl *displayType;
}

@property (nonatomic, retain) EditorInformation *editorInformation;

- (id)initWithFrame:(CGRect)frame controller:(EditorInformation *)myController;
- (void)dealloc;

- (void)initUserInterface;

- (void)displayTypeAction;
- (void)pauseAction;
- (void)playerAction:(NSString *)colorValue;

@end
