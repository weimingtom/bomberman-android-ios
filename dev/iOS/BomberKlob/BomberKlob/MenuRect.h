#import <Foundation/Foundation.h>

/** The `MenuRect` class aims to redefine `CGRect` struct to an object type. */
@interface MenuRect : NSObject {
    
    NSInteger x;
    NSInteger y;
    NSInteger width;
    NSInteger height;
}

/** The coordinates of the x axis. */
@property (nonatomic, assign) NSInteger x;

/** The coordinates of the y axis. */
@property (nonatomic, assign) NSInteger y;

/** The width. */
@property (nonatomic, assign) NSInteger width;

/** The height. */
@property (nonatomic, assign) NSInteger height;


///-------------------
/// @name Initializing
///-------------------

/** Returns a new rect, initialized with coordinates, width and height.
 
 @param point The x and y coordinates.
 @param widthValue The width.
 @param heightValue The height.
 @return 
 */
- (id)initWithPoint:(CGPoint)point width:(NSInteger)widthValue height:(NSInteger)heightValue;


/** Returns a new rect, initialized with coordinates, width and height.
 
 @param xValue The x coordinates.
 @param yValue The y coordinates.
 @param widthValue The width.
 @param heightValue The height.
 @return 
 */
- (id)initWithX:(NSInteger)xValue y:(NSInteger)yValue width:(NSInteger)widthValue height:(NSInteger)heightValue;


///-----------------
/// @name Converting
///-----------------

/** Returns the CGRect conversion of current object.
 
 @return The CGRect conversion of current object.
 */
- (CGRect)getCGRect;

@end
