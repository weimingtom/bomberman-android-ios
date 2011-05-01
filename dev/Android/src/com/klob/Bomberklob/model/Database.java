package com.klob.Bomberklob.model;

import java.util.ArrayList;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper{

    private static String DB_NAME = "Database";
    private SQLiteDatabase base; 
 
    
    /* Constructeur -------------------------------------------------------- */

    public Database(Context context) {
    	super(context, DB_NAME, null, 1);
    }
    
    /* Getteurs ------------------------------------------------------------ */
    
	public ArrayList<String> getAccountsPseudos(){
		
		ArrayList<String> spinnerArray = new ArrayList<String>();

		this.base = this.getReadableDatabase();

		Cursor cursor = this.base.rawQuery("SELECT pseudo FROM UserAccounts", null);
		if (cursor.moveToFirst()) {
			do {
				spinnerArray.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		
		Log.i("DataBase", "Get accounts pseudos: " + spinnerArray.toString());

		cursor.close();
		this.close();

		return spinnerArray;
	}
    
	public int getLastUser() {
		
		int res = -1;
		this.base = this.getReadableDatabase();
		
		Cursor cursor = this.base.rawQuery("SELECT lastUser FROM System", null);
		if (cursor.moveToFirst()) {
			res = cursor.getInt(0);
		}
		
		Log.i("DataBase", "Get last user : " + res);

		cursor.close();
		this.close();
		
		return res;
	}
	
	public String getLanguage() {
		
		String res = null;
		this.base = this.getReadableDatabase();

		Cursor cursor = this.base.rawQuery("SELECT language FROM System", null);
		if (cursor.moveToFirst()) {
			res = cursor.getString(0);
		}
		
		Log.i("DataBase", "Get language : " + res);

		cursor.close();
		this.close();
		
		return res;
	}
	
	public int getVolume() {
		
		int res = 50;
		this.base = this.getReadableDatabase();
		
		Cursor cursor = this.base.rawQuery("SELECT volume FROM System", null);
		if (cursor.moveToFirst()) {
			res = cursor.getInt(0);
		}
		
		Log.i("DataBase", "Get volume : " + res);

		cursor.close();	
		this.close();
		
		return res;
	}
	
	public Vector<Map> getMaps() {
		
		Vector<Map> vec = new Vector<Map>();
		
		this.base = this.getReadableDatabase();
		Cursor cursor = this.base.rawQuery("SELECT * FROM Map", null);
		if (cursor.moveToFirst()) {
			do {
				vec.add(new Map(cursor.getString(0), cursor.getString(1), (cursor.getInt(2) == 0 ? false : true)));
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		this.close();
		
		return vec;
	}
	
	public User getUser(String pseudonymAccount) {
		
		User user = null;
		this.base = this.getReadableDatabase();
		Cursor cursor = this.base.rawQuery("SELECT * FROM UserAccounts WHERE pseudo ='"+pseudonymAccount+"' ", null);
		if (cursor.moveToFirst()) {
			Log.i("Database", "----- Player loaded -----\nPseudo : " + cursor.getString(1) + "\nUserName : " + cursor.getString(2) + "\nPassword : " + cursor.getString(3) + "\nAutoConnection : " + cursor.getInt(4) + "\nRememberPassword : " + cursor.getInt(5) + "\nColor : " + cursor.getString(6) + "\nMenu Position : " + cursor.getString(7) + "\nGame won : " + cursor.getInt(8) + "\nGame lost : " + cursor.getInt(9) + "\n-------------------------\n");
			user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), (cursor.getInt(4) == 0 ? false : true), (cursor.getInt(5) == 0 ? false : true), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9));
		}
		cursor.close();
		this.close();
		
		return user;
	}
	
	public User getUser(int id) {
		
		User user = null;
		this.base = this.getReadableDatabase();
		Cursor cursor = this.base.rawQuery("SELECT * FROM UserAccounts WHERE id ='"+id+"' ", null);
		if (cursor.moveToFirst()) {
			Log.i("Database", "----- Player loaded -----\nPseudo : " + cursor.getString(1) + "\nUserName : " + cursor.getString(2) + "\nPassword : " + cursor.getString(3) + "\nAutoConnection : " + cursor.getInt(4) + "\nRememberPassword : " + cursor.getInt(5) + "\nColor : " + cursor.getString(6) + "\nMenu Position : " + cursor.getString(7) + "\nGame won : " + cursor.getInt(8) + "\nGame lost : " + cursor.getInt(9) + "\n-------------------------\n");
			user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), (cursor.getInt(4) == 0 ? false : true), (cursor.getInt(5) == 0 ? false : true), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9));
		}
		cursor.close();
		this.close();
		
		return user;
	}
	
    /* Setteurs ------------------------------------------------------------ */
	
	public void setLastUser(String pseudonymAccount) {
		this.base = this.getReadableDatabase();
		Cursor cursor = this.base.rawQuery("SELECT id FROM UserAccounts WHERE pseudo ='"+pseudonymAccount+"' ", null);
		if (cursor.moveToFirst()) {
			ContentValues entree = new ContentValues();
			entree.put("lastUser", cursor.getInt(0));
			this.base.update("System", entree, null, null);
			Log.i("DataBase", "Last user : " + pseudonymAccount);
		}
		cursor.close();
		this.close();
	}
	
	public void setVolume(int volume) {
		this.base = this.getReadableDatabase();

		ContentValues entree = new ContentValues();
		entree.put("volume", volume);
		this.base.update("System", entree, null, null);
		Log.i("DataBase", "Set volume : " + volume);
		
		this.close();
	}
	
	public void setLanguage(String language) {
		this.base = this.getReadableDatabase();

		ContentValues entree = new ContentValues();
		entree.put("language", language);
		this.base.update("System", entree, null, null);
		Log.i("DataBase", "Set language : " + language);

		this.close();
	}
	 
    /* Méthodes publiques -------------------------------------------------- */
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("Database", "Creating the database");
		db.execSQL(
				"CREATE TABLE UserAccounts (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"pseudo CHAR NOT NULL UNIQUE," +
				"userName CHAR DEFAULT ''," + 
				"password CHAR DEFAULT ''," + 
				"connectionAuto UNSIGNED TINYINT DEFAULT 0," + 
				"rememberPassword UNSIGNED TINYINT DEFAULT 0," +
				"color CHAR DEFAULT 'white'," +
				"menuPosition CHAR DEFAULT 'Right'," +
				"gameWon UNSIGNED INT DEFAULT 0," +
				"gameLost UNSIGNED INT DEFAULT 0" +
			");");
		db.execSQL(
				"CREATE TABLE System (" +
				"volume UNSIGNED SMALLINT DEFAULT 50," +
				"language CHAR DEFAULT 'English'," +
				"lastUser SMALLINT DEFAULT -1," +
				"FOREIGN KEY(lastUser) REFERENCES UserAccounts(id)" +
			");");
		db.execSQL(
				"CREATE TABLE Map (" +
				"name CHAR NOT NULL," + 
				"owner CHAR NOT NULL," +
				"official UNSIGNED INT NOT NULL," +
				"FOREIGN KEY(owner) REFERENCES UserAccounts(id)" +
			");");
		db.execSQL("INSERT INTO System (volume, language, lastUser) VALUES (50, 'French', -1);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	} 
	
	public void newAccount(String account){
	
		this.base = this.getWritableDatabase();

		ContentValues entree = new ContentValues();
		entree.put("pseudo", account);
		base.insert("UserAccounts", null, entree);
		
		this.close();
	}
	
	public void newMap(String name, String owner, int i){
		
		this.base = this.getWritableDatabase();
		int res = this.base.rawQuery("SELECT * FROM Map WHERE name ='"+name+"' ", null).getCount();
		
		ContentValues entree = new ContentValues();
		entree.put("name", name);
		entree.put("owner", owner);
		entree.put("official", i);
		
		if ( res == 0 ) {
			this.base.insert("Map", null, entree);
			Log.i("Database", "New map added : \nName : " + name +"\nOwner : " + owner + "\nOfficial : " + i);	
		}
		else {
			this.base.update("Map", entree, "name ='"+owner+"'", null);
			Log.i("Database", "New map : Map already exists, update");	
		}
		this.close();
	}
	
	public void updateUser (User user) {
		this.base = this.getReadableDatabase();
		
		ContentValues entree = new ContentValues();
		entree.put("pseudo", user.getPseudo());
		entree.put("userName", user.getUserName());
		entree.put("password", user.getPassword());
		entree.put("connectionAuto", (user.getConnectionAuto() == false ? 0 : 1));
		entree.put("rememberPassword", (user.getRememberPassword() == false ? 0 : 1));
		entree.put("color", user.getColor());
		entree.put("menuPosition", user.getMenuPosition());
		entree.put("gameWon", user.getGameWon());
		entree.put("gameLost", user.getGameLost());
		this.base.update("UserAccounts", entree, "pseudo ='"+user.getPseudo()+"' ", null);
		
		Log.i("DataBase", "Update user : " + user.getPseudo());

		this.close();
	}
	
	public void changePseudo(String oldPseudo, String newPseudo) {
		
		this.base = this.getReadableDatabase();
		
		ContentValues entree = new ContentValues();
		entree.put("pseudo", newPseudo);
		this.base.update("UserAccounts", entree, "pseudo ='"+oldPseudo+"' ", null);

		this.close();
	}
	
	public boolean existingAccount(String pseudonymAccount) {
		this.base = this.getReadableDatabase();
		
		Cursor cursor = this.base.rawQuery("SELECT * FROM UserAccounts", null);
		if (cursor.moveToFirst()) {
			do {
				if ( cursor.getString(1).toLowerCase().equals(pseudonymAccount.toLowerCase()) )  {
					this.close();
					return true;
				}
			} while (cursor.moveToNext());
		}
		
		this.close();
		
		return false;
	}
	
	public boolean existingMap(String mapName) {
		this.base = this.getReadableDatabase();
		int res = this.base.rawQuery("SELECT name FROM Map WHERE name ='"+mapName+"' ", null).getCount();
		this.close();
		
		return res == 0 ? false : true;
	}
	
	
    @Override
	public synchronized void close() {
        if(this.base != null) {
        	this.base.close();
        }
    	super.close();
	}
	
}
