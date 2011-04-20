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
    
    NSInteger identifier;
    NSString *pseudo;
    NSString *userName;
    NSString *password;
    BOOL connectionAuto;
    BOOL rememberPassword;
    NSInteger color;
    NSInteger menuPosition;
    NSInteger gameWon;
    NSInteger gameLost;
}

@property (readonly) NSInteger identifier;
@property (nonatomic, retain) NSString *pseudo;
@property (nonatomic, retain) NSString *userName;
@property (nonatomic, retain) NSString *password;
@property (nonatomic) BOOL connectionAuto;
@property (nonatomic) BOOL rememberPassword;
@property (nonatomic) NSInteger color;
@property (nonatomic) NSInteger menuPosition;
@property (nonatomic) NSInteger gameWon;
@property (nonatomic) NSInteger gameLost;

- (id)initWithPseudo:(NSString *)aPseudo;
- (id)initWithId:(NSInteger)aId;

- (void)saveInDataBase;

@end
