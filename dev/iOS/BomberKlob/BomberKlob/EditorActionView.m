#import "EditorActionView.h"
#import "EditorAction.h"
#import "RessourceManager.h"
#import "AnimationSequence.h"
#import "ItemMenu.h"
#import "Objects.h"


@implementation EditorActionView

@synthesize editorAction;

- (id)initWithFrame:(CGRect)frame controller:(EditorAction *)myController {
    self = [super initWithFrame:frame];
    
    if (self) {
        self.editorAction = myController;
        self.backgroundColor = [UIColor yellowColor];
        resource = [RessourceManager sharedRessource];

        [self initItems];
        [self buildUserInterface];
    }
    
    return self;
}

- (void)dealloc {
    [editorAction release];
    [itemsLevel0 release];
    [itemsLevel1 release];
    [shitchTool release];
    [remove release];
    [itemsLevel0 release];
    [itemsLevel1 release];
    [super dealloc];
}


- (void)initItems {
    NSMutableArray *itemsLevel0Tmp = [[NSMutableArray alloc] init];
    NSMutableArray *itemsLevel1Tmp = [[NSMutableArray alloc] init];
    
    Objects *object;
    NSEnumerator *enumerator = [resource.bitmapsAnimates keyEnumerator];
    id key;
    
    NSError *error = NULL;
    NSRegularExpression *regex = [NSRegularExpression regularExpressionWithPattern:@"^fire(a-z)*" options:NSRegularExpressionCaseInsensitive error:&error];
    NSRange rangeOfFirstMatch;
    
    while ((key = [enumerator nextObject])) {
        object = [resource.bitmapsAnimates objectForKey:key];
        rangeOfFirstMatch = [regex rangeOfFirstMatchInString:object.imageName options:0 range:NSMakeRange(0, [object.imageName length])];
        
        if (NSEqualRanges(rangeOfFirstMatch, NSMakeRange(NSNotFound, 0))) {
        
            if (object.level == 0) {
                [itemsLevel0Tmp addObject:object];
            }
            else if (object.level == 1) {
                [itemsLevel1Tmp addObject:object];
            }
        }
    }

    itemsLevel0 = [[NSArray alloc] initWithArray:[itemsLevel0Tmp sortedArrayUsingSelector:@selector(compareImageName:)]];
    itemsLevel1 = [[NSArray alloc] initWithArray:[itemsLevel1Tmp sortedArrayUsingSelector:@selector(compareImageName:)]];
    
    [itemsLevel0Tmp release];
    [itemsLevel1Tmp release];
}


- (void)buildUserInterface {
    
    shitchTool = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    shitchTool.frame = CGRectMake(SHITCH_TOOL_X, SHITCH_TOOL_Y, SHITCH_TOOL_WIDTH, SHITCH_TOOL_HEIGHT);
    [shitchTool setTitle:@"Shift" forState:UIControlStateNormal];
    [shitchTool addTarget:self action:@selector(shitchToolTypeAction) forControlEvents:UIControlEventTouchDown];
    
    remove = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    remove.frame = CGRectMake(DELETE_X, DELETE_Y, DELETE_WIDTH, DELETE_HEIGHT);
    [remove setTitle:@"X" forState:UIControlStateNormal];
    [remove addTarget:self action:@selector(removeAction) forControlEvents:UIControlEventTouchDown];

    itemMenuLevel0 = [[ItemMenu alloc] initWithFrame:CGRectMake(ITEM_MENU_X, ITEM_MENU_Y, ITEM_MENU_WIDTH, ITEM_MENU_HEIGHT) controller:editorAction imageWidth:ITEM_SIZE imageHeight:ITEM_SIZE imageMargin:MARGE_ITEM reductionPercentage:REDUCTION_PERCENTAGE items:itemsLevel0];
    itemMenuLevel1 = [[ItemMenu alloc] initWithFrame:CGRectMake(ITEM_MENU_X, ITEM_MENU_Y, ITEM_MENU_WIDTH, ITEM_MENU_HEIGHT) controller:editorAction imageWidth:ITEM_SIZE imageHeight:ITEM_SIZE imageMargin:MARGE_ITEM reductionPercentage:REDUCTION_PERCENTAGE items:itemsLevel1];
    itemMenuLevel1.alpha = 0.0;
    
    
    [self addSubview:shitchTool];
    [self addSubview:itemMenuLevel0];
    [self addSubview:itemMenuLevel1];
    [self addSubview:remove];
}


- (void)shitchToolTypeAction {
    
    [UIView beginAnimations:@"SwitchTool" context:nil];
    [UIView setAnimationDuration:0.4];
    [UIView setAnimationBeginsFromCurrentState:YES];
    
    if (editorAction.selectedObjectType == @"level0") {
        editorAction.selectedObjectType = @"level1";
        itemMenuLevel0.alpha = 0.0;
        itemMenuLevel1.alpha = 1.0;
    }
    else if (editorAction.selectedObjectType == @"level1") {
        editorAction.selectedObjectType = @"level0";
        itemMenuLevel0.alpha = 1.0;
        itemMenuLevel1.alpha = 0.0;
    }
    
    [UIView commitAnimations];
}


- (void)removeAction {
    [editorAction changeTool:@"delete"];
}

@end
