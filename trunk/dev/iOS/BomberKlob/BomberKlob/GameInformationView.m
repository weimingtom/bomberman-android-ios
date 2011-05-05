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
#import "Player.h"
#import "Game.h"
#import "GameInformationViewController.h"
#import "GlobalGameViewControllerSingle.h"


@implementation GameInformationView
@synthesize controller;

- (id) initWithFrame:(CGRect)frame Controller:(GameInformationViewController *) controllerValue{
	self = [super initWithFrame:frame];
	
	if (self){
		self.controller = controllerValue;
		[self initComponents];
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	RessourceManager * resource = [RessourceManager sharedRessource];
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextSetFillColorWithColor(context, [UIColor blueColor].CGColor);
	CGContextFillRect(context, rect);

	CGContextSetFillColorWithColor(context, [UIColor whiteColor].CGColor);
	int i = 0;
	int ecart = resource.screenHeight / 4;
	for (NSString * key in resource.bitmapsPlayer) {
		
//		Objects * o = [resource.bitmapsPlayer valueForKey:key];
		UIImage * image = ((Objects *)[resource.bitmapsPlayer valueForKey:key]).idle;
		[image drawInRect:CGRectMake(ecart, 0, resource.tileSize , resource.tileSize)];
		
//		Player * p = [controller.globalController.engine.game.players objectAtIndex:i];
//		Game * game = controller.globalController.engine.game;
//		NSString *score = [NSString stringWithFormat:@"%d", p.lifeNumber];
		
//		UIFont * font = [UIFont boldSystemFontOfSize:9.0];
//		[score drawInRect:CGRectMake(ecart+(resource.tileSize/2)-2, resource.tileSize, resource.tileSize, resource.tileSize) withFont:font];
		ecart+= 50;
		i++;
	}
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	
}
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	
}
- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event{
	
}
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event{
	
}

- (void) initComponents{	
	
	UIButton *  bombButton = [UIButton  buttonWithType:UIButtonTypeRoundedRect];
	bombButton.frame = CGRectMake(0,0, 50, 20);
	[bombButton setTitle:@"Menu" forState:UIControlStateNormal];
	[bombButton addTarget:self action:@selector(pauseAction) forControlEvents:UIControlEventTouchUpInside];	
	[self addSubview:bombButton];
}

- (void)pauseAction {
    [controller.globalController pauseAction];
}


@end
