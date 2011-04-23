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
#import "Undestructible.h"
#import "Destructible.h"
#import "Player.h"
#import "Bomb.h"
@implementation XmlParser

@synthesize objectsBombs,objects,objectsAnimations,objectsInanimates, objectsIdle;

- (XmlParser *) initXMLParser:(NSString *) typeValue{
	
	self = [super init];
	if (self){
		type = typeValue;
	
	}
	return self;
	
}


- (void)parserDidStartDocument:(NSXMLParser *)parser {

		objectsInanimates = [[NSMutableDictionary alloc] init];
		objectsAnimations = [[NSMutableDictionary alloc] init];
		objectsBombs = [[NSMutableDictionary alloc] init];
		objects = [[NSMutableDictionary alloc] init];
		currentProperty = [[NSMutableString alloc] init];
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
	if (type == @"player"){
		
		UIImage* imageRef = [UIImage imageNamed:@"players.png"];
		CGImageRef image = imageRef.CGImage;
		
		NSUInteger heightOfOneCase = imageRef.size.height/4;
		NSUInteger widthOfOneCase = imageRef.size.width/24; // 24 = nombre d'image differentes dans la grande image

		if([elementName isEqualToString:@"player"]){
			currentProperty = [attributeDict valueForKey:@"name"];
			[objectsAnimations setObject:[[Player alloc] init] forKey:currentProperty];
		}
		else if([elementName isEqualToString:@"animation"]){
			currentCanLoop = [attributeDict valueForKey:@"canLoop"];
			currentAnimation = [attributeDict valueForKey:@"name"];
			
			AnimationSequence * sequences = [[AnimationSequence alloc] initWithLoop:currentCanLoop];
			AnimationSequence * destroySequences = [[AnimationSequence alloc] initWithLoop:currentCanLoop];

			[((Player *)[objectsAnimations objectForKey:currentProperty ]).animations setObject:sequences forKey:currentAnimation];
			[((Player *)[objectsAnimations objectForKey:currentProperty ]).destroyAnimations setObject:destroySequences forKey:currentAnimation];

		}
	
		else if ([elementName isEqualToString:@"png"]) {
			Player * currentObject = [objectsAnimations objectForKey:currentProperty ];
			int x = [[attributeDict valueForKey:@"x"] intValue];
			int y = [[attributeDict valueForKey:@"y"] intValue];
			
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(x*widthOfOneCase,y*heightOfOneCase,widthOfOneCase , heightOfOneCase))];
			
			if ([currentAnimation isEqual:@"idle"]){
				currentObject.idle = imageRef;
				[(AnimationSequence *)[currentObject.animations objectForKey:currentAnimation] addImageSequence:imageRef];
			}
			else if ([currentAnimation isEqual:@"destroy"]){
				currentDelayNextFrame = [attributeDict valueForKey:@"delayNextFrame"];
				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentAnimation] setDelayNextFrame:currentDelayNextFrame];
				currentObject.imageName = currentAnimation;
				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentAnimation] addImageSequence:imageRef];
			}
			else {
				currentDelayNextFrame = [attributeDict valueForKey:@"delayNextFrame"];
				[(AnimationSequence *)[currentObject.animations objectForKey:currentAnimation] setDelayNextFrame:currentDelayNextFrame];
				currentObject.imageName = currentAnimation;
				[(AnimationSequence *)[currentObject.animations objectForKey:currentAnimation] addImageSequence:imageRef];
			}

			return;
		}
	}
	else if(type == @"bombs") {
		
		UIImage* imageRef = [UIImage imageNamed:@"bombs.png"];
		CGImageRef image = imageRef.CGImage;
		
		NSInteger heightOfOneCase = imageRef.size.height/4;
		NSInteger widthOfOneCase = imageRef.size.width/4; 

		if([elementName isEqualToString:@"bomb"]){
			currentProperty = [attributeDict valueForKey:@"name"];
			Bomb * bomb = [[Bomb alloc] init];
			[objectsBombs setObject:bomb forKey:currentProperty];
			AnimationSequence * sequences = [[AnimationSequence alloc] init];
			AnimationSequence * destroySequences = [[AnimationSequence alloc] init];
			[bomb.animations setObject:sequences forKey:currentProperty];
			[bomb.destroyAnimations setObject:destroySequences forKey:currentProperty];

			bomb.imageName = currentProperty;
		}
		else if([elementName isEqualToString:@"animation"]){
			currentString = [attributeDict valueForKey:@"name"];
			currentCanLoop = [attributeDict valueForKey:@"canLoop"];
		}
		else if ([elementName isEqualToString:@"png"]) {
			
			Bomb * currentObject = [objectsBombs objectForKey:currentProperty];
			int x = [[attributeDict valueForKey:@"x"] intValue];
			int y = [[attributeDict valueForKey:@"y"] intValue];
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(x*widthOfOneCase, y*heightOfOneCase,widthOfOneCase , heightOfOneCase))];
			
			if ([currentString isEqual:@"idle"]) {
				currentObject.idle = imageRef;
			}
			else if ([currentString isEqual:@"animate"]) {
				[(AnimationSequence *)[currentObject.animations objectForKey:currentProperty] addImageSequence:imageRef];
				[(AnimationSequence *)[currentObject.animations objectForKey:currentProperty] setCanLoop:currentCanLoop];
			}
			else {
				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentProperty] addImageSequence:imageRef];
				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentProperty] setCanLoop:currentCanLoop];
			}
			return;
		}

	}
	else if (type == @"objects"){
		UIImage* imageRef = [UIImage imageNamed:@"objects.png"];
		CGImageRef image = imageRef.CGImage;
		
		NSUInteger heightOfOneCase = imageRef.size.height/17;
		NSUInteger widthOfOneCase = imageRef.size.width/6; 
		
		if([elementName isEqualToString:@"destructible"] ||[elementName isEqualToString:@"undestructible"]){
			currentProperty = [attributeDict valueForKey:@"name"];
			Object * currentObject;
			if ([elementName isEqualToString:@"destructible"])
				currentObject = [[Destructible alloc] init];
			if ([elementName isEqualToString:@"undestructible"])
				currentObject = [[Undestructible alloc] init];
			if ([currentProperty rangeOfString:@"fire"].location != NSNotFound)
				currentObject.destroy = YES;

			currentObject.imageName = currentProperty; 
			currentObject.hit = [[attributeDict valueForKey:@"hit"] intValue];
			currentObject.level = [[attributeDict valueForKey:@"level"] intValue];
			currentObject.fireWall = [[attributeDict valueForKey:@"fireWall"] intValue];
			if ([elementName isEqualToString:@"destructible"])
				((Destructible *)currentObject).life = [[attributeDict valueForKey:@"life"] intValue];
			currentObject.damages = [[attributeDict valueForKey:@"damages"] intValue];

			AnimationSequence * sequences = [[AnimationSequence alloc] init];
			AnimationSequence * destroySequences = [[AnimationSequence alloc] init];
			[currentObject.animations setObject:sequences forKey:currentProperty];
			[currentObject.destroyAnimations setObject:destroySequences forKey:currentProperty];
			[objects setObject:currentObject forKey:currentProperty];
			
		}
		if([elementName isEqualToString:@"animation"]){
			currentString = [attributeDict valueForKey:@"name"];
			currentCanLoop = [attributeDict valueForKey:@"canLoop"];
			
		}
		else if ([elementName isEqualToString:@"png"]) {
			Object * currentObject = [objects objectForKey:currentProperty];
			int x = [[attributeDict valueForKey:@"x"] intValue];
			int y = [[attributeDict valueForKey:@"y"] intValue];
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(x*widthOfOneCase, y*heightOfOneCase,widthOfOneCase , heightOfOneCase))];
			
			if ([currentString isEqual:@"idle"]) {
				currentObject.idle = imageRef;
				[(AnimationSequence *)[currentObject.animations objectForKey:currentProperty] addImageSequence:imageRef];
			}
			else if ([currentString isEqual:@"animate"]) {

				[(AnimationSequence *)[currentObject.animations objectForKey:currentProperty] addImageSequence:imageRef];
				[(AnimationSequence *)[currentObject.animations objectForKey:currentProperty] setDelayNextFrame:[[attributeDict valueForKey:@"delayNextFrame"] intValue]];
				[(AnimationSequence *)[currentObject.animations objectForKey:currentProperty] setCanLoop:currentCanLoop];

			}
			else {

				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentProperty] addImageSequence:imageRef];
				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentProperty] setDelayNextFrame:[[attributeDict valueForKey:@"delayNextFrame"] intValue]];
				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentProperty] setCanLoop:currentCanLoop];

			}

			
			return;
		}
	}
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
	
	if (characters) {
		[characters appendString:string];
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
