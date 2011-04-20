#import <Foundation/Foundation.h>
#import "sqlite3.h"

@class DBMap;


/** The `DataBase` class is a singleton, it manages the database allowing to save all required informations to operation of the application. */
@interface DataBase : NSObject {
    
@private
    sqlite3 *dataBase;
}

///------------------------------------
/// @name Getting the DataBase Instance
///------------------------------------

/** Return the application's database.
 
 @return The application's database.
 */
+ (DataBase *)sharedDataBase;


///---------------------------------------
/// @name Loading and Opening the DataBase
///---------------------------------------

/** Opens dataBase if it exists otherwise it creates it. */
- (void)openDataBase;

/** Loads all database table, if they don't exist, it creates them and adds required data to operation of the application. */
- (void)loadDataBase;


///----------------------------
/// @name Initializing DataBase
///----------------------------

/** Initializes the `System` table of `DataBase` (ie add the required values  to `System` table). */
- (void)initSystemTable;

/** Initializes the `Map` table of `DataBase` (ie add the required values to `Map` table). */
- (void)initMapTable;


///------------------------------------
/// @name Managing Data of the DataBase
///------------------------------------

/** Inserts `values` into `table`.
 
 @param table The table where will be inserted data.
 @param values The values that will be added into `table`. 
 @see update:set:where:
 @see createOrUpdateMap:
 */
- (void)insertInto:(NSString *)table values:(NSString *)values;

/** Updates the `table` with the values for the data which they respond of this conditions.
 
 @param table The table which will be updated with the new `values`.
 @param values New values that will replace the old values of `table`.
 @param condition The condition for updates values.
 @see insertInto:values:
 @see createOrUpdateMap:
 */
- (void)update:(NSString *)table set:(NSString *)values where:(NSString *)condition;

/** Returns a result set of records of `table` that respond to the `condition`.
 
 @param attributes The attributes that will be returned.
 @param table The table where will be sought the datas.
 @param condition The conditions of result.
 @return The datas which responding to the request.
 */
- (sqlite3_stmt *)select:(NSString *)attributes from:(NSString *)table where:(NSString *)condition;

/** It creates map if it doesn't exist in database, otherwise updates the map.
 
 @param map The map which will be created or updated.
 @see insertInto:values:
 @see update:set:where:
 */
- (void)createOrUpdateMap:(DBMap *)map;

@end
