#import <Foundation/Foundation.h>

@class DataBase;

/** The `DBUser` class aims to manage the differents characteristic of user in database. */
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

/** The identifier of user.
 
 All identifiers are unique.
 */
@property (readonly) NSInteger identifier;

/** The pseudo of user. */
@property (nonatomic, retain) NSString *pseudo;

/** The user name. */
@property (nonatomic, retain) NSString *userName;

/** The user password. */
@property (nonatomic, retain) NSString *password;

/** If the application must auto login the user. */
@property (nonatomic) BOOL connectionAuto;

/** If the application must remember the user password. */
@property (nonatomic) BOOL rememberPassword;

/** The user color. */
@property (nonatomic) NSInteger color;

/** The position menu. */
@property (nonatomic) NSInteger menuPosition;

/** The number of game won. */
@property (nonatomic) NSInteger gameWon;

/** The number of game lost. */
@property (nonatomic) NSInteger gameLost;


///-----------------------------------
/// @name Initializing a DBUser Object
///-----------------------------------

/** Initializes and returns a newly allocated `DBUser` object with the specified pseudo.
 
 @param aPseudo The user pseudo.
 @return A newly allocated `DBUser`object with the specified pseudo.
 */
- (id)initWithPseudo:(NSString *)aPseudo;

/** Initializes and returns a newly allocated `DBUser` object with the specified identifier.
 
 @param aId The user identifier.
 @return A newly allocated `DBUser` object with the specified identifier.
 */
- (id)initWithId:(NSInteger)aId;


///--------------------------------
/// @name Interaction with Database
///--------------------------------

/** Saves the user in database. */
- (void)saveInDataBase;

@end
