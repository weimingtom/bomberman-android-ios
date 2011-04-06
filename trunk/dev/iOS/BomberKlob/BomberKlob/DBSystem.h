//
//  System.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class DataBase, DBUser;


@interface DBSystem : NSObject {
    
    DataBase *dataBase;
    
    NSUInteger volume;
    BOOL mute;
    NSString *language;
    DBUser *lastUser;
}

@property (nonatomic) NSUInteger volume;
@property (nonatomic) BOOL mute;
@property (nonatomic, retain) NSString *language;
@property (nonatomic, retain) DBUser *lastUser;

- (id)initWithVolume:(NSUInteger)aVolume mute:(BOOL)aMute language:(NSString *)aLanguage lastUser:(DBUser *)anUser;
- (void)dealloc;

- (void)saveInDataBase;
- (void)updateVolume;
- (void)updateMute;
- (void)updateLanguage;
- (void)updateLastUser;

@end
