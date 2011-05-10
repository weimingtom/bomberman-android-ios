#import "RessourceManager.h"
#import "XmlParser.h"
#import "Objects.h"
#import "Position.h"
#import "Player.h"
#import "AnimationSequence.h"
#import "Map.h"


@implementation RessourceManager

@synthesize bitmapsAnimates, bitmapsPlayer,bitmapsBombs, tileSize, screenWidth, screenHeight, bitmapsInformationGameView;

static RessourceManager * ressource = nil;

+(RessourceManager*)sharedRessource
{
	@synchronized([RessourceManager class])
	{
		if (!ressource)
			[[self alloc] init];
		
		return ressource;
	}
	
	return nil;
}


+(id)alloc
{
	@synchronized([RessourceManager class])
	{
		NSAssert(ressource == nil, @"Attempted to allocate a second instance of a singleton.");
		ressource = [super alloc];
		return ressource;
	}
	
	return nil;
}


-(id)init {
	self = [super init];
    
	if (self != nil) {
		[self loadProperty];
		[self loadBombs];
		[self loadPlayer];
		[self loadObjects];
		[self loadBitmapInformationGameView];
    }
	
	return self;
}


- (void)dealloc {
    [bitmapsAnimates release];
    [bitmapsPlayer release];
    [bitmapsBombs release];
    [super dealloc];
}


- (void) loadProperty{
	tileSize = ([[UIScreen mainScreen] bounds].size.width*0.85) / HEIGHT;
//	tileSize = ([[UIScreen mainScreen] bounds].size.height*0.90) / WIDTH;
	screenHeight = [[UIScreen mainScreen] bounds].size.height;
	screenWidth = [[UIScreen mainScreen] bounds].size.width ;
}


- (void) loadPlayer{	
	NSError *error = nil;
    NSString *xmlFilePath = [[NSBundle mainBundle] pathForResource:@"players" ofType:@"xml"];
    NSString *content = [[NSString alloc] initWithContentsOfFile:xmlFilePath encoding:NSUTF8StringEncoding error:&error];
	
	NSXMLParser *parser = [[NSXMLParser alloc] initWithData:[content dataUsingEncoding:NSUTF8StringEncoding]];
	XmlParser *parserDelegate = [[XmlParser alloc] initXMLParser:@"player"];	
	[parser setDelegate:parserDelegate];
	[parser parse];
	
	NSError *parseError = [parser parserError];
	if (parseError) {
		NSLog(@"XmlParser - Error parsing data: %@", [parseError localizedDescription]);
	} 
	
	self.bitmapsPlayer = parserDelegate.players;
    
    [content release];
	[parser release];
//	[parserDelegate release];
}


- (void) loadBombs{
	NSError *error = nil;
    NSString *xmlFilePath =[[NSBundle mainBundle] pathForResource:@"bombs" ofType:@"xml"];
    NSString *content = [[NSString alloc] initWithContentsOfFile:xmlFilePath encoding:NSUTF8StringEncoding error:&error];
	
	NSXMLParser *parser = [[NSXMLParser alloc] initWithData:[content dataUsingEncoding:NSUTF8StringEncoding]];
	XmlParser *parserDelegate = [[XmlParser alloc] initXMLParser:@"bombs"];	
	[parser setDelegate:parserDelegate];
	[parser parse];
	
	NSError *parseError = [parser parserError];
	if (parseError) {
		NSLog(@"XmlParser - Error parsing data: %@", [parseError localizedDescription]);
	} 
	
	self.bitmapsBombs = parserDelegate.objectsBombs;
    
    [content release];
	[parser release];
//	[parserDelegate release];
}


- (void) loadObjects{
	NSError *error = nil;
    NSString *xmlFilePath =[[NSBundle mainBundle] pathForResource:@"objects" ofType:@"xml"];
    NSString *content = [[NSString alloc] initWithContentsOfFile:xmlFilePath encoding:NSUTF8StringEncoding error:&error];
	
	NSXMLParser *parser = [[NSXMLParser alloc] initWithData:[content dataUsingEncoding:NSUTF8StringEncoding]];
	XmlParser *parserDelegate = [[XmlParser alloc] initXMLParser:@"objects"];	
	[parser setDelegate:parserDelegate];
	[parser parse];
	
	NSError *parseError = [parser parserError];
	if (parseError) {
		NSLog(@"XmlParser - Error parsing data: %@", [parseError localizedDescription]);
	} 
	
	self.bitmapsAnimates = parserDelegate.objects;
    
    [content release];
	[parser release];
	
}

- (void) loadBitmapInformationGameView {
	bitmapsInformationGameView = [[NSMutableDictionary alloc] init];
	UIImage * image = [UIImage imageNamed:@"bombpower.png"];
	[bitmapsInformationGameView setObject:image forKey:@"bombpower"];
	image = [UIImage imageNamed:@"playerlife.png"];
	[bitmapsInformationGameView setObject:image forKey:@"playerlife"];
	image = [UIImage imageNamed:@"playerspeed.png"];
	[bitmapsInformationGameView setObject:image forKey:@"playerspeed"];
	image = [UIImage imageNamed:@"bombnumber.png"];
	[bitmapsInformationGameView setObject:image forKey:@"bombnumber"];
}


- (void) update{

	
}


- (void) hasAnimationFinished{
	
}


- (void) destroy{
	
}

@end
