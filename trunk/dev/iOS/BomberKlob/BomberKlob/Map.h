#import <Foundation/Foundation.h>

#define WIDTH  21
#define HEIGHT 13

/** The `Map` class allows to represent properties of a Map. */
@interface Map : NSObject {
    
    NSString *name;
    NSInteger width;
    NSInteger height;
    NSMutableDictionary *players;
}

/** The `name` of the map.*/
@property (nonatomic, retain) NSString *name;

/** The `width` of the map in number of case.*/
@property (nonatomic, assign) NSInteger width;

/** The `height` of the map in number of case.*/
@property (nonatomic, assign) NSInteger height;

/** The initial position of the players.*/
@property (nonatomic, retain) NSMutableDictionary *players;

@end
