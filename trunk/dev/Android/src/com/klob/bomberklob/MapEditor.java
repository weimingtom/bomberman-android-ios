package com.klob.bomberklob;

import com.klob.bomberklob.engine.EditorView;
import com.klob.bomberklob.model.Model;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MapEditor extends Activity implements View.OnClickListener {

	private Model model;
	private EditorView editorView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mapeditor);
        
        this.model = Model.getInstance();
    }
    
    @Override
	protected void onStop() {
		Log.i("MapEditor", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("MapEditor", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("MapEditor", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("MapEditor", "onPause ");
		super.onPause();
	}  
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
