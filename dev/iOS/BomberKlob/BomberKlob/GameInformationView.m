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
		[self initUpdateThread];
		[self setBackgroundColor:[UIColor colorWithPatternImage:[UIImage imageNamed:@"gametoolbarbackground.png"]]];
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	RessourceManager * resource = [RessourceManager sharedRessource];
	CGContextRef context = UIGraphicsGetCurrentContext();
//	int i = 0;
	int ecart = resource.screenHeight / 4;
	UIFont * font = [UIFont boldSystemFontOfSize:11.0];
	for (int i=0; i < [controller.globalController.engine.game.players count]; i++) {
		if (i == 2) {
			if ([[[controller.globalController.engine.game class]description]isEqualToString:@"Single"]) {
				NSMutableString * temps = @"Temps:";
				NSString * time = ((Single *)controller.globalController.engine.game).time;
				CGContextSetFillColorWithColor(context, [UIColor whiteColor].CGColor);
				CGSize maxSize = [temps sizeWithFont:font];
				[temps drawInRect:CGRectMake(ecart, 0, maxSize.width , maxSize.height) withFont:font];
				CGSize size = [time sizeWithFont:font];
				if (maxSize.width < size.width)
					maxSize = size;
				[time drawInRect:CGRectMake(ecart, size.height, size.width , size.height) withFont:font];
				ecart += maxSize.width;
			}
		}
		
		Player * player = [controller.globalController.engine.game.players objectAtIndex:i];
		UIImage * image;
		if (player.istouched) {
			image = [player.png objectForKey:@"touched"];
		}
		else if (player.isKilled) {
			image = [player.png objectForKey:@"kill"];
		}
		else {
			image = [player.png objectForKey:@"idle"];
		}
				
		[image drawInRect:CGRectMake(ecart, 0, image.size.width , image.size.height)];
		ecart+= image.size.width;
	}
	ecart += 50;
	Player * player = [controller.globalController.engine.game getHumanPlayer];
	NSString * drawString;
	for (NSString * imageName in resource.bitmapsInformationGameView) {
		UIImage * image = [resource.bitmapsInformationGameView objectForKey:imageName];
		[image drawInRect:CGRectMake(ecart, 0, image.size.width, image.size.height)];
		if ([imageName isEqual:@"bombpower"]) {
			drawString = [NSString stringWithFormat:@"%d",player.powerExplosion];
			CGSize size = [drawString sizeWithFont:font];
			[drawString drawInRect:CGRectMake(ecart+image.size.width/2, image.size.height/2, size.width, size.height) withFont:font];
		}
		else if ([imageName isEqual:@"playerlife"]) {
			drawString = [NSString stringWithFormat:@"%d",player.lifeNumber];
			CGSize size = [drawString sizeWithFont:font];
			[drawString drawInRect:CGRectMake(ecart+image.size.width/2, image.size.height/2, size.width, size.height) withFont:font];
		}
		else if ([imageName isEqual:@"playerspeed"]) {
			drawString = [NSString stringWithFormat:@"%d",player.speed];
			CGSize size = [drawString sizeWithFont:font];
			[drawString drawInRect:CGRectMake(ecart+image.size.width/2, image.size.height/2, size.width, size.height) withFont:font];
		}else if ([imageName isEqual:@"bombnumber"]) {
			drawString = [NSString stringWithFormat:@"%d",player.bombNumber];
			CGSize size = [drawString sizeWithFont:font];
			[drawString drawInRect:CGRectMake(ecart+image.size.width/2, image.size.height/2, size.width, size.height) withFont:font];
		}
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

- (void) initUpdateThread {
	updateThread = [[[NSThread alloc] initWithTarget:self selector:@selector(startTimerUpdate) object:nil]autorelease]; //Create a new thread
	[updateThread start]; //start the thread
}


- (void) startTimerUpdate {
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	NSRunLoop* runLoop = [NSRunLoop currentRunLoop];
	
	[[NSTimer scheduledTimerWithTimeInterval: 0.5 target: self selector: @selector(setNeedsDisplay) userInfo:self repeats: YES] retain];	
	[runLoop run];
	[pool release];
}



@end
