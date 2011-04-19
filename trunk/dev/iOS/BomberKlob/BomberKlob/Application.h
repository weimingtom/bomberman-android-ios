//
//  Application.h
//  BomberKlob
//
//  Created by Benjamin Tardieu on 28/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>

@class DataBase, DBUser, DBSystem, DBMap;


@interface Application : NSObject {
    
    DataBase *dataBase;
    
    DBUser *user;
    NSArray *pseudos;
    NSArray *maps;
    DBSystem *system;
    
    AVAudioPlayer *playerMenu;
    AVAudioPlayer *playerButton;
}

@property (nonatomic, retain) DBUser *user;
@property (nonatomic, retain) NSArray *pseudos;
@property (nonatomic, retain) NSArray *maps;
@property (nonatomic, retain) DBSystem *system;
@property (nonatomic, retain) AVAudioPlayer *playerMenu;
@property (nonatomic, retain) AVAudioPlayer *playerButton;

- (id)init;
- (void)dealloc;

- (void)loadSystem;
- (void)loadPseudos;
- (void)loadMaps;
- (void)loadAudio;

- (BOOL)existPlayer;
- (BOOL)pseudoAlreadyExists:(NSString *)pseudo;
- (void)addMap:(DBMap *)map;

- (void)playSoundMenu;
- (void)playSoundButton;
- (void)modifyVolume:(NSInteger)newVolume;
- (void)modifyMute:(BOOL)newMute;

@end
