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
#import "Single.h"

@implementation GameInformationView
@synthesize controller;

- (id) initWithFrame:(CGRect)frame Controller:(GameInformationViewController *) controllerValue{
	self = [super initWithFrame:frame];
	
	if (self){
		self.controller = controllerValue;
		[self initComponents];
		[self setBackgroundColor:[UIColor colorWithPatternImage:[UIImage imageNamed:@"gametoolbarbackground.png"]]];
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	RessourceManager * resource = [RessourceManager sharedRessource];
	CGContextRef context = UIGraphicsGetCurrentContext();
	int i = 0;
	int ecart = resource.screenHeight / 4;
	for (NSString * key in resource.bitmapsPlayer) {
		if (i == 2) {
			if ([[[controller.globalController.engine.game class]description]isEqualToString:@"Single"]) {
				NSMutableString * temps = @"Temps \n ";
				if (((Single *)controller.globalController.engine.game).time != NULL) {
					[temps stringByAppendingString:((Single *)controller.globalController.engine.game).time];
				}
				CGContextSetFillColorWithColor(context, [UIColor whiteColor].CGColor);
				UIFont * font = [UIFont boldSystemFontOfSize:11.0];
				CGSize size = [temps sizeWithFont:font];
				[temps drawInRect:CGRectMake(ecart, 0, size.width , size.height) withFont:font];
				ecart += size.width;
			}
		}
		
		Player * player = [resource.bitmapsPlayer valueForKey:key];
		UIImage * image = [player.png objectForKey:@"idle"];
		[image drawInRect:CGRectMake(ecart, 0, image.size.width , image.size.height)];
		
		Game * game = controller.globalController.engine.game;
		
		ecart+= image.size.width;
		i++;
	}
	ecart += 50;
	for (NSString * imageName in resource.bitmapsInformationGameView) {
		UIImage * image = [resource.bitmapsInformationGameView objectForKey:imageName];
		[image drawInRect:CGRectMake(ecart, 0, image.size.width, image.size.height)];
		ecart += image.size.width;
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
