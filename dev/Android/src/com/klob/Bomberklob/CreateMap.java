package com.klob.Bomberklob;

import com.klob.Bomberklob.model.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateMap extends Activity implements View.OnClickListener {
	
	private Button load;
	private Button cancel;
	private Button validate;
	
	private EditText mapName;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.createmap);
        
        this.mapName = (EditText) findViewById(R.id.CreateMapEditTextMapName);
		
		this.load = (Button) findViewById(R.id.CreateMapButtonLoad);
		this.load.setOnClickListener(this);
		
		this.cancel = (Button) findViewById(R.id.CreateMapButtonCancel);
		this.cancel.setOnClickListener(this);
		
		this.validate = (Button) findViewById(R.id.CreateMapButtonValidate);
		this.validate.setOnClickListener(this);

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
			String name = this.mapName.getText().toString();
			
			if ( !name.equals("") && !Model.getSystem().getDatabase().existingMap(name) ) {
				intent = new Intent(CreateMap.this, MapEditorLayout.class);
				intent.putExtra("map", name);
			}
			else {
				Toast.makeText(CreateMap.this, R.string.CreateMapErrorName, Toast.LENGTH_SHORT).show();
			}
		}
		else if ( this.cancel == v ) {
			intent = new Intent(CreateMap.this, Home.class);
		}
		
		if ( null != intent) {
			startActivity(intent);
			this.finish();
		}
		
		if ( this.load == v ) {
			intent = new Intent(CreateMap.this, MapEditorLoader.class);
			startActivityForResult(intent, 1000);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if ( resultCode == 2000 ) {
			Intent intent = new Intent(CreateMap.this, MapEditorLayout.class);
			intent.putExtra("map", data.getStringExtra("map"));
			startActivity(intent);
			this.finish();
		}
		else if ( resultCode == 2001 ){
			Toast.makeText(CreateMap.this, R.string.CreateMapErrorLoad, Toast.LENGTH_SHORT).show();
		}
	}
}