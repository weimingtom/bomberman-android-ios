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
	NSMutableString * currentString;
	NSMutableString * currentAnimation;
	NSMutableString * characters;
	
	NSMutableDictionary * objectsAnimations;
	NSMutableDictionary * objectsBombs;
	NSMutableDictionary * objects;
	NSMutableDictionary * objectsInanimates;
	NSMutableDictionary * objectsIdle;

	NSInteger currentDelayNextFrame;
	NSMutableString * currentCanLoop;
	NSMutableString * currentProperty;

}

@property (nonatomic, retain)NSMutableDictionary * objectsAnimations;
@property (nonatomic, retain)NSMutableDictionary * objectsBombs;
@property (nonatomic, retain)NSMutableDictionary * objects;
@property (nonatomic, retain)NSMutableDictionary * objectsInanimates;
@property (nonatomic, retain)NSMutableDictionary * objectsIdle;





- (XmlParser *) initXMLParser:(NSString *) typeValue;



@end
