#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>

@class DataBase, DBUser, DBSystem, DBMap;


/** The `Application` class aims to manage the differents characteristic allowing to manage application. */
@interface Application : NSObject {
    
    DataBase *dataBase;
    
    DBUser *user;
    NSArray *pseudos;
    NSArray *maps;
    DBSystem *system;
    
    AVAudioPlayer *playerMenu;
    AVAudioPlayer *playerButton;
}


/** The `user` who is using application. 
 
 This property is initialized by the last user having used application, except if is the first time that application has been launched, user is `nil`.
 */
@property (nonatomic, retain) DBUser *user;

/** The array containing all pseudos of players. */
@property (nonatomic, retain) NSArray *pseudos;

/** The array containing all application's maps. 
 
 `maps` contains the official maps and the unfficial.
 */
@property (nonatomic, retain) NSArray *maps;

/** `system` contains all primitive informations of application (sound volume, language, ...). */
@property (nonatomic, retain) DBSystem *system;

// TODO: Revoir l'implémantation du son dans l'application.
@property (nonatomic, retain) AVAudioPlayer *playerMenu;
@property (nonatomic, retain) AVAudioPlayer *playerButton;


///-------------------------------------------
/// @name Loading the Application's Properties
///-------------------------------------------

/** Loads all pseudos of players. 
 
 @see loadSystem
 @see loadMaps
 */
- (void)loadPseudos;

/** Loads all primitive informations of application. 
 
 @see loadPseudos
 @see loadMaps
 */
- (void)loadSystem;

/** Loads all application's maps (the offical and unoffical). 
 
 @see loadPseudos
 @see loadSystem
 */
- (void)loadMaps;

// TODO: Revoir l'implémentation du son dans l'application.
- (void)loadAudio;


///----------------------------------------------
/// @name Managing the Application's Informations
///----------------------------------------------

/** Returns `YES` if it exists a player and `NO` otherwise.
 
 @return `YES` if it exists a player and `NO` otherwise.
 */
- (BOOL)existPlayer;

/** Checks if the `pseudo` is already used.

 @param pseudo Pseudo that will checked if it is already used.
 @return `YES` if the `pseudo` is already used by an other user, `NO` otherwise.
 */
- (BOOL)pseudoAlreadyUsed:(NSString *)pseudo;

/** Adds a new map in `Application`.
 
 @param map Map that we want add to `Application`.
 */
- (void)addMap:(DBMap *)map;


///-------------------------------------
/// @name Information on Unofficial Maps
///-------------------------------------

/** Returns all unofficial maps.
 
 @return All unofficial maps.
 */
- (NSArray *)unofficialMaps;

/** Returns `YES`if exist unofficial maps, otherwise `No`.
 
 @return `YES` if it exist unofficial maps, otherwise `NO`.
 */
- (BOOL)hasUnofficialMaps;

- (void)playSoundMenu;
- (void)playSoundButton;
- (void)modifyVolume:(NSInteger)newVolume;
- (void)modifyMute:(BOOL)newMute;


- (void)setUser:(DBUser *)value;

@end
