package com.klob.bomberklob;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.klob.bomberklob.model.Model;

public class Main extends Activity {

	private Model model;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		try {
			this.model = Model.getInstance(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Intent intent;
		
		if ( this.model.getSystem().getDatabase().getLastUser() == -1 ) {
			intent = new Intent(Main.this, CreateAccountOffline.class);
		}
		else {
			intent = new Intent(Main.this, Home.class);
		}
		startActivity(intent);
		this.finish();	
	}
	
    @Override
	protected void onStop() {
		Log.i("Main", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("Main", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("Main", "onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("Main", "onPause");
		super.onPause();
	} 
}