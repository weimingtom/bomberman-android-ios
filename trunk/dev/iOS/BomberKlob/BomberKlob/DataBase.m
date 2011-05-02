//
//  DataBase.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 03/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DataBase.h"
#import "DBMap.h"
#import "DBUser.h"


@implementation DataBase

static DataBase *_dataBase = nil;


+ (id)alloc {
	@synchronized([DataBase class])	{
		NSAssert(_dataBase == nil, @"Attempted to allocate a second instance of a singleton.");
		_dataBase = [super alloc];
        
		return _dataBase;
	}
    
	return nil;
}


- (id)init {
	self = [super init];
    
	if (self != nil) {
		[self openDataBase];
        [self loadDataBase];
	}
    
	return self;
}


- (void)dealloc {
    [_dataBase release];
    sqlite3_close(dataBase);
    [super dealloc];
}


+ (DataBase *)sharedDataBase {
	@synchronized([DataBase class]) {
		if (!_dataBase) {
			[[self alloc] init];
        }
        
		return _dataBase;
	}
    
	return nil;
}


- (NSString *)dataBasePath { 
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDir = [paths objectAtIndex:0];
    
   // return [documentsDir stringByAppendingPathComponent:@"dataBase.sqlite"];
	return [[[NSBundle mainBundle]bundlePath] stringByAppendingPathComponent:@"database.sqlite"];
}


- (void)openDataBase {
    // Create the database if it not exist
    if (sqlite3_open([[self dataBasePath] UTF8String], &dataBase) != SQLITE_OK) {
        sqlite3_close(dataBase); 
        NSAssert(0, @"Database failed to open.");
    }
}


- (void)loadDataBase {
    char *err; 
    NSMutableString *sql;
    
    // Definition of AccountPlayer table.
    sql = [NSMutableString stringWithString:@"CREATE TABLE IF NOT EXISTS AccountPlayer(id INTEGER PRIMARY KEY AUTOINCREMENT, pseudo VARCHAR(25) NOT NULL UNIQUE, userName VARCHAR(25), password VARCHAR(20), connectionAuto BOOLEAN, rememberPassword BOOLEAN, color UNSIGNED TINYINT DEFAULT 0, menuPosition UNSIGNED TINYINT DEFAULT 1, gameWon UNSIGNED INTEGER DEFAULT 0, gameLost UNSIGNED INTEGER DEFAULT 0);"];
    
    // Definition of System table.
    [sql appendString:@"CREATE TABLE IF NOT EXISTS System(volume UNSIGNED SMALLINT DEFAULT 100, mute BOOLEAN DEFAULT 0, language VARCHAR(5) DEFAULT 'en', lastUser INTEGER, FOREIGN KEY(lastUser) REFERENCES AccountPlayer(id));"];
    
    // Definition of Map table.
    [sql appendString:@"CREATE TABLE IF NOT EXISTS Map(name VARCHAR(50) NOT NULL UNIQUE, owner UNSIGNED INTEGER, official BOOLEAN, FOREIGN KEY(owner) REFERENCES AccountPlayer(id));"];
    
    if (sqlite3_exec(dataBase, [sql UTF8String], NULL, NULL, &err) != SQLITE_OK) {
        sqlite3_close(dataBase);
        NSAssert(0, @"Tabled failed to create.");
    }
    
    sqlite3_stmt *statement = [self select:@"*" from:@"System" where:nil];
    
    // if it's the first time that the application is launched, we need to add values in System table.
    if (sqlite3_step(statement) == SQLITE_DONE) {
        [self initSystemTable];
        [self initMapTable];        
    }
}


- (void)initSystemTable {
    NSString *language = [[NSLocale preferredLanguages] objectAtIndex:0];
    
    if (language == @"fr" || language == @"en") {
        [self insertInto:@"System" values:[NSString stringWithFormat:@"(100, 0, '%@', NULL)", language]];
    }
    else {
        [self insertInto:@"System" values:@"(100, 0, 'en', NULL)"];
    }
}


// TODO: Change with the true official map
- (void)initMapTable {
    NSString *extension = @".klob";
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSString *mapPath = [NSString stringWithFormat:@"%@/Maps/", [[NSBundle mainBundle] bundlePath]];
    NSArray *files = [fileManager contentsOfDirectoryAtPath:mapPath error:NULL];
    NSMutableArray *nameMap = [[NSMutableArray alloc] init];
    NSString *fileName;
    
    for (int i = 0; i < [files count]; i++) {
        fileName = (NSString *) [files objectAtIndex:i];
        
        if ([fileName hasSuffix:extension]) {
            if ([files containsObject:[NSString stringWithFormat:@"%@.png", [fileName substringToIndex:([fileName length] - [extension length])]]]) {
                [nameMap addObject:[fileName substringToIndex:([fileName length] - [extension length])]];
            }
        }
    }
    
    for (int i = 0; i < [nameMap count]; i++) {
        [self insertInto:@"Map" values:[NSString stringWithFormat:@"('%@', NULL, 1)", [nameMap objectAtIndex:i]]];
    }
    
    [nameMap release];
}


- (void)insertInto:(NSString *)table values:(NSString *)values {
    char *err; 
    NSString *sql = [NSString stringWithFormat: @"INSERT INTO %@ VALUES %@;", table, values];

    if (sqlite3_exec(dataBase, [sql UTF8String], NULL, NULL, &err) != SQLITE_OK) {
        sqlite3_close(dataBase); 
        NSAssert(0, @"Error updating table.");
    }
}


- (void)update:(NSString *)table set:(NSString *)values where:(NSString *)condition {
    char *err; 
    NSString *sql;
    
    if (condition != nil) {
        sql = [NSString stringWithFormat: @"UPDATE %@ SET %@ WHERE %@;", table, values, condition];
    }
    else {
        sql = [NSString stringWithFormat: @"UPDATE %@ SET %@;", table, values];
    }

    if (sqlite3_exec(dataBase, [sql UTF8String], NULL, NULL, &err) != SQLITE_OK) {
        sqlite3_close(dataBase); 
        NSAssert(0, @"Error updating table.");
    }
}


- (sqlite3_stmt *)select:(NSString *)attributes from:(NSString *)table where:(NSString *)condition {
    NSString *query;
    sqlite3_stmt *statement;
    
    if (condition != nil) {
        query = [NSString stringWithFormat:@"SELECT %@ FROM %@ WHERE %@;", attributes, table, condition];
    }
    else {
        query = [NSString stringWithFormat:@"SELECT %@ FROM %@;", attributes, table];
    }
    
    return (sqlite3_prepare_v2(dataBase, [query UTF8String], -1, &statement, nil) == SQLITE_OK)? statement : nil;
}


- (void)createOrUpdateMap:(DBMap *)map {
    
    sqlite3_stmt *statement = [self select:@"owner" from:@"Map" where:[NSString stringWithFormat:@"name = '%@'", map.name]];
    
    if (sqlite3_step(statement) == SQLITE_ROW) {
        NSLog(@"%d == %d", sqlite3_column_int(statement, 0), map.owner.identifier);
        if (sqlite3_column_int(statement, 0) != map.owner.identifier) {
            [self update:@"Map" set:[NSString stringWithFormat:@"owner = %d", map.owner.identifier] where:[NSString stringWithFormat:@"name = '%@'", map.name]];
        }
    }
    else
        [self insertInto:@"Map" values:[NSString stringWithFormat:@"('%@', %d, 0)", map.name, map.owner.identifier]];
        
        
        sqlite3_finalize(statement);

}

@end
