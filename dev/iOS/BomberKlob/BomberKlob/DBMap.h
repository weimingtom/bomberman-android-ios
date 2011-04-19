//
//  Map.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class DBUser;


@interface DBMap : NSObject {
    
    NSString *name;
    DBUser *owner;
    BOOL official;
}

@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) DBUser *owner;
@property (nonatomic) BOOL official;

- (id)initWithName:(NSString *)aName owner:(NSUInteger)anOwner official:(BOOL)anOfficial;
- (void)dealloc;

- (void)saveInDataBase;

@end
