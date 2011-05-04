package com.klob.Bomberklob.menus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MultiplayerChangeAccount extends Activity implements View.OnClickListener {
	private Button cancel,valid;
	private EditText userName, password;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayerchangeaccount);
        
        cancel = (Button)findViewById(R.id.buttonCancel);
        cancel.setOnClickListener(this);
		
		valid = (Button)findViewById(R.id.buttonValid);
		valid.setOnClickListener(this);
		
		userName = (EditText)findViewById(R.id.editUserName);
		password = (EditText)findViewById(R.id.editPassword);
	}
	
	@Override
	protected void onStop() {
		Log.i("", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("", "onDestroy ");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("", "onPause ");
		super.onPause();
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
	
	private boolean testerString(String chaine){
		if(chaine.contains("\t") || chaine.contains("\n") || chaine.contains("\r") || chaine.contains(" ") || chaine.equals("")){
			return false;
		}
		return true;
	}
	
	/** 
	 * FIXME vérification à faire avec serveur ou bd locale ? 
	 * 		si local comment determiner couple username/password valide puisque userId !=
	 * TODO faire la liaison avec le serveur
	 */
	public void onClick(View view) {
		Intent intent = null;
		if(view == valid){
			try {
				int userId = Model.getSystem().getDatabase().getLastUserId();
				String pwd = md5(password.getText().toString());
				
				if( !testerString(userName.getText().toString()) || !testerString(password.getText().toString()) ){
		 			Toast.makeText(MultiplayerChangeAccount.this, R.string.ErrorAutoConnection, Toast.LENGTH_SHORT).show();
		 		}
				else if (Model.getSystem().getDatabase().isGoodMultiUser(userId, userName.getText().toString(), pwd)){
					/** FIXME modif et test à faire ici **/
					intent = new Intent(MultiplayerChangeAccount.this, ProfileManager.class);
				}
				else{
					Toast.makeText(MultiplayerChangeAccount.this, R.string.ErrorIdentification, Toast.LENGTH_SHORT).show();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(view == cancel){
			intent = new Intent(MultiplayerChangeAccount.this, ProfileManager.class);
		}
		if ( intent != null ) {
			startActivity(intent);
			this.finish();
		}
		
	}

}
