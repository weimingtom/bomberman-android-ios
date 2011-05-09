#import <Foundation/Foundation.h>

#define WIDTH  21
#define HEIGHT 13

@interface Map : NSObject {
    
    NSString *name;
    NSInteger width;
    NSInteger height;
    NSMutableDictionary *players;
}

@property (nonatomic, retain) NSString *name;
@property (nonatomic, assign) NSInteger width;
@property (nonatomic, assign) NSInteger height;
@property (nonatomic, retain) NSMutableDictionary *players;

@end
