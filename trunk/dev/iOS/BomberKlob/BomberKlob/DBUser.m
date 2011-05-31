#import "DBUser.h"
#import "DataBase.h"


@implementation DBUser

@synthesize identifier;
@synthesize pseudo;
@synthesize userName;
@synthesize password;
@synthesize connectionAuto;
@synthesize rememberPassword;
@synthesize color;
@synthesize menuPosition;
@synthesize gameWon;
@synthesize gameLost;


- (id)initWithPseudo:(NSString *)aPseudo {
    self = [super init];
    
    if (self) {
        dataBase = [DataBase sharedDataBase];
        
        identifier = 0;
        self.pseudo = aPseudo;
        self.color = 0;
        self.menuPosition = 1;
        self.gameWon = 0;
        self.gameLost = 0;
    }
    
    return self;
}


- (id)initWithId:(NSInteger)aId {
    self = [super init];
    
    if (self) {
        if (aId != 0) {
            dataBase = [DataBase sharedDataBase];
            sqlite3_stmt *statement = [dataBase select:@"*" from:@"AccountPlayer" where:[NSString stringWithFormat:@"id = %d", aId]];
            identifier = aId;
            
            while (sqlite3_step(statement) == SQLITE_ROW) {
                pseudo = [[NSString alloc] initWithUTF8String:(char *) sqlite3_column_text(statement, 1)];
                
                if ([[NSString stringWithUTF8String:(char *) sqlite3_column_text(statement, 2)] isEqual:@""]) {
                    userName = nil; 
                    password = nil;
                }
                else {
                    userName = [[NSString alloc] stringWithUTF8String:(char *) sqlite3_column_text(statement, 2)]; 
                    password = [[NSString alloc] stringWithUTF8String:(char *) sqlite3_column_text(statement, 3)];
                }
                
                connectionAuto = (BOOL) sqlite3_column_int(statement, 4);
                rememberPassword = (BOOL) sqlite3_column_int(statement, 5);
                color = sqlite3_column_int(statement, 6);
                menuPosition = sqlite3_column_int(statement, 7);
                gameWon = sqlite3_column_int(statement, 8);
                gameLost = sqlite3_column_int(statement, 9);
            }
            
            sqlite3_finalize(statement);
        }
    }
    
    return self;
}


- (void)dealloc {
    [pseudo release];
    [userName release];
    [password release];
    [super dealloc];
}


- (NSString *)description {
    return [NSString stringWithFormat:@"User:\n Id: %d\n Pseudo: %@\n User name: %@\n Password: %@\n Connection auto: %d\n Remember password: %d\n Color: %d\n Menu position: %d\n Game won: %d\n Game lost: %d\n", identifier, pseudo, userName, password, connectionAuto, rememberPassword, color, menuPosition, gameWon, gameLost];
}


- (void)saveInDataBase {
    NSString *values;
    
    if (!userName) {
        values = [NSString stringWithFormat: @"(NULL, '%@', '', '', 0, 0, %d, %d, %d, %d)", pseudo, color, menuPosition, gameWon, gameLost];
    }
    else {
        values = [NSString stringWithFormat: @"(NULL, '%@', '%@', '%@', %d, %d, %d, %d, %d, %d)", pseudo, userName, password, connectionAuto, rememberPassword, color, menuPosition, gameWon, gameLost];
    }
    
    [dataBase insertInto:@"AccountPlayer" values:values];
    
    sqlite3_stmt *statement = [dataBase select:@"id" from:@"AccountPlayer" where:[NSString stringWithFormat:@"pseudo = '%@'", pseudo]];
    
    while (sqlite3_step(statement) == SQLITE_ROW) {
        identifier = (NSUInteger) sqlite3_column_int(statement, 0); 
    }
    
    sqlite3_finalize(statement);
}

@end
