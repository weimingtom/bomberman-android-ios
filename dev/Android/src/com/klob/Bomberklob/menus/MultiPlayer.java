package com.klob.Bomberklob.menus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
        
        setContentView(R.layout.multiplayer);
        
		this.cancel = (Button)findViewById(R.id.MultiPlayerGameButtonCancel);
		this.cancel.setOnClickListener(this);
		
		this.connection = (Button)findViewById(R.id.MultiPlayerGameButtonConnection);
		this.connection.setOnClickListener(this);
		
		this.newAccount = (Button)findViewById(R.id.MultiPlayerGameNewAccount);
		this.newAccount.setOnClickListener(this);
		
		this.userPseudo = (TextView) findViewById(R.id.MultiPlayerGameUserName);
		this.userPseudo.setText(Model.getUser().getPseudo());
		
		this.password = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxPassword);
		Log.i("","::::: " + Model.getUser().getRememberPassword());
		if(Model.getUser().getRememberPassword()){
			password.setChecked(true);
			userAccountName.setText(Model.getUser().getUserName());
			Log.i("", " mot de passe de remplissage: "+ Model.getUser().getPassword());
			password.setText(Model.getUser().getPassword());
			
		}
		
//		if ( Model.getUser().getRememberPassword() ) {
//			this.password.setChecked(true);
//		}
		
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

		this.userAccountName = (EditText) findViewById(R.id.MultiPlayerGameEditTextName);
//		this.userAccountName.setText(Model.getUser().getUserName());
		this.userAccountName.setFilters(new InputFilter[]{filter});
		
		this.userAccountPassword = (EditText) findViewById(R.id.MultiPlayerGameEditTextPassword);
//		this.userAccountPassword.setText(Model.getUser().getPassword());
		this.userAccountPassword.setFilters(new InputFilter[]{filter});
		
		this.connectionAuto = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxConnection);
//		this.connectionAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
//
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
//				if (isChecked && userAccountPassword.getText().toString().equals("") || userAccountName.getText().toString().equals("")) {
//					connectionAuto.setChecked(false);
//					Toast.makeText(MultiPlayer.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
//				}
//				else {
//					password.setChecked(true);
//				}
//			}
//		});
		if(Model.getUser().getConnectionAuto()){
			int userId = Model.getSystem().getDatabase().getLastUserId();
			String userName = Model.getUser().getUserName();
			String password = Model.getUser().getPassword();
			try {
				if (Model.getSystem().getDatabase().isGoodMultiUser(userId, userName, password)) {
					Intent intent = new Intent(MultiPlayer.this, HomeMulti.class);
					startActivity(intent);				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	private static final String md5(final String password) {
	    try {
	        
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(password.getBytes());
	        byte messageDigest[] = digest.digest();
	 
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++) {
	            String h = Integer.toHexString(0xFF & messageDigest[i]);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();
	 
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}

	private boolean testString(String chaine){
		if(chaine.contains("\t") || chaine.contains("\n") || chaine.contains("\r") || chaine.contains(" ") || chaine.equals("")){
			return false;
		}
		return true;
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
	
	public void onClick(View view) {
		
		Intent intent = null;
		boolean bool = false;
		
//		// Si le nom d'utilisateur a été mit à jour
//		if ( !this.userAccountName.getText().toString().equals("") && !Model.getUser().getUserName().equals(this.userAccountName.getText().toString()) && this.userAccountName.getText().toString().indexOf(" ") == -1) {		
//			Model.getUser().setUserName(this.userAccountName.getText().toString());
//			bool = true;
//		}
//		
//		// Si la checkbox de mémorisation du mot de passe a été cochée
//		if ( this.password.isChecked() ) {
//			if ( !Model.getUser().getRememberPassword() ) {
//				Model.getUser().setRememberPassword(true);
//				bool = true;
//			}
//			
//			if ( !this.userAccountPassword.getText().toString().equals("") && !this.userAccountPassword.equals(Model.getUser().getPassword())) {
//				Model.getUser().setPassword(this.userAccountPassword.getText().toString());
//				bool = true;
//			}
//		}
//		else {
//			if ( Model.getUser().getRememberPassword() ) {
//				Model.getUser().setRememberPassword(false);
//				Model.getUser().setPassword("");
//				bool = true;
//			}
//		}
//		
//		// Si la checkbox de connexion auto a été cochée
//		if ( this.connectionAuto.isChecked() ) {
//			if ( !Model.getUser().getConnectionAuto() ) {
//				Model.getUser().setConnectionAuto(true);
//				bool = true;
//			}
//		}
//		
//		if (bool) {
//			Model.getSystem().getDatabase().updateUser(Model.getUser());
//		}
//		
//		if(view == this.connection){
//			if ( !Model.getUser().getUserName().equals("") && !Model.getUser().getPassword().equals("")) {
//				//FIXME Appeler le serveur
//			}
//			else {
//				Toast.makeText(MultiPlayer.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
//			}
//		}
		if( view == connection){
			try {
				int userId = Model.getSystem().getDatabase().getLastUserId();
				String pwd = md5(userAccountPassword.getText().toString());
				
//				Log.i("lastUserId", ">>> " + Model.getSystem().getDatabase().getUserIdByName(Model.getUser().getPseudo()) + " >>> " + Model.getUser().getPseudo() + " >>> " + Model.getUser().getPassword());
//				Log.i("lastUserIdSystemPassword", ">>> " + Model.getSystem().getDatabase().getUser(userId).getPassword() );
//				Log.i("pwd lol ", md5("lol"));
				
				if( !testString(userAccountName.getText().toString()) || !testString(userAccountPassword.getText().toString()) ){

		 			Toast.makeText(MultiPlayer.this, R.string.MultiPlayerConnectionErrorAutoConnection, Toast.LENGTH_SHORT).show();
		 		}
				else if (Model.getSystem().getDatabase().isGoodMultiUser(userId, userAccountName.getText().toString(), pwd)){
					/** save password **/
					if(password.isChecked()){
							Model.getUser().setRememberPassword(true);
							Model.getSystem().getDatabase().updateSavePwdUser(userId, 1);
					}
					else if (!password.isChecked()) {
						Model.getUser().setRememberPassword(false);
						Model.getSystem().getDatabase().updateSavePwdUser(userId, 0);
					}
					/** auto connect **/
					if(connectionAuto.isChecked()){
							Model.getUser().setConnectionAuto(true);
							Model.getUser().setRememberPassword(true);
							Model.getSystem().getDatabase().updateAutoConnectUser(userId, 1);
					}
					else if (!connectionAuto.isChecked()) {
						Model.getUser().setConnectionAuto(false);
						Model.getSystem().getDatabase().updateAutoConnectUser(userId, 0);
					}
					intent = new Intent(MultiPlayer.this, HomeMulti.class);
					startActivity(intent);
				}
				else{

					Toast.makeText(MultiPlayer.this, R.string.MultiPlayerConnectionError, Toast.LENGTH_SHORT).show();
				}
			} catch (SQLException e) {

				Toast.makeText(MultiPlayer.this, R.string.MultiPlayerConnectionError, Toast.LENGTH_SHORT).show();
				e.printStackTrace();
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
