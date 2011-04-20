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
	NSMutableDictionary * animationsBombs;
	NSMutableDictionary * animationsAnimates;
	NSMutableString * currentAnimation;
	NSMutableString * currentCanLoop;
	NSMutableString * currentColorPlayer;

}

@property (nonatomic, retain)NSMutableDictionary * png;
@property (nonatomic, retain)NSMutableDictionary * animations;
@property (nonatomic, retain)NSMutableDictionary * animationsBombs;
@property (nonatomic, retain)NSMutableDictionary * animationsAnimates;




- (XmlParser *) initXMLParser:(NSString *) typeValue;



@end
