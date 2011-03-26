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

public class Options extends Activity implements View.OnClickListener{
	
	private Button retour;
	private Button gestionProfil;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // plein ecran, à remettre dans chaque onCreate
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

                
        setContentView(R.layout.options);
        
        gestionProfil = (Button) findViewById(R.id.gestionProfil);
        gestionProfil.setOnClickListener(this);
        
        retour = (Button) findViewById(R.id.retour);
        retour.setOnClickListener(this);   
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
		if(v == gestionProfil){
			Intent intent = new Intent(Options.this, GestionProfil.class);
			startActivity(intent);
		}
		else if( v == retour){
			finish();
		}
    }

}
