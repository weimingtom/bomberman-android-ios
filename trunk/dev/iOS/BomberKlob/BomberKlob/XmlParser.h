#import <Foundation/Foundation.h>

@class Objects;

/** The `XmlParser` class allows to parse a XML file of the game. */
@interface XmlParser : NSXMLParser <NSXMLParserDelegate> {

	NSString  *type;
	NSMutableString *currentString;
	NSMutableString *currentAnimation;
	NSMutableString *characters;
	NSMutableString *currentSound;

	
	NSMutableDictionary *players;
	NSMutableDictionary *objectsBombs;
	NSMutableDictionary *objects;
	NSMutableDictionary *objectsIdle;

	NSInteger currentDelayNextFrame;
	BOOL currentCanLoop;
	NSMutableString *currentProperty;
}

/** The Players of the game.*/
@property (nonatomic, retain) NSMutableDictionary *players;

/** The Bombs of the game.*/
@property (nonatomic, retain) NSMutableDictionary *objectsBombs;

/** The Objects of the game.*/
@property (nonatomic, retain) NSMutableDictionary *objects;

/** The Idle bitmaps of the game.*/
@property (nonatomic, retain) NSMutableDictionary *objectsIdle;


/**Allows to parse a XML's type file. 
 
 @param typeValue The XML's type object to load
 @return the Parser.
 */
- (XmlParser *) initXMLParser:(NSString *) typeValue;

@end
