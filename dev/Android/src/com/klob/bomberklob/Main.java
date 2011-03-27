package com.klob.bomberklob;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
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
		
		setVolumeControlStream(this.model.getSystem().getVolume()); //FIXME correct ?
		
		Intent intent = null;
		Resources res = this.getResources();
		Configuration conf = res.getConfiguration();
		conf.locale = this.model.getSystem().getLocalLanguage();
		res.updateConfiguration(conf, res.getDisplayMetrics());
		try {
			Context context = this.createPackageContext(getPackageName(), Context.CONTEXT_INCLUDE_CODE);
			
			if ( this.model.getSystem().getLastUser() == -1 ) {
				intent = new Intent(context, CreateAccountOffline.class);
			}
			else {
				intent = new Intent(context, Home.class);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    	
		if ( intent != null ) {
			startActivity(intent);
			this.finish();	
		}
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