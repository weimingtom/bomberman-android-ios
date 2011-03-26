package com.klob.bomberklob;

import com.klob.bomberklob.R;

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
	
	 @Override
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
	 
	 @Override
	protected void onStop() {
			Log.i("", "onStop ");
			super.onStop();
		}
		
		@Override
		protected void onDestroy(){
			Log.i("", "onDestroy ");
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
			if(v == rafraichir){
				Toast.makeText(AccueilMulti.this, "Rafraichissement des parties", Toast.LENGTH_SHORT).show();

			}
			else if (v == creerPartie) {

			}
			else if(v == retour){
				finish();
			}
		}
}


