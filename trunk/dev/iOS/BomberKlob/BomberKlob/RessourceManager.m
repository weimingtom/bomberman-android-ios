//
//  RessourceManager.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 04/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "RessourceManager.h"
#import "XmlParser.h"
#import "Objects.h"
#import "Position.h"
#import "Player.h"
#import "AnimationSequence.h"


@implementation RessourceManager

@synthesize bitmapsAnimates,bitmapsInanimates, bitmapsPlayer,bitmapsBombs, tileSize, screenWidth, screenHeight;

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
		[self loadPlayer];	
		[self loadBombs];
		[self loadObjects];
    }
	
	return self;
}


- (void)dealloc {
    [bitmapsAnimates release];
    [bitmapsInanimates release];
    [bitmapsPlayer release];
    [bitmapsBombs release];
    [super dealloc];
}


- (void) loadProperty{
	//tileSize = ([[UIScreen mainScreen] bounds].size.width*0.88) / 13;
	tileSize = ([[UIScreen mainScreen] bounds].size.height*0.90) / 21;
	screenHeight = [[UIScreen mainScreen] bounds].size.height;
	screenWidth = [[UIScreen mainScreen] bounds].size.width ;
}


- (void) loadPlayer{
	
	bitmapsPlayer = [[NSMutableDictionary alloc] init];
	
	
	NSError * erreur = nil;
//	NSString * contenu = [NSString stringWithContentsOfFile:@"/Users/choucomog/bomberman-android-ios/dev/iOS/BomberKlob/BomberKlob/Resources/xml/players.xml"
//												   encoding:NSUTF8StringEncoding
//													  error:&erreur]; // On donne l'adresse de "erreur"
    NSString *xmlFilePath =[[NSBundle mainBundle] pathForResource:@"players" ofType:@"xml"];
    NSString * contenu = [NSString stringWithContentsOfFile:xmlFilePath encoding:NSUTF8StringEncoding error:&erreur]; // On donne l'adresse de "erreur"
	
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
	
	
	bitmapsPlayer = parserDelegate.objectsAnimations;
}

- (void) loadBombs{
	
	bitmapsBombs = [[NSMutableDictionary alloc] init];
	
	
	NSError * erreur = nil;
    NSString *xmlFilePath =[[NSBundle mainBundle] pathForResource:@"bombs" ofType:@"xml"];
    NSString * contenu = [NSString stringWithContentsOfFile:xmlFilePath encoding:NSUTF8StringEncoding error:&erreur]; // On donne l'adresse de "erreur"
	
	NSXMLParser * parseur = [[NSXMLParser alloc] initWithData:[contenu dataUsingEncoding:NSUTF8StringEncoding]];
	XmlParser * parserDelegate = [[XmlParser alloc] initXMLParser:@"bombs"];	
	[parseur setDelegate:parserDelegate];
	[parseur parse];
	
	NSError *parseError = [parseur parserError];
	if (parseError) {
		NSLog(@"XmlParser - Error parsing data: %@", [parseError localizedDescription]);
	} 
	[erreur release];
	[parseError release];
	
	bitmapsBombs = parserDelegate.objectsBombs;
}

- (void) loadObjects{
	
	bitmapsAnimates = [[NSMutableDictionary alloc] init];
	
	
	NSError * erreur = nil;
    NSString *xmlFilePath =[[NSBundle mainBundle] pathForResource:@"objects" ofType:@"xml"];
    NSString * contenu = [NSString stringWithContentsOfFile:xmlFilePath encoding:NSUTF8StringEncoding error:&erreur]; // On donne l'adresse de "erreur"
	
	NSXMLParser * parseur = [[NSXMLParser alloc] initWithData:[contenu dataUsingEncoding:NSUTF8StringEncoding]];
	XmlParser * parserDelegate = [[XmlParser alloc] initXMLParser:@"objects"];	
	[parseur setDelegate:parserDelegate];
	[parseur parse];
	
	NSError *parseError = [parseur parserError];
	if (parseError) {
		NSLog(@"XmlParser - Error parsing data: %@", [parseError localizedDescription]);
	} 
	[erreur release];
	[parseError release];
	
	bitmapsAnimates = parserDelegate.objects;
	
}


- (void) update{

	
}


- (void) hasAnimationFinished{
	
}


- (void) destroy{
	
}

@end
