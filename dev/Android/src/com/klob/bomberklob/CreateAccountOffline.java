package com.klob.bomberklob;

import java.io.IOException;

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

public class CreateAccountOffline extends Activity implements View.OnClickListener{

	private Model model;
	
	private EditText pseudo;
	private Button validate;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.createaccountoffline);
        
        try {
			this.model = Model.getInstance(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	
	@Override
	public void onClick(View view) {

		if(validate == view) {
			String pseudo = this.pseudo.getText().toString();
			
			if ( !pseudo.equals("") ) { // FIXME Rajouter une taille min ?
				pseudo = pseudo.toLowerCase();
				this.model.getSystem().getDatabase().newAccount(pseudo);
				this.model.getSystem().getDatabase().setLastUser(pseudo);
				this.model.getSystem().setLastUser();
				this.model.setUser(pseudo);
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
