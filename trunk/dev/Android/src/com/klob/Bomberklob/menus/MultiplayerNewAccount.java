package com.klob.Bomberklob.menus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

public class MultiplayerNewAccount  extends Activity implements View.OnClickListener{

	private TextView userPseudo;
	private EditText userAccountName;
	private EditText userAccountPassword1;
	private EditText userAccountPassword2;

	private CheckBox password;
	private CheckBox connectionAuto;

	private Button validate;
	private Button cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.multiplayernewaccount);
		
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

		this.userPseudo = (TextView) findViewById(R.id.NewAccountOnLineUserName);
		this.userPseudo.setText(Model.getUser().getPseudo());

		this.userAccountName = (EditText) findViewById(R.id.NewAccountOnLineEditTextName);
		this.userAccountName.setText(Model.getUser().getUserName());
		this.userAccountName.setFilters(new InputFilter[]{filter});
		
		this.userAccountPassword1 = (EditText) findViewById(R.id.NewAccountOnLineEditTextPassword1);
		this.userAccountPassword1.setFilters(new InputFilter[]{filter});
		
		this.userAccountPassword2 = (EditText) findViewById(R.id.NewAccountOnLineEditTextPassword2);
		this.userAccountPassword2.setFilters(new InputFilter[]{filter});

		this.validate = (Button)findViewById(R.id.NewAccountOnLineButtonConnection);
		this.validate.setOnClickListener(this);

		this.cancel = (Button)findViewById(R.id.NewAccountOnLineButtonCancel);
		this.cancel.setOnClickListener(this);

		this.password = (CheckBox) findViewById(R.id.NewAccountOnLineCheckBoxPassword);
		this.password.setOnClickListener(this);
//		this.password.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
//
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
//				if (isChecked) {
//					Model.getUser().setRememberPassword(true);
//				}
//				else {
//					Model.getUser().setRememberPassword(false);
//				}
//				Model.getSystem().getDatabase().updateUser(Model.getUser());
//			}
//		});

//		if ( Model.getUser().getRememberPassword() ) {
//			this.password.setChecked(true);
//		}

		this.connectionAuto = (CheckBox) findViewById(R.id.NewAccountOnLineCheckBoxConnection);
		this.connectionAuto.setOnClickListener(this);
//		this.connectionAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
//
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
//				if (isChecked && !userAccountPassword1.getText().toString().equals("") && !userAccountName.getText().toString().equals("")) {
//					// FIXME encoder le mdp
//					Model.getUser().setPassword(userAccountPassword1.getText().toString());
//					Model.getUser().setUserName(userAccountName.getText().toString());
//					Model.getUser().setConnectionAuto(true);
//				}
//				else {
//					connectionAuto.setChecked(false);
//					Model.getUser().setConnectionAuto(false);
//					Toast.makeText(NewAccountOnLine.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
//				}
//				Model.getSystem().getDatabase().updateUser(Model.getUser());
//			}
//		});
	}

	@Override
	protected void onStop() {
		Log.i("NewAccountOnLine", "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy(){
		Log.i("NewAccountOnLine", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onResume(){
		Log.i("NewAccountOnLine", "onResume");
		super.onResume();
	}

	@Override
	protected void onPause(){
		Log.i("NewAccountOnLine", "onPause");
		super.onPause();
	}
	
	private boolean testerString(String chaine){
		if(chaine.contains("\t") || chaine.contains("\n") || chaine.contains("\r") || chaine.contains(" ") || chaine.equals("")){
			return false;
		}
		return true;
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


	public void onClick(View view) {

		Intent intent = null;
		

		if( view == this.validate ) {
//			if ( !this.userAccountName.getText().toString().equals("") && !this.userAccountPassword1.getText().toString().equals("")) {
//				if ( !this.userAccountPassword1.getText().toString().equals(this.userAccountPassword2.getText().toString()) ) {
//					Model.getUser().setUserName(this.userAccountName.getText().toString());
//					
//					if (Model.getUser().getRememberPassword()) {
//						// FIXME Encoder le mdp
//						Model.getUser().setPassword(this.userAccountPassword1.getText().toString());
//					}
//					
//					Model.getSystem().getDatabase().updateUser(Model.getUser());
//					//FIXME Connexion avec le serveur
//				}
//				else {
//					//FIXME
//				}
//			}
//			else {
//				Toast.makeText(NewAccountOnLine.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
//			}
			
			if( !testerString(userAccountName.getText().toString()) || !testerString(userAccountPassword1.getText().toString()) || !testerString(userAccountPassword2.getText().toString())){
	 			Toast.makeText(MultiplayerNewAccount.this, R.string.ErrorAutoConnection, Toast.LENGTH_SHORT).show();
	 		}
			/** TODO factorisation a faire **/
//	 		else if(password.getText().toString().compareTo("")!=0 
//			&& (repassword.getText().toString().compareTo("")!=0) && (userName.getText().toString().compareTo("")!=0)){
			else if(!userAccountPassword1.getText().toString().equals(userAccountPassword2.getText().toString())){
					Toast.makeText(MultiplayerNewAccount.this, R.string.ErrorPassword,Toast.LENGTH_SHORT).show();
				}
				else{
					/** test disponibilité sur serveur **/
					/** TODO insertion compte sur serveur	**/
					int userId = Model.getSystem().getDatabase().getLastUserId();
					String pwd =  md5(userAccountPassword1.getText().toString());

					Model.getSystem().getDatabase().addAccountMulti(userId, userAccountName.getText().toString(), pwd);
					Model.getUser().setUserName(userAccountName.getText().toString());
					Model.getUser().setPassword(pwd);
					
					/** save password **/
					if(password.isChecked()){
							Model.getUser().setRememberPassword(true);
							Model.getSystem().getDatabase().updateSavePwdUser(userId, 1);
					}
					/** auto connect **/
					if(connectionAuto.isChecked()){
							Model.getUser().setConnectionAuto(true);
							Model.getUser().setRememberPassword(true);
							Model.getSystem().getDatabase().updateAutoConnectUser(userId, 1);
					}
					
					Toast.makeText(MultiplayerNewAccount.this, "Inscription réalisée avec succès", Toast.LENGTH_SHORT).show();
						intent = new Intent(MultiplayerNewAccount.this, MultiplayerHome.class);
						startActivity(intent);
				}
			
		}
		else if( view == this.cancel ) {
			intent = new Intent(MultiplayerNewAccount.this, Multiplayer.class);
		}

		if ( intent != null ) {
			startActivity(intent);
			this.finish();
		}
	}
}
