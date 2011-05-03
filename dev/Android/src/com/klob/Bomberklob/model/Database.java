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
 
    private static String DB_NAME = "Klob";
    private SQLiteDatabase base; 
  

    public Database(Context context) {
    	super(context, DB_NAME, null, 1);
    	base = getWritableDatabase();
    }	
 
 
    @Override
	public synchronized void close() {
 
    	    if(base != null)
    		    base.close();
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE PlayerAccount (id INTEGER PRIMARY KEY AUTOINCREMENT," +
					" pseudo VARCHAR(50) UNIQUE NOT NULL," +
					"userName VARCHAR(50) DEFAULT NULL  ," +
					"password VARCHAR(50) DEFAULT NULL ," +
					" connectionAuto INTEGER DEFAULT 0, " +
					"rememberPassword INTEGER DEFAULT 0," +
					" color VARCHAR(15) DEFAULT 'white'," +
					"menuPosition VARCHAR(10) DEFAULT 'Right'," +
					" gameWon INTEGER DEFAULT '0'," +
					" gameLost INTEGER DEFAULT '0')");
		
				
		db.execSQL("CREATE TABLE System (" +
				"volume UNSIGNED SMALLINT," +
				"language VARCHAR(20) ," +
				"lastUser INTEGER ," +
				"FOREIGN KEY(lastUser) REFERENCES PlayerAccount(id)" +
			");");
		
		db.execSQL(
				"CREATE TABLE Map (" +
				"name VARCHAR(50) NOT NULL," + 
				"owner INTEGER NOT NULL," +
				"official INTEGER NOT NULL," +
				"FOREIGN KEY(owner) REFERENCES PlayerAccount(id)" +
			");");		
		db.execSQL("INSERT INTO System (volume, language, lastUser) VALUES (50, 'French', -1);");
		
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	@Override
	public void onOpen(SQLiteDatabase db){
	}
	
	
	/** Ajouts à la base de donnée **/
	
	/** ajout compte hors ligne **/
	public long newAccount(String nomCompte){
		base = getWritableDatabase();

		ContentValues entree = new ContentValues();
		
		entree.put("pseudo", nomCompte);
		long var = base.insert("PlayerAccount", null, entree);
		
		base.close();
		return var;
	}
	
	
	/** ajout d'une map **/
	public void newMap(String name, String owner, int i){
//		Cursor cursor = base.rawQuery("SELECT * FROM Map WHERE name ='"+name+"' ", null);
//		entree.put("name", name);
//		entree.put("owner", owner);
//		entree.put("official", i);
//		
//		if(cursor.moveToFirst()){
//			if(!cursor.isNull(0)){
//				this.base.update("Map", entree, "name ='"+owner+"'", null);
//				Log.i("Database", "New map : Map already exists, update");				
//			}
//			else{
//				this.base.insert("Map", null, entree);
//				Log.i("Database", "New map added : \nName : " + name +"\nOwner : " + owner + "\nOfficial : " + i);
//			}
//		}
		
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
	
	
	/** Mise à jour des données **/
	
	/** ajout compte multijoueur **/
	public boolean addAccountMulti(int userId, String userName, String pwd) {
		base = getWritableDatabase();
		ContentValues entree = new ContentValues();
		boolean res = false;
				
		entree.put("userName", userName);
		entree.put("password", pwd);
		res = (base.update("PlayerAccount", entree, "id = '"+ userId +"'", null) > 0 );
				
		base.close();	
		return res;

	}	
	
	/** mise à jour du volume **/
	public int setVolume(int volume){
		if(volume < 100){
			base = getWritableDatabase();

			ContentValues entree = new ContentValues();
			
			entree.put("volume", volume);
			boolean res = (base.update("System", entree, null, null) > 0 );
			base.close();
			if(!res){
				Log.i("", "Mise à jour du son impossible");
			}			
			return volume;	
		}
		else{
			Log.i("", "Mauvaise valeur du volume ");
			return -1;
		}
	}
	
	/** mise à jour dernier utilisateur **/
	public void setLastUser(String pseudonymAccount) {
		this.base = this.getWritableDatabase();
		Cursor cursor = this.base.rawQuery("SELECT id FROM PlayerAccount WHERE pseudo ='"+pseudonymAccount+"' ", null);
		if (cursor.moveToFirst()) {
			ContentValues entree = new ContentValues();
			entree.put("lastUser", cursor.getInt(0));
			this.base.update("System", entree, null, null);
			Log.i("DataBase", "Last user : " + pseudonymAccount);
		}
		cursor.close();
		base.close();
	}
	
	/** update couleur du joueur **/
	public boolean updateColorUser(int userId, String color){
		base = getWritableDatabase();
		boolean res;
		ContentValues entree = new ContentValues();
		
		entree.put("color", color);
		res = (base.update("PlayerAccount", entree, "id = '"+userId+"'", null) > 0 );
		
		base.close();
		return res;	
		
	}
	
	/** update côté menu du joueur **/
	public boolean updateMenuUser(int userId, int side){
		base = getWritableDatabase();
		boolean res;
		ContentValues entree = new ContentValues();
		
		entree.put("menuPosition", side);
		res = (base.update("PlayerAccount", entree, "id = '"+userId+"'", null) > 0 );
		
		base.close();
		return res;	
		
	}
	
	/** update connexion auto **/
	public boolean updateAutoConnectUser(int userId, int auto){
		base = getWritableDatabase();
		boolean res;
		
		ContentValues entree = new ContentValues();
		entree.put("connectionAuto", auto);
		res = (base.update("PlayerAccount", entree, "id = '"+userId+"'", null) > 0 );
		
		base.close();
		return res;	
		
	}
	
	/** update sauvegarde mot de passe **/
	public boolean updateSavePwdUser(int userId, int save){
		base = getWritableDatabase();
		boolean res;
		
		ContentValues entree = new ContentValues();
		entree.put("rememberPassword", save);
		res = (base.update("PlayerAccount", entree, "id = '"+userId+"'", null) > 0 );
		
		base.close();
		return res;	
	}
	
	/** update userName **/
	public boolean updateUserName(int userId, String userName){
		base = getWritableDatabase();
		boolean res;
		
		ContentValues entree = new ContentValues();
		entree.put("userName", userName);
		res = (base.update("PlayerAccount", entree, "id = '"+userId+"'", null) > 0 );
		
		base.close();
		return res;	
		
	}
	
	/** update password **/
	public boolean updatePassword(int userId, String oldPassword, String newPassword){
		base = getWritableDatabase();
		boolean res;
		
		ContentValues entree = new ContentValues();
		entree.put("password", newPassword);
		res = (base.update("PlayerAccount", entree, "id = '"+userId+"'", null) > 0 );
		
		base.close();
		return res;		
	}
	
	/** update language **/
	public boolean setLanguage(String language){
		base = getWritableDatabase();
		boolean res;
		
		ContentValues entree = new ContentValues();
		entree.put("language", language);
		res = (base.update("System", entree, null, null) > 0 );
		
		base.close();
		return res;		
	}
	
	/** 	 **/
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
		this.base.update("PlayerAccount", entree, "pseudo ='"+user.getPseudo()+"' ", null);
		
		Log.i("DataBase", "Update user : " + user.getPseudo());

		base.close();
	}
	
	public void changePseudo(String oldPseudo, String newPseudo) {
		
		this.base = this.getReadableDatabase();
		
		ContentValues entree = new ContentValues();
		entree.put("pseudo", newPseudo);
		this.base.update("PlayerAccount", entree, "pseudo ='"+oldPseudo+"' ", null);

		base.close();
	}
	
	
	
	/** Appel aux valeurs **/
	
	/** appel au dernier utilisateur retournant son id **/
	public int getLastUserId(){
		base = getReadableDatabase();
	    int lastUser = -1;
	
		Cursor cursor = base.rawQuery("select lastUser from System", null);		
		if (cursor.moveToFirst()) {
	        	lastUser = cursor.getInt(0);
		}
		else{
			Log.i("", "Aucun dernier utilisateur");
		}
		cursor.close();
		base.close();
		return lastUser;
	}
	
	/** appel au dernier utilisateur retournant son nom **/
	public String getLastUserName(){
		base = getReadableDatabase();
	    String lastUser = "";
	    int lastUserId = -1;
	    
		Cursor cursorId = base.rawQuery("select lastUser from System", null);		
		if (cursorId.moveToFirst()) {
	        	lastUserId = cursorId.getInt(0);
	        	Cursor cursorName = base.rawQuery("select pseudo from AccountPayer where id = '"+ lastUserId +"' ", null);
	        	if (cursorName.moveToFirst()) {
		        	lastUser = cursorId.getString(0);
	        	}
	        	else{
	        		Log.i("", "Aucun nom d'utilisateur rattaché à cette id");
	        	}
		}
		else{
			Log.i("", "Aucun dernier utilisateur");
		}
		base.close();
		return lastUser;
	}
	
	/** FIXME **/
	public int getLastUser() {
		
		int res = -1;
		this.base = this.getReadableDatabase();
		
		Cursor cursor = this.base.rawQuery("SELECT lastUser FROM System", null);
		if (cursor.moveToFirst()) {
			res = cursor.getInt(0);
		}
		
		Log.i("DataBase", "Get last user : " + res);

		cursor.close();
		base.close();
		
		return res;
	}	
	
	/** appel de l'id par son pseudo **/
	public int getUserIdByName(String userName){
		base = getReadableDatabase();
	    int user = -1;
	    
	    Cursor cursorId = base.rawQuery("select id from PlayerAccount where pseudo ='"+ userName +"' ", null);		
		if (cursorId.moveToFirst()) {
	        	user = cursorId.getInt(0);
		}
		else{
			Log.i("", "Aucun utilisateur correspondant à cet id");
		}
		cursorId.close();
	    base.close();
		return user;	
	}
	
	/** appel aux informations complètes d'un utilisateur par son id  **/
	public ArrayList<String> getUserInfoById(int lastUserId){
		ArrayList<String> spinnerArray = new ArrayList<String>();
		
		base = getReadableDatabase();
		

	      Cursor cursor = base.rawQuery("select pseudo, userName, password, connectionAuto, rememberPassword, color," +
	      		"menuPosition, gameWon, gameLost from PlayerAccount where id = '"+ lastUserId +"' ", null);
	      
      	if (cursor.moveToFirst()) {
	      		spinnerArray.add(cursor.getString(0));
	      		spinnerArray.add(cursor.getString(1));
	      		spinnerArray.add(cursor.getString(2));
	      		spinnerArray.add(cursor.getString(3)); /*int*/
	      		spinnerArray.add(cursor.getString(4)); /*int*/
	      		spinnerArray.add(cursor.getString(5));
	      		spinnerArray.add(cursor.getString(6));
	      		spinnerArray.add(cursor.getString(7)); /*int*/
	      		spinnerArray.add(cursor.getString(8)); /*int*/
      	}
      	else{
      		Log.i("", "Utilisateur inconnu");
      	}

	      cursor.close();
	      base.close();
		
		return spinnerArray;
			
	}
	
	/** retourne la liste des utilisateurs locaux **/
	public ArrayList<String> getAccountsPseudos(){
		ArrayList<String> spinnerArray = new ArrayList<String>();
		
		base = getReadableDatabase();
		

	      String[] colonnes={"pseudo","id"};
	      Cursor cursor = base.query("PlayerAccount", colonnes, null, null, null, null, null);
	      if (cursor.moveToFirst()) {
	         do {
	        	 spinnerArray.add(cursor.getString(0));
	         
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      
	      cursor.close();
	      base.close();
	      	
		return spinnerArray;
	}
	
	/** retourne la liste des map**/	
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
		base.close();
		
		return vec;
	}
	
	/** Retourne l'instance de l'utilisateur correspondant à son pseudo **/
	public User getUser(String pseudonymAccount) {
		
		User user = null;
		this.base = this.getReadableDatabase();
		Cursor cursor = this.base.rawQuery("SELECT * FROM PlayerAccount WHERE pseudo ='"+pseudonymAccount+"' ", null);
		if (cursor.moveToFirst()) {
			Log.i("Database", "----- Player loaded -----\nPseudo : " + cursor.getString(1) + "\nUserName : " + cursor.getString(2) + "\nPassword : " + cursor.getString(3) + "\nAutoConnection : " + cursor.getInt(4) + "\nRememberPassword : " + cursor.getInt(5) + "\nColor : " + cursor.getString(6) + "\nMenu Position : " + cursor.getString(7) + "\nGame won : " + cursor.getInt(8) + "\nGame lost : " + cursor.getInt(9) + "\n-------------------------\n");
			user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), (cursor.getInt(4) == 0 ? false : true), (cursor.getInt(5) == 0 ? false : true), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9));
		}
		cursor.close();
		base.close();
		
		return user;
	}
	
	public User getUser(int id) {
		
		User user = null;
		this.base = this.getReadableDatabase();
		Cursor cursor = this.base.rawQuery("SELECT * FROM PlayerAccount WHERE id ='"+id+"' ", null);
		if (cursor.moveToFirst()) {
			Log.i("Database", "----- Player loaded -----\nPseudo : " + cursor.getString(1) + "\nUserName : " + cursor.getString(2) + "\nPassword : " + cursor.getString(3) + "\nAutoConnection : " + cursor.getInt(4) + "\nRememberPassword : " + cursor.getInt(5) + "\nColor : " + cursor.getString(6) + "\nMenu Position : " + cursor.getString(7) + "\nGame won : " + cursor.getInt(8) + "\nGame lost : " + cursor.getInt(9) + "\n-------------------------\n");
			user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), (cursor.getInt(4) == 0 ? false : true), (cursor.getInt(5) == 0 ? false : true), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9));
		}
		cursor.close();
		base.close();
		
		return user;
	}
	
	/** retourne la langue utilisée par le System **/
public String getLanguage() {
		
		String res = null;
		this.base = this.getReadableDatabase();

		Cursor cursor = this.base.rawQuery("SELECT language FROM System", null);
		if (cursor.moveToFirst()) {
			res = cursor.getString(0);
		}
		Log.i("DataBase", "Get language : " + res);

		cursor.close();
		base.close();
		
		return res;
	}
	
	/** retourne le volume **/
	public int getVolume(){
		base = getReadableDatabase();
		Cursor cursor;
		int volume = -1;
		
		cursor = base.rawQuery("select volume from System", null);
		
		if (cursor.moveToFirst()) {
			volume = cursor.getInt(0);
		}
		
		cursor.close();
		base.close();
		return volume;
		
	}
	
	
	/** Test booléens **/
	
	/** le nom de compte hors ligne existe-t-il déjà **/
	public boolean existingAccount(String pseudonymAccount) {
		this.base = this.getReadableDatabase();
		
		Cursor cursor = this.base.rawQuery("SELECT pseudo FROM PlayerAccount", null);
		if (cursor.moveToFirst()) {
			do {
				if ( cursor.getString(0).toLowerCase().equals(pseudonymAccount.toLowerCase()) )  {
					base.close();
					return true;
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		base.close();
		
		return false;
	}
	
	/** le nom de map existe-t-il déjà **/
	public boolean existingMap(String mapName) {
		this.base = this.getReadableDatabase();
		int res = this.base.rawQuery("SELECT name FROM Map WHERE name ='"+mapName+"' ", null).getCount();
		base.close();
		
		return res == 0 ? false : true;
	}
	
	/** userName et password multi correspondent au bon userId ? **/
	public boolean isGoodMultiUser(int userId, String userName, String password) throws java.sql.SQLException{
		
		base = getReadableDatabase();
		boolean res = false;
		
		Log.i("BDD", "param: "+ userId + " : " + userName + " : " + password);
		
		Cursor cursor = base.rawQuery("select userName,password from PlayerAccount where id ='" +userId+ "'", null);
		if(cursor.moveToFirst()){
			if(!cursor.isNull(0)){
				res = cursor.getString(0).equals(userName) && cursor.getString(1).equals(password);
			}
		}
			
		cursor.close();
		base.close();
		
		return res;
	}
      
 
}