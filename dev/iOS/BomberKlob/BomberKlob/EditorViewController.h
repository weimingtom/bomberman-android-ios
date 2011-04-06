//
//  EditorController.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MapEditor.h"
#import "GameView.h"

@interface EditorViewController : NSObject {
    MapEditor * mapEditor;
    GameView * view;
}

- (id) init;

@end
