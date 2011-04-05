package com.klob.Bomberklob;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MapEditorMenu extends Activity implements View.OnClickListener {

	private Button reset, quit, save, resume;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mapeditormenu);
        
        this.resume = (Button) findViewById(R.id.MapEditorMenuResume);
        this.resume.setOnClickListener(this);
        
        this.reset = (Button) findViewById(R.id.MapEditorMenuReset);
        this.reset.setOnClickListener(this);
        
        this.quit = (Button) findViewById(R.id.MapEditorMenuQuit);
        this.quit.setOnClickListener(this);
        
        this.save = (Button) findViewById(R.id.MapEditorMenuSave);
        this.save.setOnClickListener(this);

    }
    
    @Override
	protected void onStop() {
		Log.i("MapEditorMenu", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("MapEditorMenu", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("MapEditorMenu", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("MapEditorMenu", "onPause ");
		super.onPause();
	}
	
	@Override
	public void onClick(View arg0) {
		
		if ( arg0 == this.resume) {
			this.setResult(2000);
		}
		else if ( arg0 == this.save) {
			this.setResult(2001);
		}
		else if ( arg0 == this.reset) {
			this.setResult(2002);
		}
		else if ( arg0 == this.quit) {
			this.setResult(2003);
		}
		this.finish();
	}

}
