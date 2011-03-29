//
//  Application.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class User, System;

@interface Application : NSObject {
    
    User *user;
    NSArray *pseudos;
    NSArray *maps;
    System *system;
}

@property (nonatomic, retain) User *user;
@property (nonatomic, retain) NSArray *pseudos;
@property (nonatomic, retain) NSArray *maps;
@property (nonatomic, retain) System *system;

- (id)init;
- (void)dealloc;

- (void)loadLastUser;
- (void)loadPseudos;
- (void)loadMaps;
- (void)loadSystem;

- (BOOL)existPlayer;
- (BOOL)pseudoAlreadyExists:(NSString *)pseudo;

@end
