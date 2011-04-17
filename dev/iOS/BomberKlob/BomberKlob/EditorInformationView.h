//
//  EditorInformationView.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class EditorInformation;


@interface EditorInformationView : UIView {
    
    EditorInformation *editorInformation;
}

@property (nonatomic, retain) EditorInformation *editorInformation;

- (id)initWithFrame:(CGRect)frame controller:(EditorInformation *)myController;

@end
