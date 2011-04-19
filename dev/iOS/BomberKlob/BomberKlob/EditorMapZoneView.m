//
//  EditorMapZoneView.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 16/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EditorMapZoneView.h"
#import "EditorMapZone.h"
#import "EditorViewController.h"
#import "MapEditor.h"
#import "Map.h"
#import "Position.h"
#import "Undestructible.h"
#import "RessourceManager.h"

// FIXME: Problème lorsque l'on change d'outils (normal -> suppression) et que l'on veut supprimer le bloc que l'on vient de poser.
@implementation EditorMapZoneView

@synthesize editorMapZone, oldTouchPosition;

- (id)initWithFrame:(CGRect)frame controller:(EditorMapZone *)myController {
    self = [super initWithFrame:frame];
    
    if (self) {
        self.editorMapZone = myController;
        oldTouchPosition = [[Position alloc] initWithX:-1 y:-1];
        tileSize = [RessourceManager sharedRessource].tileSize;
    }
    
    return self;
}


- (void)drawRect:(CGRect)rect {
    CGContextRef context = UIGraphicsGetCurrentContext();
    [editorMapZone.editorViewController.mapEditor.map draw:context];	
}


- (void)dealloc {
    [editorMapZone release];
    [super dealloc];
}


#pragma mark - Event management

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    CGPoint touchPoint = [[touches anyObject] locationInView:self];
    Position *touchPosition = [[Position alloc] initWithX:(touchPoint.x / tileSize) y:(touchPoint.y / tileSize)];
    
    if (![oldTouchPosition isEqual:touchPosition]) {
        self.oldTouchPosition = touchPosition;
        
        [editorMapZone clickOnPosition:touchPosition];
        [self setNeedsDisplay];
    }
    
    [touchPosition release];
}


- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
    CGPoint touchPoint = [[touches anyObject] locationInView:self];
    Position *touchPosition = [[Position alloc] initWithX:(touchPoint.x / tileSize) y:(touchPoint.y / tileSize)];
    
    if (![oldTouchPosition isEqual:touchPosition]) {
        self.oldTouchPosition = touchPosition;
        
        [editorMapZone clickOnPosition:touchPosition];
        [self setNeedsDisplay];
    }
    
    [touchPosition release];
}


- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
    
}

#pragma mark -

@end
