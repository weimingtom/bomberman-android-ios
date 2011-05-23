package com.klob.Bomberklob.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.StringTokenizer;

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

public class Multiplayer extends Activity implements View.OnClickListener {
	
	private Button cancel;
	private Button connection;
	private Button newAccount;
	private TextView userPseudo;
	private EditText userAccountName;
	private EditText userAccountPassword;
	private CheckBox password;
	private CheckBox connectionAuto;
	
	private HttpURLConnection connectionServ;
	
	/*
	 * menu de connexion multijoueurs
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
        setContentView(R.layout.multiplayer);
        
		this.cancel = (Button)findViewById(R.id.MultiPlayerGameButtonBack);
		this.cancel.setOnClickListener(this);
		
		this.connection = (Button)findViewById(R.id.MultiPlayerGameButtonConnection);
		this.connection.setOnClickListener(this);
		
		this.newAccount = (Button)findViewById(R.id.MultiPlayerGameNewAccount);
		this.newAccount.setOnClickListener(this);
		
		this.userPseudo = (TextView) findViewById(R.id.MultiPlayerGameUserName);
		this.userPseudo.setText(Model.getUser().getPseudo());
		
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
		
		this.password = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxPassword);
		if(Model.getUser().getRememberPassword()){
			password.setChecked(true);
			userAccountName.setText(Model.getUser().getUserName());
			userAccountPassword.setText(Model.getUser().getPassword());
		}
		
//		if ( Model.getUser().getRememberPassword() ) {
//			this.password.setChecked(true);
//		}
		
		
		
		
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
					Intent intent = new Intent(Multiplayer.this, MultiplayerHome.class);
					startActivity(intent);				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * fonction de cryptage md5 pour les mots de passe
	 * @param password
	 * @return passwordEncrypted
	 */
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

	/**
	 * simple fonction de vérification de chaîne de caractères
	 * @param chaine
	 * @return true/false
	 */
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
	
	
	
	
	public boolean connection() {
		URL url;
		boolean response = false;
		String password = md5(userAccountPassword.getText().toString());
		String userName = userAccountName.getText().toString();
		
		try {

			url = new URL("http://10.0.2.2:8181/BomberklobServer/connection");
			System.out.println(url.toString());
			try {
				connectionServ = (HttpURLConnection) url.openConnection();
				connectionServ.setDoOutput(true);
				connectionServ.connect();
				
				/**
				 * on commence deja par tester l'envoi d'idents TODO une fois la
				 * bd faites sur le serveur faire un test de disponibilité
				 * 
				 */
				String[] identifier = { userName, password };

				// message à envoyer à la servlet
				OutputStreamWriter writer = new OutputStreamWriter(
						connectionServ.getOutputStream());
				
//				JSONSerializer jsonSerializer = new JSONSerializer();
//				jsonSerializer.serialize(identifier, writer);
				writer.flush();

				if (connectionServ.getResponseCode() != HttpURLConnection.HTTP_OK) {
					Log.e("connectionError",
							"Message retour connexion invalide");
					throw new IOException("Response not OK Version"
							+ connectionServ.getResponseCode());
				}

				// message reçu provenant de la servlet
				InputStreamReader in = new InputStreamReader(
						connectionServ.getInputStream());
				BufferedReader reader = new BufferedReader(in);
				StringBuffer sbf = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sbf.append(line);
				}
				Log.i("@@@@@", "==> " + sbf);
				
				/**
				 * récupération de l'identification de Session
				 */
				String cookies = connectionServ.getHeaderField("Set-Cookie");
				String userKey = null;
				if (cookies!=null){
					StringTokenizer st = new StringTokenizer(cookies, ";");
					if (st.hasMoreTokens()){
						String token = st.nextToken();					
						userKey = token.substring(token.indexOf("=") + 1, token.length()).trim();						
					}
				}
				Log.i("COOKIE", "** "+userKey);
				
				
				if(sbf.toString().equals("OK")){
					response = true;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		connectionServ.disconnect();
		return response;
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
//			try {
				int userId = Model.getSystem().getDatabase().getLastUserId();
				String pwd = md5(userAccountPassword.getText().toString());
				
//				Log.i("lastUserId", ">>> " + Model.getSystem().getDatabase().getUserIdByName(Model.getUser().getPseudo()) + " >>> " + Model.getUser().getPseudo() + " >>> " + Model.getUser().getPassword());
//				Log.i("lastUserIdSystemPassword", ">>> " + Model.getSystem().getDatabase().getUser(userId).getPassword() );
//				Log.i("pwd lol ", md5("lol"));
				
				if( !testString(userAccountName.getText().toString()) || !testString(userAccountPassword.getText().toString()) ){

		 			Toast.makeText(Multiplayer.this, R.string.ErrorAutoConnection, Toast.LENGTH_SHORT).show();
		 		}
				else{
					if(connection()){
						Toast.makeText(Multiplayer.this, "Authentifié", Toast.LENGTH_SHORT).show();
						intent = new Intent(Multiplayer.this,MultiplayerHome.class);
						startActivity(intent);
					}
					else{
						Toast.makeText(Multiplayer.this, "ErrorAuth", Toast.LENGTH_SHORT).show();
					}
					
				}
//				else if (Model.getSystem().getDatabase().isGoodMultiUser(userId, userAccountName.getText().toString(), pwd)){
//					/** save password **/
//					if(password.isChecked()){
//							Model.getUser().setRememberPassword(true);
//							Model.getSystem().getDatabase().updateSavePwdUser(userId, 1);
//					}
//					else if (!password.isChecked()) {
//						Model.getUser().setRememberPassword(false);
//						Model.getSystem().getDatabase().updateSavePwdUser(userId, 0);
//					}
//					/** auto connect **/
//					if(connectionAuto.isChecked()){
//							Model.getUser().setConnectionAuto(true);
//							Model.getUser().setRememberPassword(true);
//							Model.getSystem().getDatabase().updateAutoConnectUser(userId, 1);
//					}
//					else if (!connectionAuto.isChecked()) {
//						Model.getUser().setConnectionAuto(false);
//						Model.getSystem().getDatabase().updateAutoConnectUser(userId, 0);
//					}
//					intent = new Intent(Multiplayer.this, MultiplayerHome.class);
//					startActivity(intent);
//				}
//				else{
//
//					Toast.makeText(Multiplayer.this, R.string.ErrorAuth, Toast.LENGTH_SHORT).show();
//				}
//			} catch (SQLException e) {
//
//				Toast.makeText(Multiplayer.this, "ErrorAuth", Toast.LENGTH_SHORT).show();
//				e.printStackTrace();
//			}
		}
		else if(view == this.newAccount){
			intent = new Intent(Multiplayer.this, MultiplayerNewAccount.class);
		}
		else if(view == this.cancel){
			intent = new Intent(Multiplayer.this, Home.class);
		}
		
		if ( intent != null ) {
			startActivity(intent);
			this.finish();
		}		
	}
}
