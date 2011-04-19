package com.klob.Bomberklob.menus;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;

public class MultiPlayer extends Activity implements View.OnClickListener {
	
	private Button cancel;
	private Button connection;
	private Button newAccount;
	private TextView userPseudo;
	private EditText userAccountName;
	private EditText userAccountPassword;
	private CheckBox password;
	private CheckBox connectionAuto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.multiplayergame);
        
		this.cancel = (Button)findViewById(R.id.MultiPlayerGameButtonCancel);
		this.cancel.setOnClickListener(this);
		
		this.connection = (Button)findViewById(R.id.MultiPlayerGameButtonConnection);
		this.connection.setOnClickListener(this);
		
		this.newAccount = (Button)findViewById(R.id.MultiPlayerGameNewAccount);
		this.newAccount.setOnClickListener(this);
		
		this.userPseudo = (TextView) findViewById(R.id.MultiPlayerGameUserName);
		this.userPseudo.setText(Model.getUser().getPseudo());
		
		this.password = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxPassword);
		
		if ( Model.getUser().getRememberPassword() ) {
			this.password.setChecked(true);
		}
		
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

		this.userAccountName = (EditText) findViewById(R.id.MultiPlayerGameEditTextName);
		this.userAccountName.setText(Model.getUser().getUserName());
		this.userAccountName.setFilters(new InputFilter[]{filter});
		
		this.userAccountPassword = (EditText) findViewById(R.id.MultiPlayerGameEditTextPassword);
		this.userAccountPassword.setText(Model.getUser().getPassword());
		this.userAccountPassword.setFilters(new InputFilter[]{filter});
		
		this.connectionAuto = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxConnection);
		this.connectionAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked && userAccountPassword.getText().toString().equals("") || userAccountName.getText().toString().equals("")) {
					connectionAuto.setChecked(false);
					Toast.makeText(MultiPlayer.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
				}
				else {
					password.setChecked(true);
				}
			}
		});
	}

	
	@Override
	protected void onStop() {
		Log.i("MultiPlayerGame", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("MultiPlayerGame", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("MultiPlayerGame", "onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("MultiPlayerGame", "onPause");
		super.onPause();
	}
	
	@Override
	public void onClick(View view) {
		
		Intent intent = null;
		boolean bool = false;
		
		// Si le nom d'utilisateur a été mit à jour
		if ( !this.userAccountName.getText().toString().equals("") && !Model.getUser().getUserName().equals(this.userAccountName.getText().toString()) && this.userAccountName.getText().toString().indexOf(" ") == -1) {		
			Model.getUser().setUserName(this.userAccountName.getText().toString());
			bool = true;
		}
		
		// Si la checkbox de mémorisation du mot de passe a été cochée
		if ( this.password.isChecked() ) {
			if ( !Model.getUser().getRememberPassword() ) {
				Model.getUser().setRememberPassword(true);
				bool = true;
			}
			
			if ( !this.userAccountPassword.getText().toString().equals("") && !this.userAccountPassword.equals(Model.getUser().getPassword())) {
				Model.getUser().setPassword(this.userAccountPassword.getText().toString());
				bool = true;
			}
		}
		else {
			if ( Model.getUser().getRememberPassword() ) {
				Model.getUser().setRememberPassword(false);
				Model.getUser().setPassword("");
				bool = true;
			}
		}
		
		// Si la checkbox de connexion auto a été cochée
		if ( this.connectionAuto.isChecked() ) {
			if ( !Model.getUser().getConnectionAuto() ) {
				Model.getUser().setConnectionAuto(true);
				bool = true;
			}
		}
		
		if (bool) {
			Model.getSystem().getDatabase().updateUser(Model.getUser());
		}
		
		if(view == this.connection){
			if ( !Model.getUser().getUserName().equals("") && !Model.getUser().getPassword().equals("")) {
				//FIXME Appeler le serveur
			}
			else {
				Toast.makeText(MultiPlayer.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
			}
		}
		else if(view == this.newAccount){
			intent = new Intent(MultiPlayer.this, NewAccountOnLine.class);
		}
		else if(view == this.cancel){
			intent = new Intent(MultiPlayer.this, Home.class);
		}
		
		if ( intent != null ) {
			startActivity(intent);
			this.finish();
		}		
	}
}
