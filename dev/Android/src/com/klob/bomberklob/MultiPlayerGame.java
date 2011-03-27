package com.klob.bomberklob;

import java.io.IOException;

import com.klob.bomberklob.R;
import com.klob.bomberklob.model.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class MultiPlayerGame extends Activity implements View.OnClickListener {

	private Model model;
	
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
        
        try {
			this.model = Model.getInstance(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		this.cancel = (Button)findViewById(R.id.MultiPlayerGameButtonCancel);
		this.cancel.setOnClickListener(this);
		
		this.connection = (Button)findViewById(R.id.MultiPlayerGameButtonConnection);
		this.connection.setOnClickListener(this);
		
		this.newAccount = (Button)findViewById(R.id.MultiPlayerGameNewAccount);
		this.newAccount.setOnClickListener(this);
		
		this.userPseudo = (TextView) findViewById(R.id.MultiPlayerGameUserName);
		this.userPseudo.setText(this.model.getUser().getPseudo());
		
		this.password = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxPassword);
		this.password.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked) {
					model.getUser().setRemenberPassword(true);
				}
				else {
					model.getUser().setRemenberPassword(false);
				}
			}
		});
		
		if ( this.model.getUser().getRemenberPassword() ) {
			this.password.setChecked(true);
		}

		this.userAccountName = (EditText) findViewById(R.id.MultiPlayerGameEditTextName);
		this.userAccountName.setText(this.model.getUser().getUserName());
		
		this.userAccountPassword = (EditText) findViewById(R.id.MultiPlayerGameEditTextPassword);
		this.userAccountPassword.setText(this.model.getUser().getPassword());
		
		this.connectionAuto = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxConnection);
		this.connectionAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked && !userAccountPassword.getText().toString().equals("") && !userAccountName.getText().toString().equals("")) {
					// FIXME encoder le mdp
					model.getUser().setPassword(userAccountPassword.getText().toString());
					model.getUser().setUserName(userAccountName.getText().toString());
					model.getUser().setConnectionAuto(true);
				}
				else {
					connectionAuto.setChecked(false);
					model.getUser().setConnectionAuto(false);
					Toast.makeText(MultiPlayerGame.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
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
		
		this.model.getUser().setUserName(this.userAccountName.getText().toString());
		this.model.getSystem().getDatabase().updateUser(this.model.getUser());
		
		if(view == this.connection){
			if ( this.model.getUser().getRemenberPassword() ) {
				
			}
			//FIXME Appeler le serveur
		}
		else if(view == this.newAccount){
			intent = new Intent(MultiPlayerGame.this, NewAccountOnLine.class);
		}
		else if(view == this.cancel){
			intent = new Intent(MultiPlayerGame.this, Home.class);
		}
		
		if ( intent != null ) {
			startActivity(intent);
			this.finish();
		}		
	}
}
