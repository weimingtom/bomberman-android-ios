#import "EditorMapZoneView.h"
#import "EditorMapZone.h"
#import "EditorViewController.h"
#import "MapEditor.h"
#import "Map.h"
#import "Position.h"
#import "Undestructible.h"
#import "RessourceManager.h"


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
    [editorMapZone.editorViewController.mapEditor.map drawMapAndPlayers:context alpha:editorMapZone.alpha];
}


- (void)dealloc {
    [editorMapZone release];
    [oldTouchPosition release];
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
    self.oldTouchPosition = nil;
}

#pragma mark -

@end
