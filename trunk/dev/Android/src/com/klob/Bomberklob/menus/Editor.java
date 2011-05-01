package com.klob.Bomberklob.menus;

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

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.editor.EditorLayout;
import com.klob.Bomberklob.model.Model;

public class Editor extends Activity implements View.OnClickListener {
	
	private Button load;
	private Button cancel;
	private Button validate;
	
	private EditText mapName;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mapselector);
        
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
		Log.i("Editor", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("Editor", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("Editor", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("Editor", "onPause ");
		super.onPause();
	}  


	public void onClick(View v) {
		
		Intent intent = null;
		
		if ( this.validate == v ) {
			String name = this.mapName.getText().toString();
			
			if ( !name.equals("") && !Model.getSystem().getDatabase().existingMap(name) ) {
				intent = new Intent(Editor.this, EditorLayout.class);
				intent.putExtra("map", name);
			}
			else {
				Toast.makeText(Editor.this, R.string.CreateMapErrorName, Toast.LENGTH_SHORT).show();
			}
		}
		else if ( this.cancel == v ) {
			intent = new Intent(Editor.this, Home.class);
		}
		
		if ( null != intent) {
			startActivity(intent);
			this.finish();
		}
		
		if ( this.load == v ) {
			intent = new Intent(Editor.this, EditorLoader.class);
			startActivityForResult(intent, 1000);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if ( resultCode == 2000 ) {
			Intent intent = new Intent(Editor.this, EditorLayout.class);
			intent.putExtra("map", data.getStringExtra("map"));
			startActivity(intent);
			this.finish();
		}
		else if ( resultCode == 2001 ){
			Toast.makeText(Editor.this, R.string.CreateMapErrorLoad, Toast.LENGTH_SHORT).show();
		}
	}
}
