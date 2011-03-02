package com.android.Bomberman;

import java.io.IOException;
import java.util.Calendar;

import com.android.Bomberman.Database.BaseDeDonnee;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreerCompteHorsLigne extends Activity implements View.OnClickListener{
	
	private EditText login;
	private Button valider;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // plein ecran, à remettre dans chaque onCreate
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.creercomptehorsligne);
        
        login = (EditText) findViewById(R.id.Login);
		
		valider = (Button)findViewById(R.id.boutonValider);
		valider.setOnClickListener(this);
		
	}
	
	protected void onStop() {
		Log.i("", "onStop ");
		super.onStop();
	}
	
	protected void onDestroy(){
		Log.i("", "onDestroy ");
		super.onDestroy();
	}
	
	protected void onResume(){
		Log.i("", "onResume ");
		super.onResume();
	}
	
	protected void onPause(){
		Log.i("", "onPause ");
		super.onPause();
	}
	
	
	public void onClick(View view) {
		if(view ==valider){
//			

//			Toast.makeText(CreerCompteMulti.this, "Creation du compte multi"+
//					this.nomMap+" en mode "+typeP+" avec "+ennemis+
//					" ennemis, ca va être "+difficulte+" !", Toast.LENGTH_SHORT).show();
			
			Log.i("", "Ecriture db ");
			
			BaseDeDonnee maBase = new BaseDeDonnee(this);
	 
	        try {
	 
	        	maBase.createDataBase();
		 
		 	} catch (IOException ioe) {
		 
		 		throw new Error("Unable to create database");
		 
		 	}
		 
		 	try {
		 		String Login = login.getText().toString();
		 		boolean result;
		 		
				try {
					// test de l'existance d'un compte avec le meme Login
					result = maBase.existanceCompte(Login);
					if(!result){
			 			maBase.insertionCompte(Login );
			 			Log.i("", "res => "+maBase.listeCompte());
			 			maBase.close();
			 			
			 			Intent intent = new Intent(CreerCompteHorsLigne.this, Bomberman.class);
						startActivity(intent);
			 		}
			 		else{
			 			Toast.makeText(CreerCompteHorsLigne.this, "Compte deja existant !", Toast.LENGTH_SHORT).show();
			 		}
				} catch (java.sql.SQLException e) {
					e.printStackTrace();
				}
		 		
		 		
		 
		 	}catch(SQLException sqle){
		 		
		 		Log.i("", "oups db ");
		 		throw sqle;
		 
		 	}
			
			
			Log.i("", "Ecriture db fin");
			
			
		}
		
		
	}

}
