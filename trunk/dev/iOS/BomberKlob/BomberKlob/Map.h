//
//  Map.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Map : NSObject {
    
    NSString *name;
    NSInteger owner;
    BOOL officiel;
}

- (id)initWithName:(NSString *)aName 
             owner:(NSInteger)anOwner 
          officiel:(BOOL)anOfficiel;
- (void)dealloc;

@end
