package com.klob.Bomberklob;

import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Main extends Activity {

	private Thread mainthread;
	private Handler handler;
	private Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		this.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if ( msg.what == 0 ) {
					if ( intent != null ) {
						startActivity(intent);
						finish();	
					}
				}
			}
		};

		this.mainthread = new Thread() {
			@Override
			public void run() {

				Model.setInstance(getApplicationContext());

				setVolumeControlStream(Model.getSystem().getVolume());

				Resources res = getResources();
				Configuration conf = res.getConfiguration();
				conf.locale = Model.getSystem().getLocalLanguage();
				res.updateConfiguration(conf, res.getDisplayMetrics());
				try {
					Context context = createPackageContext(getPackageName(), Context.CONTEXT_INCLUDE_CODE);
					
					ResourcesManager.setInstance(getApplicationContext());
					ResourcesManager.animatedObjectsInitialisation();
					ResourcesManager.inanimatedObjectsInitialisation();
					ResourcesManager.playersInitialisation();
					
					if ( Model.getSystem().getLastUser() == -1 ) {
						intent = new Intent(context, CreateAccountOffline.class);
					}
					else {
						intent = new Intent(context, Home.class);
					}
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				
				handler.sendMessage(handler.obtainMessage(0));
			};
		};
		this.mainthread.start();
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