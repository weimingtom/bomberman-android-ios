//
//  Map.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class User;


@interface Map : NSObject {
    
    NSString *name;
    User *owner;
    BOOL official;
}

@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) User *owner;
@property (nonatomic) BOOL official;

- (id)init;
- (void)dealloc;

- (void)saveInDataBase;

@end
