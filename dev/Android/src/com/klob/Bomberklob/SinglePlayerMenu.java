package com.klob.Bomberklob;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SinglePlayerMenu extends Activity implements View.OnClickListener {

	private Button options, quit, restart, resume;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.singleplayermenu);
        
        this.resume = (Button) findViewById(R.id.SinglePlayerMenuResume);
        this.resume.setOnClickListener(this);
        
        this.options = (Button) findViewById(R.id.SinglePlayerMenuOptions);
        this.options.setOnClickListener(this);
        
        this.quit = (Button) findViewById(R.id.SinglePlayerMenuQuit);
        this.quit.setOnClickListener(this);
        
        this.restart = (Button) findViewById(R.id.SinglePlayerMenuRestart);
        this.restart.setOnClickListener(this);

    }
	
    @Override
	protected void onStop() {
		Log.i("SinglePlayerMenu", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("SinglePlayerMenu", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("SinglePlayerMenu", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("SinglePlayerMenu", "onPause ");
		super.onPause();
	}
	
	@Override
	public void onClick(View arg0) {
		
		if ( arg0 == this.resume) {
			this.setResult(2000);
		}
		else if ( arg0 == this.options) {
			this.setResult(2001);
		}
		else if ( arg0 == this.restart) {
			this.setResult(2002);
		}
		else if ( arg0 == this.quit) {
			this.setResult(2003);
		}
		this.finish();
	}
}
