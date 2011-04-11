//
//  XmlParser.h
//  BomberKlob
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Object;

@interface XmlParser : NSXMLParser {

	NSString  * type;
	NSMutableDictionary * png;
	NSMutableString * currentProperty;
	
	NSMutableDictionary * animations;
	NSMutableString * currentAnimation;
	NSMutableString * currentCanLoop;

}

@property (nonatomic, retain)NSMutableDictionary * png;
@property (nonatomic, retain)NSMutableDictionary * animations;


- (XmlParser *) initXMLParser:(NSString *) typeValue;


@end
