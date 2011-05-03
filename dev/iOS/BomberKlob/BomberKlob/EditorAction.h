#import <Foundation/Foundation.h>
#import "ManageItemMenu.h"

@class EditorActionView, EditorViewController;


@interface EditorAction : NSObject <ManageItemMenu> {
    
    EditorActionView *editorActionView;
    EditorViewController *editorViewController;
    
    NSString *selectedObjectType;
    NSString *selectedObjectLevel0;
    NSString *selectedObjectLevel1;
}

@property (nonatomic, retain) EditorActionView *editorViewAction;
@property (nonatomic, retain) EditorViewController *editorViewController;
@property (nonatomic, retain) NSString *selectedObjectType;
@property (nonatomic, retain) NSString *selectedObjectLevel0;
@property (nonatomic, retain) NSString *selectedObjectLevel1;

- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController;
- (void)dealloc;

- (void)changeTool:(NSString *)tool;
- (NSString *)getSelectedObject;

@end
