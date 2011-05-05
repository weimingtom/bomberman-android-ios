//
//  XmlParser.m
//  BomberKlob
//
//  Created by Kilian Coubo on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "XmlParser.h"
#import "Objects.h"
#import "Position.h"
#import "Player.h"
#import "AnimationSequence.h"
#import "Undestructible.h"
#import "Destructible.h"
#import "Player.h"
#import "Bomb.h"

@implementation XmlParser

@synthesize objectsBombs, objects, objectsAnimations, objectsInanimates, objectsIdle;


- (XmlParser *) initXMLParser:(NSString *) typeValue {
	self = [super init];
    
	if (self){
		type = typeValue;
	}
    
	return self;	
}


- (void)dealloc {
    [type release];
    [currentString release];
    [currentAnimation release];
    [characters release];
    
    [objectsAnimations release];
    [objectsBombs release];
    [objects release];
    [objectsInanimates release];
    [objectsIdle release];
    
    [currentProperty release];
    [super dealloc];
}


- (void)parserDidStartDocument:(NSXMLParser *)parser {
    objectsInanimates = [[NSMutableDictionary alloc] init];
    objectsAnimations = [[NSMutableDictionary alloc] init];
    objectsBombs = [[NSMutableDictionary alloc] init];
    objects = [[NSMutableDictionary alloc] init];
    currentProperty = [[NSMutableString alloc] init];
}


- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
    
	if (type == @"player") {
		UIImage* imageRef = [UIImage imageNamed:@"players.png"];

		if([elementName isEqualToString:@"player"]) {
            Player *player = [[Player alloc] init];
			currentProperty = [attributeDict valueForKey:@"name"];
			[objectsAnimations setObject:player forKey:currentProperty];
            [player release];
		}
		else if([elementName isEqualToString:@"animation"]) {
			currentCanLoop = [[attributeDict valueForKey:@"canLoop"] intValue];
			currentAnimation = [attributeDict valueForKey:@"name"];
			
			AnimationSequence *sequences = [[AnimationSequence alloc] initWithLoop:currentCanLoop];
			AnimationSequence *destroySequences = [[AnimationSequence alloc] initWithLoop:currentCanLoop];

			[((Player *)[objectsAnimations objectForKey:currentProperty]).animations setObject:sequences forKey:currentAnimation];
			[((Player *)[objectsAnimations objectForKey:currentProperty]).destroyAnimations setObject:destroySequences forKey:currentAnimation];
            
            [sequences release];
			[destroySequences release];
		}
		else if ([elementName isEqualToString:@"framerect"]) {
			Player *currentObject = [objectsAnimations objectForKey:currentProperty];
			NSInteger x = [[attributeDict valueForKey:@"left"] integerValue];
			NSInteger y = [[attributeDict valueForKey:@"top"] integerValue];
			NSInteger width = [[attributeDict valueForKey:@"right"] integerValue] - x;
			NSInteger height = [[attributeDict valueForKey:@"bottom"] integerValue] - y;
			
			
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(x,y,width , height))];
			
			if ([currentAnimation isEqual:@"idle"]){
				currentObject.idle = imageRef;
				[(AnimationSequence *)[currentObject.animations objectForKey:currentAnimation] addImageSequence:imageRef];
			}
			else if ([currentAnimation isEqual:@"destroy"]){
				currentDelayNextFrame = [[attributeDict valueForKey:@"delayNextFrame"] integerValue];
				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentAnimation] setDelayNextFrame:currentDelayNextFrame];
				currentObject.imageName = currentAnimation;
				[(AnimationSequence *)[currentObject.destroyAnimations objectForKey:currentAnimation] addImageSequence:imageRef];
			}
			else {
				currentDelayNextFrame = [[attributeDict valueForKey:@"delayNextFrame"] integerValue];
				[(AnimationSequence *)[currentObject.animations objectForKey:currentAnimation] setDelayNextFrame:currentDelayNextFrame];
				currentObject.imageName = currentAnimation;
				[(AnimationSequence *)[currentObject.animations objectForKey:currentAnimation] addImageSequence:imageRef];
			}
            
            [imageRef release];

			return;
		}        
	}
	else if(type == @"bombs") {
		UIImage* imageRef = [UIImage imageNamed:@"bombs.png"];
		

		if([elementName isEqualToString:@"bomb"]){
			currentProperty = [attributeDict valueForKey:@"name"];
			Bomb * bomb = [[Bomb alloc] init];
			[objectsBombs setObject:bomb forKey:currentProperty];
			AnimationSequence * sequences = [[AnimationSequence alloc] init];
			AnimationSequence * destroySequences = [[AnimationSequence alloc] init];
			[bomb.animations setObject:sequences forKey:currentProperty];
			[bomb.destroyAnimations setObject:destroySequences forKey:currentProperty];

			bomb.imageName = currentProperty;
            
            [sequences release];
			[destroySequences release];
		}
		else if([elementName isEqualToString:@"animation"]){
			currentString = [attributeDict valueForKey:@"name"];
			currentCanLoop = [[attributeDict valueForKey:@"canLoop"] intValue];
			currentSound = [attributeDict valueForKey:@"sound"];
			
			NSError *error;
			NSString *pathMenuSound = [[NSBundle mainBundle] pathForResource:currentSound ofType:@"wav"];
			if ([[NSFileManager defaultManager] fileExistsAtPath:pathMenuSound]) {
				if ([currentString isEqual:@"animate"]) {
					AVAudioPlayer * sound = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath:pathMenuSound] error:&error];
					if (!sound) 
						NSLog(@"Error: %@", [error localizedDescription]);
										[sound prepareToPlay];
					[sound setNumberOfLoops:0];
					sound.volume = 1;
					((AnimationSequence *)[((Objects *)[objectsBombs objectForKey:currentProperty]).animations objectForKey:currentProperty]).sound = sound;
				}
				else if ([currentString isEqual:@"destroy"]) {
					AVAudioPlayer * sound = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath:pathMenuSound] error:&error];
					if (!sound) 
						NSLog(@"Error: %@", [error localizedDescription]);
					[sound prepareToPlay];
					[sound setNumberOfLoops:0];
					sound.volume = 1;
					((AnimationSequence *)[((Objects *)[objectsBombs objectForKey:currentProperty]).destroyAnimations objectForKey:currentProperty]).sound = sound;
				}
			}

		}
		else if ([elementName isEqualToString:@"framerect"]) {
			Bomb * currentObject = [objectsBombs objectForKey:currentProperty];
			NSInteger x = [[attributeDict valueForKey:@"left"] integerValue];
			NSInteger y = [[attributeDict valueForKey:@"top"] integerValue];
			NSInteger width = [[attributeDict valueForKey:@"right"] integerValue] - x;
			NSInteger height = [[attributeDict valueForKey:@"bottom"] integerValue] - y;
			
			
			
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(x, y,width , height))];
			
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
            
            [imageRef release];
            
			return;
		}
	}
	else if (type == @"objects"){
		UIImage* imageRef = [UIImage imageNamed:@"objects.png"];
 		
		if([elementName isEqualToString:@"destructible"] || [elementName isEqualToString:@"undestructible"]){
			currentProperty = [attributeDict valueForKey:@"name"];
			Objects *currentObject = nil;
            
			if ([elementName isEqualToString:@"destructible"]) {
				currentObject = [[Destructible alloc] init];
            }
			else if ([elementName isEqualToString:@"undestructible"]) {
				currentObject = [[Undestructible alloc] init];
            }
            
			if ([currentProperty rangeOfString:@"fire"].location != NSNotFound)
				currentObject.destroyable = YES;

			currentObject.imageName = currentProperty; 
			currentObject.hit = [[attributeDict valueForKey:@"hit"] integerValue];
			currentObject.level = [[attributeDict valueForKey:@"level"] integerValue];
			currentObject.fireWall = [[attributeDict valueForKey:@"fireWall"] integerValue];
            
			if ([elementName isEqualToString:@"destructible"])
				((Destructible *)currentObject).life = [[attributeDict valueForKey:@"life"] integerValue];
            
			currentObject.damage = [[attributeDict valueForKey:@"damages"] integerValue];

			AnimationSequence * sequences = [[AnimationSequence alloc] init];
			AnimationSequence * destroySequences = [[AnimationSequence alloc] init];
			[currentObject.animations setObject:sequences forKey:currentProperty];
			[currentObject.destroyAnimations setObject:destroySequences forKey:currentProperty];
			[objects setObject:currentObject forKey:currentProperty];
			
            [currentObject release];
            [sequences release];
            [destroySequences release];
		}
		else if([elementName isEqualToString:@"animation"]) {
			currentString = [attributeDict valueForKey:@"name"];
			currentCanLoop = [[attributeDict valueForKey:@"canLoop"] intValue];
			currentSound = [attributeDict valueForKey:@"sound"];
			
			NSError *error;
			NSString *pathMenuSound = [[NSBundle mainBundle] pathForResource:currentSound ofType:@"wav"];
			if ([[NSFileManager defaultManager] fileExistsAtPath:pathMenuSound]) {
				if ([currentString isEqual:@"destroy"] && ![currentSound isEqualToString:@""]) {
					AVAudioPlayer * sound = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath:pathMenuSound] error:&error];
					if (!sound) 
						NSLog(@"Error: %@", [error localizedDescription]);
					[sound prepareToPlay];
					[sound setNumberOfLoops:0];
					sound.volume = 1;
					((AnimationSequence *)[((Objects *)[objects objectForKey:currentProperty]).destroyAnimations objectForKey:currentProperty]).sound = sound;
				}
			}
		}
		else if ([elementName isEqualToString:@"framerect"]) {
			Objects * currentObject = [objects objectForKey:currentProperty];
			NSInteger x = [[attributeDict valueForKey:@"left"] integerValue];
			NSInteger y = [[attributeDict valueForKey:@"top"] integerValue];
			NSInteger width = [[attributeDict valueForKey:@"right"] integerValue] - x;
			NSInteger height = [[attributeDict valueForKey:@"bottom"] integerValue] - y;
			imageRef = [[UIImage alloc] initWithCGImage:CGImageCreateWithImageInRect(imageRef.CGImage, CGRectMake(x, y,width , height))];
			
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
			[imageRef release];
            
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
