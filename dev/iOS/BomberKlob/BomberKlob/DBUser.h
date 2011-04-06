//
//  User.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class DataBase;


@interface DBUser : NSObject {
    
    DataBase *dataBase;
    
    NSUInteger identifier;
    NSString *pseudo;
    NSString *userName;
    NSString *password;
    BOOL connectionAuto;
    BOOL rememberPassword;
    NSUInteger color;
    NSUInteger menuPosition;
    NSUInteger gameWon;
    NSUInteger gameLost;
}

// TODO: A changer...
@property (readonly) NSUInteger identifier;
@property (nonatomic, retain) NSString *pseudo;
@property (nonatomic, retain) NSString *userName;
@property (nonatomic, retain) NSString *password;
@property (nonatomic) BOOL connectionAuto;
@property (nonatomic) BOOL rememberPassword;
@property (nonatomic) NSUInteger color;
@property (nonatomic) NSUInteger menuPosition;
@property (nonatomic) NSUInteger gameWon;
@property (nonatomic) NSUInteger gameLost;

- (id)initWithPseudo:(NSString *)aPseudo;
- (id)initWithId:(NSUInteger)aId;
- (void)dealloc;

- (void)saveInDataBase;

@end
