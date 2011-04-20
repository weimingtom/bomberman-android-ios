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

@implementation GameActionView

- (id) initWithFrame:(CGRect)frame Engine:(Engine *) engineValue{
	self = [super initWithFrame:frame];
	
	if (self){
		engine = engineValue;
		[self initComponents];
	}
	
	return self;
}

- (void)drawRect:(CGRect)rect{
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextSetFillColorWithColor(context, [UIColor purpleColor].CGColor);
	
	CGContextFillRect(context, rect);
}

- (void) initComponents{
	[UIButton buttonWithType:UIButtonTypeRoundedRect];
	UIButton *  bombButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
	bombButton.frame = CGRectMake(0,self.bounds.size.height-( self.bounds.size.height/10), self.bounds.size.width, self.bounds.size.height/10);
	
	[bombButton setTitle:@"BOOM" forState:UIControlStateNormal];
	[bombButton addTarget:self action:@selector(bombButtonClicked) forControlEvents:UIControlEventTouchUpInside];
	//[bombButton setBackgroundImage: forState:UIControlStateNormal
	[self addSubview:bombButton];
}

- (void) bombButtonClicked{
	RessourceManager * resource = [RessourceManager sharedRessource];
	Player * p = [engine.game.players objectAtIndex:0];
	NSInteger bx = (p.position.x)/resource.tileSize;
	NSInteger by = (p.position.y+resource.tileSize)/resource.tileSize;
	Position * bombPosition = [[Position alloc] initWithX:(bx*resource.tileSize) y:(by*resource.tileSize)];
	Bomb * bomb = [[Bomb alloc] initWithImageName:@"normal" position:bombPosition];
	if (![engine isInCollision:bomb :0 :0]) {
		[p.bombsPlanted addObject:bomb];
		[engine.game.map addBlock:bomb position:[[Position alloc] initWithX:bx y:by]];
	}
	
	
}

@end
