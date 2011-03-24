package com.android.Bomberman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ChangerPassMulti extends Activity implements View.OnClickListener {
	private Button annuler;
	private Button valider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // plein ecran, à remettre dans chaque onCreate
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.changerpassmulti);
        
        annuler = (Button)findViewById(R.id.boutonAnnuler);
        annuler.setOnClickListener(this);
		
		valider = (Button)findViewById(R.id.boutonValider);
		valider.setOnClickListener(this);
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
	public void onClick(View view) {
		if(view == valider){
//			Toast.makeText(ConnexionCompteMulti.this, "Connexion au compte multi", Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(ChangerPassMulti.this, AccueilMulti.class);
			startActivity(intent);

		}
		else if(view == annuler){
			finish();
		}
		
	}

}
