//
//  RessourceManager.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "RessourceManager.h"
#import "XmlParser.h"
#import "Object.h"
#import "Position.h"
#import "Player.h"
#import "AnimationSequence.h"


@implementation RessourceManager

@synthesize bitmapsInanimates, bitmapsPlayer, tileWidth, tileHeight;

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
		[self loadBitmapInanimates];
		[self loadBitmapPlayer];	}
	
	return self;
}


- (void) loadProperty{
	//tileHeight = [[UIScreen mainScreen] bounds].size.height / 21;
	tileWidth = [[UIScreen mainScreen] bounds].size.width / 15;
	 tileHeight = tileWidth;
}

- (void) loadBitmapInanimates{
	
	bitmapsInanimates = [[NSMutableDictionary alloc] init];

	
	NSError * erreur = nil;
//	NSString * contenu = [NSString stringWithContentsOfFile:@"/Users/choucomog/Desktop/bomberman-android-ios/dev/iOS/BomberKlob/BomberKlob/Resources/xml/inanimates.xml"
//												   encoding:NSUTF8StringEncoding
//													  error:&erreur]; // On donne l'adresse de "erreur"
    // TODO: A modifier...
    NSString *xmlFilePath =[[NSBundle mainBundle] pathForResource:@"inanimates" ofType:@"xml"];
    NSString * contenu = [NSString stringWithContentsOfFile:xmlFilePath encoding:NSUTF8StringEncoding error:&erreur]; // On donne l'adresse de "erreur"
	NSLog(@"Contenu: %@\n", contenu);
	
	NSXMLParser * parseur = [[NSXMLParser alloc] initWithData:[contenu dataUsingEncoding:NSUTF8StringEncoding]];
	XmlParser * parserDelegate = [[XmlParser alloc] initXMLParser:@"inanimates"];	
	[parseur setDelegate:parserDelegate];
	[parseur parse];
	
	NSError *parseError = [parseur parserError];
	if (parseError) {
		NSLog(@"XmlParser - Error parsing data: %@", [parseError localizedDescription]);
	} 
	[erreur release];
	[parseError release];
	
	UIImage* imageRef = [UIImage imageNamed:@"inanimate.png"];
	CGImageRef image = imageRef.CGImage;
	CGImageRef imageTemp;
	
	bitmapsInanimates = parserDelegate.png;
	
	NSLog(@"Fin du parsage");	
}

- (void) loadBitmapPlayer{
	
	bitmapsPlayer = [[NSMutableDictionary alloc] init];
	
	
	NSError * erreur = nil;
	NSString * contenu = [NSString stringWithContentsOfFile:@"/Users/choucomog/bomberman-android-ios/dev/iOS/BomberKlob/BomberKlob/Resources/xml/players.xml"
												   encoding:NSUTF8StringEncoding
													  error:&erreur]; // On donne l'adresse de "erreur"
	
	
	NSXMLParser * parseur = [[NSXMLParser alloc] initWithData:[contenu dataUsingEncoding:NSUTF8StringEncoding]];
	XmlParser * parserDelegate = [[XmlParser alloc] initXMLParser:@"player"];	
	[parseur setDelegate:parserDelegate];
	[parseur parse];
	
	NSError *parseError = [parseur parserError];
	if (parseError) {
		NSLog(@"XmlParser - Error parsing data: %@", [parseError localizedDescription]);
	} 
	[erreur release];
	[parseError release];
	
	bitmapsPlayer = parserDelegate.animations;
	
	NSLog(@"Fin du parsage");	
}

- (void) update{

	
}

- (void) hasAnimationFinished{
	
}

- (void) destroy{
	
}



@end
