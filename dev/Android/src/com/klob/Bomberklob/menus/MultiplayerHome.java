package com.klob.Bomberklob.menus;


import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MultiplayerHome extends Activity implements View.OnClickListener{
	
	private Button previous;
	private Button createGame;
	
	private ImageButton refresh;
	
	/** 
	 * menu d'accueil multijoueurs où sont listés les parties en cour
	 */
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.multiplayerhome);

	        previous = (Button)findViewById(R.id.buttonCancel);
			previous.setOnClickListener(this);
			
			createGame = (Button)findViewById(R.id.buttonCreateGame);
			createGame.setOnClickListener(this);
			
			refresh = (ImageButton) findViewById(R.id.buttonRefresh);
			refresh.setOnClickListener(this);
	                
	        
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

		public void onClick(View v) {
			Intent intent = null;
			if(v == refresh){
				Toast.makeText(MultiplayerHome.this, "Rafraichissement des parties", Toast.LENGTH_SHORT).show();

			}
			else if (v == createGame) {
				/*
				 * Nous sommes maintenant sûr que la vue ayant été cliquée est le bouton
				 * de notre interface. Il suffit donc de créer un nouvel Intent pour démarrer
				 * la seconde activité.
				 */
//				Toast.makeText(AccueilMulti.this, "Creation partie multi", Toast.LENGTH_SHORT).show();

				//Intent intent = new Intent(HomeMulti.this, CreateGameMulti.class);
				//startActivity(intent);
			}
			else if(v == previous){
				if(Model.getUser().getConnectionAuto()){
					intent = new Intent(MultiplayerHome.this, Home.class);
				}
				else{
					intent = new Intent(MultiplayerHome.this, MultiPlayer.class);
				}
				
			}			
			if ( intent != null ) {
				startActivity(intent);
				this.finish();
			}	
		}
}


