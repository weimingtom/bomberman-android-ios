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

import flexjson.JSONSerializer;

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
		this.userAccountName.setFilters(new InputFilter[]{filter});
		
		this.userAccountPassword = (EditText) findViewById(R.id.MultiPlayerGameEditTextPassword);
		this.userAccountPassword.setFilters(new InputFilter[]{filter});
		
		this.password = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxPassword);
		if(Model.getUser().getRememberPassword()){
			password.setChecked(true);
			userAccountName.setText(Model.getUser().getUserName());
			userAccountPassword.setText(Model.getUser().getPassword());
		}
		
		this.connectionAuto = (CheckBox) findViewById(R.id.MultiPlayerGameCheckBoxConnection);

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
	 * function md5 encryption for passwords
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
	 * checking function string
	 * @param string
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
	
	
	
	/**
	 * function connection to the server using the identifiers entered
	 * @return true if authentificated and connected, false sinon
	 */
	public boolean connection() {
		URL url;
		boolean response = false;
		String password = md5(userAccountPassword.getText().toString());
		String userName = userAccountName.getText().toString();
		
		try {
				
			// adresse du serveur et de la servlet correspondante
			url = new URL("http://10.0.2.2:8181/BomberklobServer/connection");
			System.out.println(url.toString());
			try {
				connectionServ = (HttpURLConnection) url.openConnection();
				connectionServ.setDoOutput(true);
				connectionServ.connect();
				
				// on commence déjà par tester l'envoie d'idents 
				String[] identifier = { userName, password };

				// message à envoyer à la servlet
				OutputStreamWriter writer = new OutputStreamWriter(
						connectionServ.getOutputStream());
				
				JSONSerializer jsonSerializer = new JSONSerializer();
				jsonSerializer.serialize(identifier, writer);
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

		if( view == connection){
			
				int userId = Model.getSystem().getDatabase().getLastUserId();
				String pwd = md5(userAccountPassword.getText().toString());
							
				if( !testString(userAccountName.getText().toString()) || !testString(userAccountPassword.getText().toString()) ){
		 			Toast.makeText(Multiplayer.this, R.string.ErrorAutoConnection, Toast.LENGTH_SHORT).show();
		 		}
				else{
					if(connection()){
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
					
						Toast.makeText(Multiplayer.this, "Authentifié", Toast.LENGTH_SHORT).show();
						intent = new Intent(Multiplayer.this,MultiplayerHome.class);
						startActivity(intent);
					}
					else{
						Toast.makeText(Multiplayer.this, R.string.ErrorIdentification, Toast.LENGTH_SHORT).show();
					}
				}
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
