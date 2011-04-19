//
//  EditorInformationView.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class EditorInformation, RessourceManager;


@interface EditorInformationView : UIView {
    
    EditorInformation *editorInformation;
    
    RessourceManager *resource;
    
    UIButton *pause;
    UIImageView *player1;
    UIImageView *player2;
    UIImageView *player3;
    UIImageView *player4;
    UISegmentedControl *displayType;
}

@property (nonatomic, retain) EditorInformation *editorInformation;

- (id)initWithFrame:(CGRect)frame controller:(EditorInformation *)myController;
- (void)dealloc;

- (void)initUserInterface;

- (void)displayTypeAction;
- (void)pauseAction;

@end
