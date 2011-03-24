package com.android.Bomberman.Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDeDonnee extends SQLiteOpenHelper{
 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.android.Bomberman/databases/";
 
    private static String DB_NAME = "Comptes";
 
    private SQLiteDatabase maBase; 
 
    private final Context monContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public BaseDeDonnee(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.monContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    		Log.i("", "Bd deja existante ");
    	}else{
 
    		//By calling this method an empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = monContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    	
    	
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	maBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(maBase != null)
    		    maBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("", "Creation bd ");
		db.execSQL("CREATE TABLE Utilisateur (Login TEXT PRIMARY KEY )");
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	
	@Override
	public void onOpen(SQLiteDatabase db){
		Log.i("", "Ouverture bd ");
	}
	
	
	public long insertionCompte(String nomCompte){
		String myPath = DB_PATH + DB_NAME;
		maBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

		ContentValues entree = new ContentValues();
		
		//on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		entree.put("Login", nomCompte);
	

		return maBase.insert("Utilisateur", null, entree);
		
	}
	
	
	public boolean existanceCompte(String nomCompte) throws java.sql.SQLException{
		String myPath = DB_PATH + DB_NAME;
		maBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		
		if(maBase.rawQuery("select Login as _id from Utilisateur where Login ='"+nomCompte+"' ", null).getCount() == 0 ){
			return false;
		}
		else{
			return true;
		}
	}
	
	public ArrayList<String> listeCompte(){
		ArrayList<String> spinnerArray = new ArrayList<String>();
		String chaine = "";
		
		String myPath = DB_PATH + DB_NAME;
		maBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		

//		List list = new ArrayList();
	      String[] colonnes={"Login"};
	      Cursor cursor = maBase.query("Utilisateur", colonnes, null, null, null, null, null);
	      if (cursor.moveToFirst()) {
	         do {
	        	 spinnerArray.add(cursor.getString(0));
//	            list.add(cursor.getString(0)); 
	         
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      
	      cursor.close();
	      maBase.close();
		
		return spinnerArray;
	}
	
	
	public boolean estVide(){
		String myPath = DB_PATH + DB_NAME;
		maBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		
				
		if(maBase.rawQuery("select Login as _id from Utilisateur ", null).getCount() == 0 ){
			maBase.close();
			return true;
		}
		else{
			maBase.close();
			return false;
		}
	}
	
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
 
}
