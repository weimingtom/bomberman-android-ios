//
//  Application.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class User;

@interface Application : NSObject {
    
    User *user;
    NSArray *pseudos;
}

@property (nonatomic, retain) User *user;
@property (nonatomic, retain) NSArray *pseudos;

- (id)init;
- (void)dealloc;

- (BOOL)pseudoAlreadyExists:(NSString *)pseudo;

@end
