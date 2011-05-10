#import <Foundation/Foundation.h>

@class Objects;

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

@property (nonatomic, retain) NSMutableDictionary *players;
@property (nonatomic, retain) NSMutableDictionary *objectsBombs;
@property (nonatomic, retain) NSMutableDictionary *objects;
@property (nonatomic, retain) NSMutableDictionary *objectsIdle;

- (XmlParser *) initXMLParser:(NSString *) typeValue;

@end
