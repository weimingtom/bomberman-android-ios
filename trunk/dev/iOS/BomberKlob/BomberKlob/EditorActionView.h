//
//  EditorActionView.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

#define ITEM_SIZE 30
#define FIRST_ITEM_Y 50
#define MARGE_BETWEEN_ITEM 2

@class EditorAction, RessourceManager;


@interface EditorActionView : UIView {
    
    EditorAction *editorAction;
    
    RessourceManager *resource;
    
    NSArray *items;
    UIImageView *image1;
    UIImageView *image2;
    UIImageView *image3;
    UIImageView *image4;
    UIImageView *image5;
    
    UIButton *remove;
}

@property (nonatomic, retain) EditorAction *editorAction;

- (id)initWithFrame:(CGRect)frame controller:(EditorAction *)myController;

- (void)initItems;
- (void)initUserInterface;
- (void)initImages;

- (void)removeAction;

@end
