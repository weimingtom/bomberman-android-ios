//
//  EditorInformation.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class EditorInformationView, EditorViewController;


@interface EditorInformation : NSObject {
    
    EditorInformationView *editorInformationView;
    EditorViewController *editorViewController;
}

@property (nonatomic, retain) EditorInformationView *editorInformationView;
@property (nonatomic, retain) EditorViewController *editorViewController;

- (id)initWithFrame:(CGRect)frame controller:(EditorViewController *)myController;
- (void)dealloc;

- (void)displayBlocks:(BOOL)display;
- (void)pauseAction;

- (void)changeTool:(NSString *)tool;

@end
