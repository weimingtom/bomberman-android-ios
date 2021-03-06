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

public class NewAccountOffline extends Activity implements View.OnClickListener{
	
	private EditText pseudo;
	private Button cancel;
	private Button validate;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        
        setContentView(R.layout.newaccountoffline);
		
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
        
		this.pseudo = (EditText) findViewById(R.id.NewAccountOfflineEditText);
		this.cancel   = (Button) findViewById(R.id.NewAccountOfflineButtonCancel);
		this.validate = (Button) findViewById(R.id.NewAccountOfflineButtonOk);
		
		this.pseudo.setFilters(new InputFilter[]{filter});
		this.validate.setOnClickListener(this);
		if ( Model.getSystem().getLastUser() == -1 ) {
			this.cancel.setVisibility(View.INVISIBLE);
		}
		else {
			this.cancel.setOnClickListener(this);
		}		
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
	
	public void onClick(View view) {
		
		Intent intent = null;

		if(this.validate == view) {
			String pseudo = this.pseudo.getText().toString();
			
			if ( !pseudo.equals("") ) { // FIXME Rajouter une taille min ?
				if ( !Model.getSystem().getDatabase().existingAccount(pseudo)) {
					Model.getSystem().getDatabase().newAccount(pseudo);
					Model.getSystem().getDatabase().setLastUser(pseudo);
					Model.getSystem().setLastUser();
					Model.setUser(pseudo);
					intent = new Intent(NewAccountOffline.this, Home.class);
				}
				else {
					Toast.makeText(NewAccountOffline.this, R.string.ErrorPseudo, Toast.LENGTH_SHORT).show();
				}
			}
			else {
				Toast.makeText(NewAccountOffline.this, R.string.UserName, Toast.LENGTH_SHORT).show();
			}
		}
		else if (this.cancel == view) {
			intent = new Intent(NewAccountOffline.this, Home.class);		
		}
		
		if (intent != null ) {
			startActivity(intent);
			this.finish();	
		}
	}
}