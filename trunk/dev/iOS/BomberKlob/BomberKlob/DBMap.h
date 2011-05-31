#import <Foundation/Foundation.h>

@class DBUser;


/** The `DBMap` class aims to manage the differents characteristic of maps in database. */
@interface DBMap : NSObject {
    
    NSString *name;
    DBUser *owner;
    BOOL official;
}


/** The `name` of map. */
@property (nonatomic, retain) NSString *name;

/** The `player` who create or modify the map. */
@property (nonatomic, retain) DBUser *owner;

/** `official` allows to know, if the map is official or not. 
 
 The unofficial map can't be used to play online, just the official can be used to play online. All maps create by a user are unoffical.
 */
@property (nonatomic) BOOL official;


/** Initializes and returns a newly allocated `DBMap` object with the specified name, owner and anOfficial value. 
 
 @param aName Map name.
 @param anOwner Map owner.
 @param anOfficial Define if the map is official or not.
 @return A newly allocated `DBMap` object with the specified name, owner and anOfficial value.
 */
- (id)initWithName:(NSString *)aName owner:(NSUInteger)anOwner official:(BOOL)anOfficial;

@end
