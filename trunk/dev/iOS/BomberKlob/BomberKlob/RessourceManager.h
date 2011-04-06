//
//  RessourceManager.h
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface RessourceManager : NSObject {
    
}

- (void) loadBitmap:(NSInteger) tileSize;

- (void) update;

- (void) hasAnimationFinished;

- (void) destroy;

@end
