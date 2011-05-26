//
//  MultiplayerConnectionMenuViewController.m
//  BomberKlob
//
//  Created by Kilian Coubo on 19/05/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "MultiplayerConnectionMenuViewController.h"
#import "MultiplayerRegisterMenuViewController.h"
#import "MultiplayerGameListController.h"
#import "JSON.h"


@implementation MultiplayerConnectionMenuViewController
@synthesize registerButton;
@synthesize offlineLogin;
@synthesize username;
@synthesize password;
@synthesize autoLogin;
@synthesize rememberPassword;
@synthesize returnButton;
@synthesize done;

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
    [registerButton release];
    [offlineLogin release];
    [username release];
    [password release];
    [autoLogin release];
    [rememberPassword release];
    [returnButton release];
    [done release];
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
    
    self.title = NSLocalizedString(@"Connection", @"Title of 'Connection' page");
	self.navigationItem.rightBarButtonItem = done;}

- (void)viewDidUnload
{
    [self setRegisterButton:nil];
    [self setOfflineLogin:nil];
    [self setUsername:nil];
    [self setPassword:nil];
    [self setAutoLogin:nil];
    [self setRememberPassword:nil];
    [self setReturnButton:nil];
    [self setDone:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}

- (IBAction)registerAction:(id)sender {
	MultiplayerRegisterMenuViewController * multiplayerRegisterMenuViewController = [[MultiplayerRegisterMenuViewController alloc] initWithNibName:@"MultiplayerMenuViewController" bundle:nil];
    [self.navigationController pushViewController:multiplayerRegisterMenuViewController animated:YES];
    registerController = multiplayerRegisterMenuViewController;
}

- (IBAction)doneAction:(id)sender {
	NSError *jsonError;
	
	NSMutableArray * array = [[NSMutableArray alloc] init];
	[array addObject:[username text]];
	[array addObject: [MultiplayerRegisterMenuViewController md5:[password text]]];
	
	
	
	NSMutableString * requete = [[NSMutableString alloc] init];
	SBJsonWriter *writer = [[SBJsonWriter alloc] init];
	[writer appendValue:array into:requete];
	
	NSString *post = requete;
	
	NSData *postData = [post dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
	NSString *postLength = [NSString stringWithFormat:@"%d",[postData length]];
	NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
	[request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://localhost:8080/BomberklobServer/connection"]]];
	[request setHTTPMethod:@"POST"];
	[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
	[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Current-Type"];
	[request setHTTPBody:postData];
	NSURLConnection *conn = [[NSURLConnection alloc]initWithRequest:request delegate:self];
	if (!conn) {
		NSLog(@"Connection error");
	}

}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)theData{
	NSString * response = [[NSString alloc] initWithData:theData encoding:NSUTF8StringEncoding];
    NSLog(@"String sent from server %@",response);
	if ([response isEqualToString:@"OK"]) {
		
		
		NSError *jsonError;
		
		NSMutableArray * array = [[NSMutableArray alloc] init];
		if (idCookie != nil) {
			[array addObject:idCookie];
		}
		else {
			[array addObject:registerController.idCookie];
		}
		
		NSMutableString * requete = [[NSMutableString alloc] init];
		SBJsonWriter * writer = [[SBJsonWriter alloc] init];
		[writer appendValue:array into:requete];
		NSString *post = requete;
		NSData *postData = [post dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
		NSString *postLength = [NSString stringWithFormat:@"%d",[postData length]];
		NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
		[request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://localhost:8080/BomberklobServer/gameslist"]]];
		[request setHTTPMethod:@"POST"];
		[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
		[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Current-Type"];
		[request setHTTPBody:postData];
		NSURLConnection *conn = [[NSURLConnection alloc]initWithRequest:request delegate:self];
		if (!conn) {
			NSLog(@"Connection error");
		}
	}
	else if (![response isEqualToString:@"ERROR"] && ![response isEqualToString:@"KO"] && ![response isEqualToString:@"BU"]){
		NSError * error = [[NSError alloc] init];
		NSString * response = [[NSString alloc] initWithData:theData encoding:NSUTF8StringEncoding];
		SBJsonParser * parser = [[SBJsonParser alloc] init];
		NSDictionary * gameList =  [parser objectWithString:response error:error];
		[gameList retain];
		MultiplayerGameListController * multiplayerGameListController = [[MultiplayerGameListController alloc] initWithNibName:@"MultiplayerGameListController" bundle:nil gamesList:gameList];
		[self.navigationController pushViewController:multiplayerGameListController animated:YES];
	}
	
}
- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response{	
	NSHTTPURLResponse *HTTPResponse = (NSHTTPURLResponse *)response;
    NSDictionary *fields = [HTTPResponse allHeaderFields];
	NSString *cookie = [fields valueForKey:@"Set-Cookie"]; // It is your cookie
	idCookie = [cookie substringWithRange:NSMakeRange(11, 32)];
	NSLog(@"IdCookie : %@",idCookie);
	
	

	/*if ([[[response URL] description] isEqualToString:"http://localhost:8080/BomberklobServer/gameslist"]) {

	}*/
	
}
@end
