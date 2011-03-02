package com.android.Bomberman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CreerCompteMulti  extends Activity implements View.OnClickListener{
	
	private Button valider;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // plein ecran, à remettre dans chaque onCreate
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.creercomptemulti);
        
		
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
//			Intent intent = new Intent(CreerCompteMulti.this, Accueil.class);
//			startActivity(intent);

//			Toast.makeText(CreerCompteMulti.this, "Creation du compte multi"+
//					this.nomMap+" en mode "+typeP+" avec "+ennemis+
//					" ennemis, ca va être "+difficulte+" !", Toast.LENGTH_SHORT).show();
		}
		
	}


}
