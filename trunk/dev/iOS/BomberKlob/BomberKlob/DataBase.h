//
//  DataBase.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 03/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "sqlite3.h"

@class DBMap;


@interface DataBase : NSObject {
    
@private
    sqlite3 *dataBase;
}

+ (DataBase *)instance;

- (void)openDataBase;
- (void)loadDataBase;

- (void)initSystemTable;
- (void)initMapTable;

- (void)insertInto:(NSString *)table values:(NSString *)values;
- (void)update:(NSString *)table set:(NSString *)values where:(NSString *)condition;
- (sqlite3_stmt *)select:(NSString *)attributes from:(NSString *)table where:(NSString *)condition;
- (void)createOrUpdateMap:(DBMap *)map;

@end
