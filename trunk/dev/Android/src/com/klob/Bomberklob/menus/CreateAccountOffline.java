package com.klob.Bomberklob.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;

public class CreateAccountOffline extends Activity implements View.OnClickListener{
	
	private EditText pseudo;
	private Button validate;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
        setContentView(R.layout.createaccountoffline);
		
		InputFilter filter = new InputFilter() {

			public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
		        for (int i = start; i < end; i++) { 
		             if (!Character.isLetterOrDigit(source.charAt(i)) && Character.isSpaceChar(source.charAt(i))) { 
		                 return "";     
		             }     
		        }		       
		        return null;   
		    }  
		};
        
		this.pseudo = (EditText) findViewById(R.id.CreateAccountOfflineEditText);
		this.pseudo.setFilters(new InputFilter[]{filter});
        
		this.validate = (Button)findViewById(R.id.CreateAccountOfflineButton);
		this.validate.setOnClickListener(this);
	}
	
	@Override
	protected void onStop() {
		Log.i("CreateAccountOffline", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("CreateAccountOffline", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("CreateAccountOffline", "onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("CreateAccountOffline", "onPause");
		super.onPause();
	}
	
	
	public void onClick(View view) {

		if(validate == view) {
			String pseudo = this.pseudo.getText().toString();
			
			if ( !pseudo.equals("") ) { // FIXME Rajouter une taille min ?
				Model.getSystem().getDatabase().newAccount(pseudo);
				Model.getSystem().getDatabase().setLastUser(pseudo);
				Model.getSystem().setLastUser();
				Model.setUser(pseudo);
				Intent intent = new Intent(CreateAccountOffline.this, Home.class);
				startActivity(intent);
				this.finish();
			}
			else {
				Toast.makeText(CreateAccountOffline.this, "Invalid Username", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
