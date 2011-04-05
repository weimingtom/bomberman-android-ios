//
//  System.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class DataBase, User;


@interface System : NSObject {
    
    DataBase *dataBase;
    
    NSUInteger volume;
    NSString *language;
    User *lastUser;
}

@property (nonatomic) NSUInteger volume;
@property (nonatomic, retain) NSString *language;
@property (nonatomic, retain) User *lastUser;

- (id)initWithVolume:(NSUInteger)aVolume language:(NSString *)aLanguage lastUser:(User *)anUser;
- (void)dealloc;

- (void)saveInDataBase;
- (void)updateLastUser;

@end
