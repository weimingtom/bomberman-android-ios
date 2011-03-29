//
//  System.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 29/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface System : NSObject {
    
    NSInteger volume;
    NSString *language;
    NSInteger lastUser;
}

@property (nonatomic) NSInteger volume;
@property (retain, nonatomic) NSString *language;
@property (nonatomic) NSInteger lastUser;

- (id)initWithVolume:(NSInteger)aVolume 
            language:(NSString *)aLanguage 
            lastUser:(NSInteger)alastUser;
- (void)dealloc;

@end
