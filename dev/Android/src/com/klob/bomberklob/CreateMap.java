package com.klob.bomberklob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.klob.bomberklob.engine.ObjectsGallery;
import com.klob.bomberklob.model.Model;

public class CreateMap extends Activity implements View.OnClickListener {
	
	private Model model;
	
	private Button load;
	private Button cancel;
	private Button validate;
	
	
	private ObjectsGallery og;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.createmap);

        this.model = Model.getInstance();
		
		this.load = (Button) findViewById(R.id.CreateMapButtonLoad);
		this.load.setOnClickListener(this);
		
		this.cancel = (Button) findViewById(R.id.CreateMapButtonCancel);
		this.cancel.setOnClickListener(this);
		
		this.validate = (Button) findViewById(R.id.CreateMapButtonValidate);
		this.validate.setOnClickListener(this);
		
        
        this.og = (ObjectsGallery) findViewById(R.id.FrameLayoutTest);
    }
    
    @Override
	protected void onStop() {
		Log.i("CreateMap", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("CreateMap", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("CreateMap", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("CreateMap", "onPause ");
		super.onPause();
	}  

	@Override
	public void onClick(View v) {
		
		Intent intent = null;
		
		if ( this.validate == v ) {
			intent = new Intent(CreateMap.this, MapEditor.class);
		}
		else if ( this.cancel == v ) {
			intent = new Intent(CreateMap.this, Home.class);
		}
		else if ( this.load == v ) {
			//???
		}

		
		if ( null != intent) {
			startActivity(intent);
			this.finish();
		}
	}

}

