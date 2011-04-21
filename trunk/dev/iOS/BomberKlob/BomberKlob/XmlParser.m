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

@synthesize png, animations,animationsBombs, animationsAnimates;

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
		animationsBombs = [[NSMutableDictionary alloc] init];
		animationsAnimates = [[NSMutableDictionary alloc] init];
		currentProperty = [[NSMutableString alloc] init];
	}
	else {
		[png removeAllObjects];
		[animations removeAllObjects];
		[animationsBombs removeAllObjects];
	}
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
	if(type == @"inanimates"){
		if ([elementName isEqualToString:@"png"]) {
			Object * currentObject = [[Object alloc] init];
			currentObject.imageName = [attributeDict valueForKey:@"name"];
            currentObject.position = [[Position alloc] initWithX:[[attributeDict valueForKey:@"x"] integerValue] y:[[attributeDict valueForKey:@"y"] integerValue]];
			currentObject.hit = [[attributeDict valueForKey:@"hit"] intValue];
			currentObject.level = [[attributeDict valueForKey:@"level"] intValue];
			currentObject.fireWall = [[attributeDict valueForKey:@"fireWall"] intValue];
						
			
			UIImage* imageRef = [UIImage imageNamed:@"inanimate.png"];
			CGImageRef image = imageRef.CGImage;
			CGImageRef imageTemp;
			
			NSUInteger heightOfOneCase = imageRef.size.height/3;
			NSUInteger widthOfOneCase = imageRef.size.width/6;
			
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(currentObject.position.x*widthOfOneCase, currentObject.position.y*heightOfOneCase, heightOfOneCase, widthOfOneCase))];

			[png setObject:[[AnimationSequence alloc] init] forKey:currentObject.imageName];
			[[png objectForKey:currentObject.imageName] addImageSequence:imageRef];
			//[currentObject release];

			
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
		
		UIImage* imageRef = [UIImage imageNamed:@"players.png"];
		CGImageRef image = imageRef.CGImage;
		
		NSUInteger heightOfOneCase = imageRef.size.height/4;
		NSUInteger widthOfOneCase = imageRef.size.width/24; // 24 = nombre d'image differentes dans la grande image

		if([elementName isEqualToString:@"player"]){
			currentProperty = [attributeDict valueForKey:@"name"];
			
			[animations setObject:[[NSMutableDictionary alloc] init] forKey:currentProperty];
			
		}
		else if([elementName isEqualToString:@"animation"]){
			currentAnimation = [attributeDict valueForKey:@"name"];
			currentCanLoop = [attributeDict valueForKey:@"canLoop"];
			
			[[animations objectForKey:currentProperty ] setObject:[[AnimationSequence alloc] initWithNameAndLoop:currentAnimation:currentCanLoop ] forKey:currentAnimation];

			
			//[animations setObject:[[AnimationSequence alloc] initWithNameAndLoop:currentAnimation:currentCanLoop ] forKey:currentAnimation];

		}
		
		else if ([elementName isEqualToString:@"png"]) {
			
			Player * currentObject = [[Player alloc] init];

			currentObject.imageName = currentAnimation; 
			currentObject.position.x = [[attributeDict valueForKey:@"x"] intValue];
			currentObject.position.y = [[attributeDict valueForKey:@"y"] intValue];
			currentObject.waitDelay = [[attributeDict valueForKey:@"nextFrameDelay"] intValue];
			currentObject.hit = 0;
			currentObject.level = 0;
			currentObject.fireWall = 0;
			
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(currentObject.position.x*widthOfOneCase, currentObject.position.y*heightOfOneCase,widthOfOneCase , heightOfOneCase))];
			[[[animations objectForKey:currentProperty ] objectForKey:currentAnimation] addImageSequence:imageRef];
			
			
			
			[currentObject release];
			return;
		}
	}
	else if(type == @"bombs") {
		
		UIImage* imageRef = [UIImage imageNamed:@"bombs.png"];
		CGImageRef image = imageRef.CGImage;
		
		NSUInteger heightOfOneCase = imageRef.size.height/6;
		NSUInteger widthOfOneCase = imageRef.size.width/3; 
		if([elementName isEqualToString:@"bombs"]){
			//animationsBombs = [[NSMutableDictionary alloc] init];
			
		}
		else if([elementName isEqualToString:@"bomb"]){
			currentProperty = [attributeDict valueForKey:@"name"];
			[animationsBombs setObject:[[AnimationSequence alloc] init] forKey:currentProperty];
			AnimationSequence * p = [animationsBombs objectForKey:currentProperty ];

			
		}		
		else if ([elementName isEqualToString:@"png"]) {
			
			Object * currentObject = [[Object alloc] init];
			
			currentObject.imageName = currentAnimation; 
			currentObject.position.x = [[attributeDict valueForKey:@"x"] intValue];
			currentObject.position.y = [[attributeDict valueForKey:@"y"] intValue];
			currentObject.hit = 0;
			currentObject.level = 0;
			currentObject.fireWall = 0;
			
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(currentObject.position.x*widthOfOneCase, currentObject.position.y*heightOfOneCase,widthOfOneCase , heightOfOneCase))];
			[[animationsBombs objectForKey:currentProperty ] addImageSequence:imageRef];
			
			[currentObject release];
			return;
		}

	}
	else if (type == @"animates"){
		UIImage* imageRef = [UIImage imageNamed:@"animate.png"];
		CGImageRef image = imageRef.CGImage;
		
		NSUInteger heightOfOneCase = imageRef.size.height/16;
		NSUInteger widthOfOneCase = imageRef.size.width/6; 
		if([elementName isEqualToString:@"destructible"] ||[elementName isEqualToString:@"undestructible"]){
			currentProperty = [attributeDict valueForKey:@"name"];
			[animationsAnimates setObject:[[AnimationSequence alloc] init] forKey:currentProperty];
			
		}		
		else if ([elementName isEqualToString:@"png"]) {
			
			Object * currentObject = [[Object alloc] init];
			
			currentObject.imageName = currentAnimation; 
			currentObject.position.x = [[attributeDict valueForKey:@"x"] intValue];
			currentObject.position.y = [[attributeDict valueForKey:@"y"] intValue];
			currentObject.hit = 0;
			currentObject.level = 0;
			currentObject.fireWall = 0;
			
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(currentObject.position.x*widthOfOneCase, currentObject.position.y*heightOfOneCase,widthOfOneCase , heightOfOneCase))];
			[[animationsAnimates objectForKey:currentProperty ] addImageSequence:imageRef];
			
			[currentObject release];
			return;
		}
	}
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
	
	if (currentString) {
		[currentString appendString:string];
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
