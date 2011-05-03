package com.klob.Bomberklob.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.klob.Bomberklob.R;

public class Help extends Activity implements View.OnClickListener {
	
	private Button goal;
	private Button instructions;
	private Button bonus;
	private Button about;
	private Button cancel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	            
	    setContentView(R.layout.help);
	    
	    this.cancel = (Button) findViewById(R.id.HelpButtonCancel);
	    this.cancel.setOnClickListener(this);

    }
    
    @Override
	protected void onStop() {
		Log.i("Help", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("Help", "onDestroy ");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("Help", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("Help", "onPause ");
		super.onPause();
	}
    
	public void onClick(View v) {
    	
    	Intent intent = null;
    	
    	if( v == this.cancel){
			intent = new Intent(Help.this, Home.class);
		}
		
		if (intent != null) {
			startActivity(intent);
			finish();
		}
    }
}
