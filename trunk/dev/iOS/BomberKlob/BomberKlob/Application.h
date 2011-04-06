//
//  Application.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class DataBase, DBUser, DBSystem;


@interface Application : NSObject {
    
    DataBase *dataBase;
    
    DBUser *user;
    NSArray *pseudos;
    NSArray *maps;
    DBSystem *system;
}

@property (nonatomic, retain) DBUser *user;
@property (nonatomic, retain) NSArray *pseudos;
@property (nonatomic, retain) NSArray *maps;
@property (nonatomic, retain) DBSystem *system;

- (id)init;
- (void)dealloc;

- (void)loadSystem;
- (void)loadPseudos;
- (void)loadMaps;

- (BOOL)existPlayer;
- (BOOL)pseudoAlreadyExists:(NSString *)pseudo;

@end
