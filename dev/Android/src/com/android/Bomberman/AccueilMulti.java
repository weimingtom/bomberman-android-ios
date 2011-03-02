package com.android.Bomberman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AccueilMulti extends Activity implements View.OnClickListener{
	
	private Button retour;
	private Button creerPartie;
	
	private ImageButton rafraichir;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        // plein ecran, à remettre dans chaque onCreate
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        setContentView(R.layout.accueilmulti);

	        retour = (Button)findViewById(R.id.boutonRetour);
			retour.setOnClickListener(this);
			
			creerPartie = (Button)findViewById(R.id.boutonCreerPartie);
			creerPartie.setOnClickListener(this);
			
			rafraichir = (ImageButton) findViewById(R.id.rafraichir);
			rafraichir.setOnClickListener(this);
	                
	        
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

		public void onClick(View v) {
			if(v == rafraichir){
				Toast.makeText(AccueilMulti.this, "Rafraichissement des parties", Toast.LENGTH_SHORT).show();

			}
			else if (v == creerPartie) {
				/*
				 * Nous sommes maintenant sûr que la vue ayant été cliquée est le bouton
				 * de notre interface. Il suffit donc de créer un nouvel Intent pour démarrer
				 * la seconde activité.
				 */
//				Toast.makeText(AccueilMulti.this, "Creation partie multi", Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(AccueilMulti.this, CreerPartieMulti.class);
				startActivity(intent);
			}
			else if(v == retour){
				finish();
			}
		}
}


