package com.klob.bomberklob;

import com.klob.bomberklob.model.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewAccountOffline extends Activity implements View.OnClickListener{

	private Model model;
	
	private EditText pseudo;
	private Button cancel;
	private Button validate;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.newaccountoffline);
        
        this.model = Model.getInstance(this);
		
		InputFilter filter = new InputFilter() {
		    @Override
			public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) {
		    	
		        for (int i = start; i < end; i++) { 
		             if (!Character.isLetterOrDigit(source.charAt(i)) && Character.isSpaceChar(source.charAt(i))) { 
		                 return "";     
		             }     
		        }		       
		        return null;   
		    }  
		};
        
		this.pseudo = (EditText) findViewById(R.id.NewAccountOfflineEditText);
		this.cancel   = (Button) findViewById(R.id.NewAccountOfflineButtonCancel);
		this.validate = (Button) findViewById(R.id.NewAccountOfflineButtonOk);
		
		this.pseudo.setFilters(new InputFilter[]{filter});
		this.validate.setOnClickListener(this);
		this.cancel.setOnClickListener(this);
	}
	
	@Override
	protected void onStop() {
		Log.i("NewAccountOffline", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("NewAccountOffline", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("NewAccountOffline", "onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("NewAccountOffline", "onPause");
		super.onPause();
	}
	
	
	@Override
	public void onClick(View view) {
		
		Intent intent = null;

		if(this.validate == view) {
			String pseudo = this.pseudo.getText().toString();
			
			if ( !pseudo.equals("") ) { // FIXME Rajouter une taille min ?
				pseudo = pseudo.toLowerCase();
				if ( this.model.getSystem().getDatabase().existingAccount(pseudo)) {
					this.model.getSystem().getDatabase().newAccount(pseudo);
					this.model.getSystem().getDatabase().setLastUser(pseudo);
					this.model.getSystem().setLastUser();
					this.model.setUser(pseudo);
					intent = new Intent(NewAccountOffline.this, Home.class);
					startActivity(intent);
					this.finish();
				}
				else {
					Toast.makeText(NewAccountOffline.this, "Username already exists", Toast.LENGTH_SHORT).show();
				}
			}
			else {
				Toast.makeText(NewAccountOffline.this, "Invalid Username", Toast.LENGTH_SHORT).show();
			}
		}
		else if (this.cancel == view) {
			intent = new Intent(NewAccountOffline.this, Home.class);
			startActivity(intent);
			this.finish();			
		}
	}
}