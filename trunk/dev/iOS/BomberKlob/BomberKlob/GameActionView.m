//
//  GameActionView.m
//  BomberKlob
//
//  Created by Kilian Coubo on 13/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GameActionView.h"
#import "Engine.h"
#import "Game.h"
#import "Player.h"
#import "Bomb.h"
#import "Position.h"
#import "RessourceManager.h"
#import "Map.h"
#import "GameActionViewController.h"
#import "GlobalGameViewControllerSingle.h"
#import "AnimationSequence.h"
#import "ItemMenu.h"
#import "Objects.h"


@implementation GameActionView
@synthesize controller;

- (id) initWithFrame:(CGRect)frame Controller:(GameActionViewController *) controllerValue{
	self = [super initWithFrame:frame];
	
	if (self){
		self.controller = controllerValue;
		[self initComponents];
		[self setBackgroundColor:[UIColor colorWithPatternImage:[UIImage imageNamed:@"gametoolbarbackground.png"]]];
	}
	
	return self;
}

- (void)dealloc {
	[controller release];
    [super dealloc];
}


- (void) initComponents{
	RessourceManager * resource = [RessourceManager sharedRessource];
	NSMutableArray * items = [[NSMutableArray alloc] init];
	NSMutableArray * images = [[NSMutableArray alloc] init];
	NSMutableDictionary * bombsTypes = [controller getHumanPlayer].bombsTypes;
	for (NSString * key in bombsTypes) {
		Bomb * bomb = [bombsTypes objectForKey:key];
		[items addObject:bomb];
		[images addObject:bomb.idle];
	}

	int x = 0;
	int y =self.frame.size.height/4;
	int width = self.frame.size.width ;
	int height =  self.frame.size.height;
	ItemMenu * bombsMenu = [[ItemMenu alloc] initWithFrame:CGRectMake(x, y, width,height/4) horizontal:NO imageWidth:self.frame.size.width/2 imageHeight:self.frame.size.width/2 imageMargin:5 reductionPercentage:15 items:items images:images];
	[self addSubview:bombsMenu];

	UIImage * bombButtonBackground = [UIImage imageNamed:@"bomb_button.png"];
	UIButton *  bombButton = [UIButton  buttonWithType:UIButtonTypeRoundedRect];
	bombButton.frame = CGRectMake(0,self.bounds.size.height-(self.frame.size.width), self.frame.size.width, self.frame.size.width);
	[bombButton setBackgroundImage:bombButtonBackground forState:UIControlStateNormal];
	[bombButton addTarget:self action:@selector(bombButtonClicked) forControlEvents:UIControlEventTouchUpInside];
	[self addSubview:bombButton];
	
	[bombsMenu release];
	[items release];
	[images release];
}

- (void) bombButtonClicked{
	[controller plantingBomb];
}

@end
