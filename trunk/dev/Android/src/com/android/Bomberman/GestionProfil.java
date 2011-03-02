package com.android.Bomberman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GestionProfil extends Activity implements View.OnClickListener{
	
	private Button annuler;
	private Button changerCompte;
	private Button modifierMdp;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // plein ecran, à remettre dans chaque onCreate
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

                
        setContentView(R.layout.gestionprofil);

        changerCompte = (Button) findViewById(R.id.changerCompte);
        changerCompte.setOnClickListener(this); 
        
        modifierMdp = (Button) findViewById(R.id.modifierMdp);
        modifierMdp.setOnClickListener(this); 
        
        annuler = (Button) findViewById(R.id.boutonRetour);
        annuler.setOnClickListener(this);   
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
		if(v == changerCompte){
			Intent intent = new Intent(GestionProfil.this, ChangerCompteMulti.class);
			startActivity(intent);
		}
		else if( v == modifierMdp){
			Intent intent = new Intent(GestionProfil.this, ChangerPassMulti.class);
			startActivity(intent);			
		}
		else if( v == annuler){
			finish();
		}
    }


}
