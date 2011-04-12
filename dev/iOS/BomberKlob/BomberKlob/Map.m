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
		
		for (int i = 0; i < 21; i++){
			[grounds addObject:[[NSMutableArray alloc]init]] ;
			[blocks addObject:[[NSMutableArray alloc]init]] ;
			
			for (int j=0; j < 15; j++) {
				
				if (i == 4 && j== 4) {
					[[grounds objectAtIndex:i] addObject:[NSNumber numberWithInt:5]];

				}
				else
					[[grounds objectAtIndex:i] addObject:[NSNumber numberWithInt:1]];
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
	UIImage * image;
	
	for (int i=0; i < [grounds count]; i++) {
		for (int j= 0; j < [[grounds objectAtIndex:i] count]; j++){
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 0){
				image = [bitmapsInanimates valueForKey:@"wbloc"];
				[image drawInRect:CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight )];
			}
			
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 1){
				image = [bitmapsInanimates valueForKey:@"grass"];
				[image drawInRect:CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight )];
			}
			
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 2){
				image = [bitmapsInanimates valueForKey:@"ground"];
				[image drawInRect:CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight )];
			}
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 3){
				image = [bitmapsInanimates valueForKey:@"snow"];
				[image drawInRect:CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight )];
			}
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 4){
				image = [bitmapsInanimates valueForKey:@"bloc"];
				[image drawInRect:CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight )]
				;
			}
			
			if ([[[grounds objectAtIndex:i] objectAtIndex:j] intValue] == 5){
				image = [bitmapsInanimates valueForKey:@"other"];
				[image drawInRect:CGRectMake(ressource.tileWidth*i, ressource.tileHeight*j, ressource.tileWidth , ressource.tileHeight )];
			}
			
			//CGContextFillRect(context, CGRectMake(i*ressource.tileWidth, j*ressource.tileHeight, 21*ressource.tileWidth, 2));
			//CGContextFillRect(context, CGRectMake(i*ressource.tileHeight, 0, 2, 15*ressource.tileHeight));
			
		}
	}
}

@end
