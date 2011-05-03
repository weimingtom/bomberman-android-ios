#import <Foundation/Foundation.h>

#define ALPHA 0.3


@class EditorMapZoneView, EditorViewController, Position, Objects;

@interface EditorMapZone : NSObject {
    
    EditorMapZoneView *editorMapZoneView;
    EditorViewController *editorViewController;
    
    CGFloat alpha;
}

@property (nonatomic, retain) EditorMapZoneView *editorMapZoneView;
@property (nonatomic, retain) EditorViewController *editorViewController;
@property (nonatomic) CGFloat alpha;

- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController;
- (void)dealloc;

- (void)clickOnPosition:(Position *)position;
- (void)displayBlocks:(BOOL)display;

@end
