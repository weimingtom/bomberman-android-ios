#import <Foundation/Foundation.h>

@class DataBase, DBUser;


/** The `DBSystem` class aims to manage the general information of application. */
@interface DBSystem : NSObject {
    
    DataBase *dataBase;
    
    NSUInteger volume;
    BOOL mute;
    NSString *language;
    DBUser *lastUser;
}

/** The database volume. */
@property (nonatomic) NSUInteger volume;

/** The mute variable of database. */
@property (nonatomic) BOOL mute;

/** The prefered language of application. */
@property (nonatomic, retain) NSString *language;

/** The last user of application */
@property (nonatomic, retain) DBUser *lastUser;


///-------------------------------------
/// @name Initializing a DBSystem Object
///-------------------------------------

/** Initializes and returns a newly allocated `DBSystem` object with the specified volume, language and last user.
 
 @param aVolume The application volume.
 @param aLanguage The application language.
 @param anUser The last user of application.
 @return A newly allocated `DBSystem`object with the specified volume, language and last user.
 */
- (id)initWithVolume:(NSUInteger)aVolume mute:(BOOL)aMute language:(NSString *)aLanguage lastUser:(DBUser *)anUser;


///-----------------------
/// @name Updating Methods
///-----------------------

/** Update the database volume. */
- (void)updateVolume;

/** Update the mute variable of database. */
- (void)updateMute;

/** Update the prefered language of application.  */
- (void)updateLanguage;

/** Update the last user of application. */
- (void)updateLastUser;

@end
