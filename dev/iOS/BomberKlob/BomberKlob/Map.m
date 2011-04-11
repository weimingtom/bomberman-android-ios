//
//  Map.m
//  BombermanIOS
//
//  Created by Kilian Coubo on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Map.h"
#import "RessourceManager.h"

@implementation Map
@synthesize grounds, blocks;

- (id) init{
	self = [super init];
	
	if (self) {
		ressource = [RessourceManager sharedRessource];
		bitmapsInanimates = ressource.bitmapsInanimates;
		
		name = @"Test";
		
		grounds = [[NSMutableArray alloc] init];
		blocks = [[NSMutableArray alloc]init];
		
		for (int i = 0; i < 15; i++){
			[grounds addObject:[[NSMutableArray alloc]init]] ;
			[blocks addObject:[[NSMutableArray alloc]init]] ;
			
			for (int j=0; j < 15; j++) {
				[[grounds objectAtIndex:i] addObject:[NSNumber numberWithInt:1]];

				
				if (i == 4 && j == 4)
					[[grounds objectAtIndex:i] addObject:[NSNumber numberWithInt:2]];
				
				if (i == 4 && j == 10)
					[[grounds objectAtIndex:i] addObject:[NSNumber numberWithInt:3]];
				
				if (i == 10 && j == 4)
					[[grounds objectAtIndex:i] addObject:[NSNumber numberWithInt:4]];
				
				if (i == 10 && j == 10)
					[[grounds objectAtIndex:i] addObject:[NSNumber numberWithInt:5]];

			}
		}
	}

	return self;
}

- (void) save{
	
}


- (void) load:(NSString*)path{
	
}


- (void) addGround:(NSInteger) ground{
	
}


- (void) addBlock:(NSInteger) block{
	
}


- (void) deleteGround: (NSInteger) ground{
	
}


- (void) deleteBlock: (NSInteger)block{
	
}


- (void) destroyBlock: (NSInteger)block{
	
}


- (void) draw:(CGContextRef)context{
	CGImageRef image;
	
	for (int i=0; i < 15; i++) {
		for (int j= 0; j < 15; j++){
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 0){
				image = [bitmapsInanimates valueForKey:@"wbloc"];
				CGContextDrawImage(context, CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight ), image);
			}
			
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 1){
				image = [bitmapsInanimates valueForKey:@"grass"];
				CGContextDrawImage(context, CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight ), image);
			}
			
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 2){
				image = [bitmapsInanimates valueForKey:@"ground"];
				CGContextDrawImage(context, CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight ), image);
			}
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 3){
				image = [bitmapsInanimates valueForKey:@"snow"];
				CGContextDrawImage(context, CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight ), image);
			}
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 4){
				image = [bitmapsInanimates valueForKey:@"bloc"];
				CGContextDrawImage(context, CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight ), image);
			}
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 5){
				image = [bitmapsInanimates valueForKey:@"other"];
				CGContextDrawImage(context, CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight ), image);
			}
			
		}
	}
}

@end
