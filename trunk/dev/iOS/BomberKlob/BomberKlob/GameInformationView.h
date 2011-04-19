//
//  GameInformationView.h
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class RessourceManager, Engine;


@interface GameInformationView : UIView {
    RessourceManager * resource;
	Engine * engine;
}

- (id) initWithFrame:(CGRect)frame Engine:(Engine *)engineValue;

@end
