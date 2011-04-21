//
//  OptionsMenuViewController.m
//  BomberKlob
//
//  Created by Benjamin Tardieu on 06/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "OptionsMenuViewController.h"
#import "AccountManagementMenuViewController.h"
#import "BomberKlobAppDelegate.h"
#import "Application.h"
#import "DBSystem.h"


@implementation OptionsMenuViewController

@synthesize soundSlider, language, volumeCell, muteCell, languageCell, accountManagerCell, mute;


- (id)initWithStyle:(UITableViewStyle)style {
    self = [super initWithStyle:style];
    
    if (self) {

    }
    
    return self;
}


- (void)dealloc {
    [volumeCell release];
    [muteCell release];
    [languageCell release];
    [accountManagerCell release];
    [language release];
    [soundSlider release];
    [mute release];
    [super dealloc];
}


- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}


#pragma mark - View lifecycle

- (void)viewDidLoad {
    [super viewDidLoad];

    self.title = NSLocalizedString(@"Options", @"Title of 'Options' page");
    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    soundSlider.value = application.system.volume;
    mute.on = application.system.mute;
    
    [self muteActived];
}


- (void)viewDidUnload {
    [self setVolumeCell:nil];
    [self setMuteCell:nil];
    [self setLanguageCell:nil];
    [self setAccountManagerCell:nil];
    [self setLanguage:nil];
    [self setSoundSlider:nil];
    [self setMute:nil];
    [super viewDidUnload];
}


- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
}


- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
}


- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
}


- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return (interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 3;
}


- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
	
	NSString *sectionHeader = nil;
	
	switch (section) {
		case 0:
			sectionHeader = NSLocalizedString(@"Sound", @"Title of section 'Sound' in the options menu");
			break;
		case 1:
			sectionHeader = NSLocalizedString(@"Language", @"Title of section 'Language' in the options menu");
			break;
		case 2:
			sectionHeader = NSLocalizedString(@"Account", @"Title of section 'Account' in the options menu");
			break;
	}
	
	return sectionHeader;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    switch (section) {
        case 0:
            return 2;
            break;
        case 1:
            return 1;
            break;
        case 2:
            return 1;
            break;
            
        default:
            return 0;
            break;
    }
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
		if (indexPath.row == 0) {
			return volumeCell;
		}
        else if (indexPath.row == 1) {
			return muteCell;
		}
    }
	else if (indexPath.section == 1) {
        Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
		
		if ([application.system.language isEqualToString:@"en"]) {
			language.text = @"English";
		}
		if ([application.system.language isEqualToString:@"fr"]) {
			language.text = @"Français";
		}
        
		return languageCell;
	}
	else if (indexPath.section == 2) {
		return accountManagerCell;
	}
	
	return nil;
}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (indexPath.section == 2) {
		[self goToAccountManagement];
	}
}


#pragma mark - My methods

- (void)muteActived {
	if (mute.on) 
		soundSlider.enabled = NO;
	else 
		soundSlider.enabled = YES;
}


- (void)goToAccountManagement {
    AccountManagementMenuViewController *accountManagementMenuViewController = [[AccountManagementMenuViewController alloc] initWithNibName:@"AccountManagementMenuViewController" bundle:nil];
	[self.navigationController pushViewController:accountManagementMenuViewController animated:YES];
	[accountManagementMenuViewController release];
}


#pragma mark - Action

- (IBAction)muteActivedAction:(id)sender {	
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [self muteActived];
    application.system.mute = mute.on;
    [application modifyMute:mute.on];
}


- (IBAction)soundModificationAction:(id)sender {    
    Application *application = ((BomberKlobAppDelegate *) [UIApplication sharedApplication].delegate).app;
    [application modifyVolume:soundSlider.value];
    application.system.volume = soundSlider.value;
}

@end
