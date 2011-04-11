//
//  XmlParser.m
//  BomberKlob
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "XmlParser.h"
#import "Object.h"
#import "Position.h"
#import "Player.h"
#import "AnimationSequence.h"

@implementation XmlParser

@synthesize png, animations;

- (XmlParser *) initXMLParser:(NSString *) typeValue{
	
	self = [super init];
	if (self){
		type = typeValue;
	}
	return self;
}


- (void)parserDidStartDocument:(NSXMLParser *)parser {
	if (png == nil){
		png = [[NSMutableDictionary alloc] init];
		animations = [[NSMutableDictionary alloc] init];
	}
	else {
		[png removeAllObjects];
		[animations removeAllObjects];
	}
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
	if(type == @"inanimates"){
		// Si l’élément lu correspond à un objet métier 
		if ([elementName isEqualToString:@"png"]) {
			// On crée une instance de l’objet métier et on la stocke
			// dans une variable d’instance
			Object * currentObject = [[Object alloc] init];
			// On ajoute l’objet métier à la liste de tous les objets 
			// lus par le parseur 
			// Lecture des attributs de l’élément
			currentObject.imageName = [attributeDict valueForKey:@"name"]; 
			currentObject.position.x = [[attributeDict valueForKey:@"x"] intValue];
			currentObject.position.y = [[attributeDict valueForKey:@"y"] intValue];
			currentObject.hit = [[attributeDict valueForKey:@"hit"] intValue];
			currentObject.level = [[attributeDict valueForKey:@"level"] intValue];
			currentObject.fireWall = [[attributeDict valueForKey:@"fireWall"] intValue];
						
			// Le traitement est fini pour cet élément. 
			
			
			UIImage* imageRef = [UIImage imageNamed:@"inanimate.png"];
			CGImageRef image = imageRef.CGImage;
			CGImageRef imageTemp;
			
			NSUInteger heightOfOneCase = CGImageGetHeight(image);
			NSUInteger widthOfOneCase = CGImageGetWidth(image)/6;

			imageTemp = CGImageCreateWithImageInRect(image, CGRectMake(currentObject.position.x*widthOfOneCase, currentObject.position.y, heightOfOneCase, widthOfOneCase));
			[png setObject:imageTemp forKey:currentObject.imageName];
			[currentObject release];

			
			return;
		}
		/*// Si le texte de l’élément contient une propriété de 
		 // l’objet métier, alors on prépare une variable 
		 // temporaire pour enregistrer son contenu 
		 if ([elementName isEqualToString:@"telephone"]) {
		 currentProperty = [NSMutableString string];
		 } 
		 else if ([elementName isEqualToString@"adresse"]) {
		 currentProperty = [NSMutableString string];
		 }*/
		
	}
	else if (type == @"player"){
		
		UIImage* imageRef = [UIImage imageNamed:@"player.png"];
		CGImageRef image = imageRef.CGImage;
		CGImageRef imageTemp;
		
		NSUInteger heightOfOneCase = CGImageGetHeight(image);
		NSUInteger widthOfOneCase = CGImageGetWidth(image)/24; // 24 = nombre d'image differentes dans la grande image

		
		if([elementName isEqualToString:@"animation"]){
			currentAnimation = [attributeDict valueForKey:@"name"];
			currentCanLoop = [attributeDict valueForKey:@"canLoop"];
			
			[animations setObject:[[AnimationSequence alloc] initWithNameAndLoop:currentAnimation:currentCanLoop ] forKey:currentAnimation];

		}
		
		if ([elementName isEqualToString:@"png"]) {
			
			Player * currentObject = [[Player alloc] init];

			currentObject.imageName = currentAnimation; 
			currentObject.position.x = [[attributeDict valueForKey:@"x"] intValue];
			currentObject.position.y = [[attributeDict valueForKey:@"y"] intValue];
			currentObject.waitDelay = [[attributeDict valueForKey:@"nextFrameDelay"] intValue];
			currentObject.hit = 0;
			currentObject.level = 0;
			currentObject.fireWall = 0;
			
			
			imageTemp = CGImageCreateWithImageInRect(image, CGRectMake(currentObject.position.x*widthOfOneCase, currentObject.position.y, widthOfOneCase,heightOfOneCase ));
			[[animations objectForKey:currentAnimation] addImageSequence:imageTemp];
			
			
			
			[currentObject release];
			return;
		}
	}
	}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
	
	if (currentProperty) {
		[currentProperty appendString:string];
	}
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName {

	/*if ([elementName isEqualToString:@"adresse"]) { 
		[currentContact setAdresse:currentProperty];
	}
	else if ([elementName isEqualToString:@"telephone"]) {
		[currentContact setTelephone:currentProperty];
	}*/
}

@end
