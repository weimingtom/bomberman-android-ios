package com.android.Bomberman;

import java.io.IOException;
import java.util.ArrayList;

import com.android.Bomberman.Database.BaseDeDonnee;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Bomberman extends Activity implements View.OnClickListener{
	
	private Button solo;
	private Button multi;
	private Button options;
	private Button stats;
	private Button creerNiveaux;
	private Button aide;
	
	private Spinner choixCompte;
	private ImageButton ajoutCompte;
	
	private BaseDeDonnee maBase;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // plein ecran, à remettre dans chaque onCreate
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        maBase = new BaseDeDonnee(this);
      	 
        try {
 
        	maBase.createDataBase();
	 
	 	} catch (IOException ioe) {
	 
	 		throw new Error("Unable to create database");
	 
	 	}
	 
	 	try {
	 		if(maBase.estVide()){
	 	        Intent intent = new Intent(Bomberman.this, CreerCompteHorsLigne.class);
	 			startActivity(intent);
	 		}
	    }
	 	catch(SQLException sqle){
	 		
	 		Log.i("", "oups db ");
	 		throw sqle;
	 
	 	}

        setContentView(R.layout.accueil);
        
        solo = (Button)findViewById(R.id.boutonSolo);
        solo.setOnClickListener(this);
        
        multi = (Button)findViewById(R.id.buttonMulti);
        multi.setOnClickListener(this);
        
        options = (Button)findViewById(R.id.buttonOptions);
        options.setOnClickListener(this);
        
        stats = (Button)findViewById(R.id.buttonStats);
        stats.setOnClickListener(this);
        
        creerNiveaux = (Button)findViewById(R.id.buttonCreerNiveaux);
        creerNiveaux.setOnClickListener(this);
        
        aide = (Button)findViewById(R.id.buttonAide);
        aide.setOnClickListener(this);
        
        
        // permet de remplir le spinner de choix de compte depuis les comptes présent dans la Bdd
        ArrayList<String> spinnerArray = maBase.listeCompte();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        Spinner spinner = (Spinner) findViewById( R.id.choixCompte );
        spinner.setAdapter(spinnerArrayAdapter);

        
        ajoutCompte = (ImageButton) findViewById(R.id.ajoutCompte);
        ajoutCompte.setOnClickListener(this);         

    }
    
    @Override
	protected void onStop() {
		Log.i("", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("", "onDestroy Accueil");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("", "onPause ");
		super.onPause();
	}     


	@Override
	public void onClick(View v) {
		
		Intent intent = null;
		
		if( v == choixCompte){
			Toast.makeText(Bomberman.this, "New compte", Toast.LENGTH_SHORT).show();
		}
		else if (v == solo) {
			/*
			 * Nous sommes maintenant sûr que la vue ayant été cliquée est le bouton
			 * de notre interface. Il suffit donc de créer un nouvel Intent pour démarrer
			 * la seconde activité.
			 */
			intent = new Intent(Bomberman.this, CreerPartieSolo.class);
			startActivity(intent);
			this.onDestroy();
		}
		else if(v == multi){
//			Toast.makeText(Bomberman.this, "Connexion au compte multi", Toast.LENGTH_SHORT).show();
			intent = new Intent(Bomberman.this, ConnexionCompteMulti.class);
			startActivity(intent);
		}
		else if(v == options){
			intent = new Intent(this, Options.class);
			startActivity(intent);
		}
		else if(v == ajoutCompte){
			intent = new Intent(Bomberman.this, CreerCompteHorsLigne.class);
			startActivity(intent);
		}
//		else if(v == stats){
//			intent = new Intent(this, Statistiques.class);
//			startActivity(intent);
//		}
//		else if(v == creerNiveaux){
//			intent = new Intent(this, CreationNiveaux.class);
//			startActivity(intent);
//		}
//		else if(v == aide){
//			intent = new Intent(this, Aide.class);
//			startActivity(intent);
//		}

	}
}