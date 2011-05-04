package com.klob.Bomberklob.menus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;

public class MultiplayerAccountEdit extends Activity implements View.OnClickListener {
	
	private Button cancel;
	private Button validate;
	
	private EditText userName;
	private EditText oldPass;
	private EditText newPass;
	private EditText confirmPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        setContentView(R.layout.multiplayeraccountedit);
		
	    this.userName = (EditText) findViewById(R.id.ChangePasswordMultiplayerEditTextUserName);
	    this.userName.setText(Model.getUser().getUserName());
        
		this.cancel = (Button) findViewById(R.id.ChangePasswordMultiplayerButtonCancel);
		this.cancel.setOnClickListener(this);

        this.validate = (Button) findViewById(R.id.ChangePasswordMultiplayerButtonValidate);
        this.validate.setOnClickListener(this);
        
		this.oldPass = (EditText)findViewById(R.id.ChangePasswordMultiplayerEditTextOldPass);
		this.newPass = (EditText)findViewById(R.id.ChangePasswordMultiplayerEditTextNewPass);
		this.confirmPass = (EditText)findViewById(R.id.ChangePasswordMultiplayerEditTextConfirm);
    }
    
    @Override
	protected void onStop() {
		Log.i("ChangePasswordMultiplayer", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("ChangePasswordMultiplayer", "onDestroy ");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("ChangePasswordMultiplayer", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("ChangePasswordMultiplayer", "onPause ");
		super.onPause();
	}
	
	private boolean testerString(String chaine){
		if(chaine.contains("\t") || chaine.contains("\n") || chaine.contains("\r") || chaine.contains(" ")){
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
    
	public void onClick(View v) {
		
		Intent intent = null;
		
		if( v == this.validate ){
			int userId = Model.getSystem().getLastUser();
			String pwd =  md5(oldPass.getText().toString());
			String newPwd =  md5(newPass.getText().toString());
			
			if(!testerString(userName.getText().toString()) ||
					!testerString(oldPass.getText().toString()) ||
					!testerString(newPass.getText().toString()) ||
					!testerString(confirmPass.getText().toString())){
	 			Toast.makeText(MultiplayerAccountEdit.this, R.string.ErrorAutoConnection, Toast.LENGTH_SHORT).show();
			}
			/** TODO les deux tests de chaine vide et chaine contenant espace peuvent être fusionnés **/
			else if(oldPass.getText().toString().compareTo("")!=0 &&
						newPass.getText().toString().compareTo("")!=0 && 
						confirmPass.getText().toString().compareTo("")!=0 && 
						(userName.getText().toString().compareTo("")!=0)){
					
					if(!newPass.getText().toString().equals(confirmPass.getText().toString())){
						Toast.makeText(MultiplayerAccountEdit.this, R.string.ErrorPassword,Toast.LENGTH_SHORT).show();
					} else
						try {
							if(Model.getSystem().getDatabase().isGoodMultiUser(userId, userName.getText().toString(), pwd)){
							
								/* test de correspondance mot de passe et username avec serveur */
								/* TODO test de disponibilité du userName sur le serveur si userId différent sinon mettre à jour */
								/* mettre a jour bdd */
								/* mettre a jour profil current User */
								/* à modifier puisque l'utilisateur ne modifie pas tout à la fois */
								Model.getSystem().getDatabase().updatePassword(userId, pwd, newPwd);
								Model.getSystem().getDatabase().updateUserName(userId, userName.getText().toString());
								Model.getUser().setUserName(userName.getText().toString());
								Model.getUser().setPassword(newPwd);
								
						//FIXME		Toast.makeText(MultiplayerAccountEdit.this, R.string.ProfilManagementSavedOk,Toast.LENGTH_SHORT).show();
								intent = new Intent(MultiplayerAccountEdit.this, ProfileManager.class);
							}
							else{
								Toast.makeText(MultiplayerAccountEdit.this, R.string.ErrorIdentification,Toast.LENGTH_SHORT).show();
							}
						} catch (NotFoundException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					
				}
			/* sinon possibilité de tester new password vide et userName différent de l'ancien
			 * => correspond à un simple changement de username
			 */
			else{
				Toast.makeText(MultiplayerAccountEdit.this, R.string.ErrorAutoConnection, Toast.LENGTH_SHORT).show();
			}
		}    	
		else if( v == this.cancel){
			intent = new Intent(MultiplayerAccountEdit.this, ProfileManager.class);
		}
		
		if (intent != null) {
			startActivity(intent);
			finish();
		}
    }

}
