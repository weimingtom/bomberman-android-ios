//
//  User.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface User : NSObject {
    
    NSString *pseudo;
    NSString *userName;
    NSString *password;
    BOOL connectionAuto;
    BOOL rembemberPassword;
    NSString *color;
    NSString *menuPosition;
    NSInteger gameWon;
    NSInteger gameLost;
}

@property (nonatomic, retain) NSString *pseudo;

- (id)init;
- (id)initWithId:(NSInteger)identifier;
- (void)dealloc;

@end
