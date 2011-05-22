//
//  MultiPlayerMenuViewController.m
//  BomberKlob
//
//  Created by Kilian Coubo on 18/05/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "MultiplayerRegisterMenuViewController.h"
#import "SBJsonWriter.h"


@implementation MultiplayerRegisterMenuViewController
@synthesize done;
@synthesize userName;
@synthesize password;
@synthesize secondPassword;
@synthesize autoLogin;
@synthesize rememberPassword;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)dealloc
{
	[done release];
	[userName release];
	[password release];
	[secondPassword release];
	[autoLogin release];
	[rememberPassword release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
	[super viewDidLoad];
    
    self.title = NSLocalizedString(@"Register", @"Title of 'Register' page");
	self.navigationItem.rightBarButtonItem = done;
}

- (void)viewDidUnload
{
	[self setDone:nil];
	[self setUserName:nil];
	[self setPassword:nil];
	[self setSecondPassword:nil];
	[self setAutoLogin:nil];
	[self setRememberPassword:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}

- (IBAction)doneAction:(id)sender {
	NSError *jsonError;

	NSMutableArray * array = [[NSMutableArray alloc] init];
	[array addObject:[userName text]];
	[array addObject:[password text]];

	

	NSMutableString * requete = [[NSMutableString alloc] init];
	SBJsonWriter *writer = [[SBJsonWriter alloc] init];
	[writer appendValue:array into:requete];
	NSLog(@"test : %@",[MultiplayerRegisterMenuViewController md5:[password text]]);
	
	NSString *post = requete;
		
	NSData *postData = [post dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
	NSString *postLength = [NSString stringWithFormat:@"%d",[postData length]];
	NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
	[request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://localhost:8080/BomberklobServer/inscription"]]];
	[request setHTTPMethod:@"POST"];
	[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
	[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Current-Type"];
	[request setHTTPBody:postData];
	NSURLConnection *conn = [[NSURLConnection alloc]initWithRequest:request delegate:self];
	if (!conn) {
		NSLog(@"Connection error");
	}
/*	// Create the request.
	 NSURLRequest *theRequest=[NSURLRequest requestWithURL:[NSURL URLWithString:@"http://localhost:8080/BomberklobServer/inscription"]
	 cachePolicy:NSURLRequestUseProtocolCachePolicy
	 timeoutInterval:60.0];
	 // create the connection with the request
	 // and start loading the data
	 NSURLConnection *theConnection=[[NSURLConnection alloc] initWithRequest:theRequest delegate:self];
	 if (theConnection) {
	 // Create the NSMutableData to hold the received data.
	 // receivedData is an instance variable declared elsewhere.
	 NSMutableData * receivedData = [[NSMutableData data] retain];
	 } else {
	 // Inform the user that the connection failed.
	 }*/
}
- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)theData{
    NSLog(@"String sent from server %@",[[NSString alloc] initWithData:theData encoding:NSUTF8StringEncoding]);
}

+ (NSString *) md5:(NSString *)str {
	const char *cStr = [str UTF8String];
	unsigned char result[16];
	CC_MD5( cStr, strlen(cStr), result );
	return [NSString stringWithFormat:
			@"%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X",
			result[0], result[1], result[2], result[3], 
			result[4], result[5], result[6], result[7],
			result[8], result[9], result[10], result[11],
			result[12], result[13], result[14], result[15]
			]; 
}
@end
