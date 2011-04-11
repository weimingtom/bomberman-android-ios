//
//  Map.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class User;


@interface DBMap : NSObject {
    
    NSString *name;
    User *owner;
    BOOL official;
}

@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) User *owner;
@property (nonatomic) BOOL official;

- (id)initWithName:(NSString *)aName owner:(NSUInteger)anOwner official:(BOOL)anOfficial;
- (void)dealloc;

- (void)saveInDataBase;

@end
