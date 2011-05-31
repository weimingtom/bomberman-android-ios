#import <Foundation/Foundation.h>


/** The `Position` class aims to represent the position. 
 
 The positions are characterized by the abscissa and orderly.
 */
@interface Position : NSObject <NSCoding> {
	NSInteger x;
	NSInteger y;
}

/** The abscissa position. */
@property (nonatomic) NSInteger x;

/** The orderly postion. */
@property (nonatomic) NSInteger y;


///-------------------------------------
/// @name Initializing a Position Object
///-------------------------------------

/** Initializes and returns a newly allocated `Position` object.
 
 @see initWithX:y:
 @see initWithPosition:
 return A newly allocated `Position` object.
 */
- (id)init;

/** Initializes and returns a newly allocated `Position` object with an abscissa and orderly value.
 
 @param aX The abscissa position.
 @param aY The orderly postion.
 @return A newly allocated `Position` object with an abscissa and orderly value.
 */
- (id)initWithX:(NSInteger)aX y:(NSInteger)aY;

/** Initializes and returns a newly allocated `Position` object with a position.
 
 @param position The position.
 @return A newly allocated `Position`object with a position.
 @see init
 @see initWithX:y:
 */
- (id)initWithPosition:(Position *)position;


///--------------------
/// @name Other Methods
///--------------------

- (BOOL)isEqual:(id)object;
- (NSString *)description;


@end
