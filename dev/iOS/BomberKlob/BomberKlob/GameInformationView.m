//
//  GameInformationView.m
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameInformationView.h"
#import "RessourceManager.h"
#import "AnimationSequence.h"
#import "Engine.h"


@implementation GameInformationView

- (id) initWithFrame:(CGRect)frame Engine:(Engine *)engineValue{
	self = [super initWithFrame:frame];
	
	if (self){
		resource = [RessourceManager sharedRessource];
		engine = engineValue;
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextSetFillColorWithColor(context, [UIColor blueColor].CGColor);
	CGContextFillRect(context, rect);

	CGContextSetFillColorWithColor(context, [UIColor whiteColor].CGColor);
	int i = 0;
	int ecart = resource.screenHeight / 4;
	for (NSString * key in resource.bitmapsPlayer) {
		
		UIImage * image = [((AnimationSequence *)[[resource.bitmapsPlayer valueForKey:key] valueForKey:@"idle"]).sequences objectAtIndex:0];
		[image drawInRect:CGRectMake(ecart, 0, resource.tileSize , resource.tileSize)];
		
		Player * p = [engine.game.players objectAtIndex:i];
		NSString *score = [NSString stringWithFormat:@"%d", p.lifeNumber];
		
		UIFont * font = [UIFont boldSystemFontOfSize:9.0];
		[score drawInRect:CGRectMake(ecart+(resource.tileSize/2)-2, resource.tileSize, resource.tileSize, resource.tileSize) withFont:font];
		ecart+= 50;
		i++;
	}
	

	
}

@end
