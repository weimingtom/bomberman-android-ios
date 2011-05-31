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

public class MultiplayerNewAccount  extends Activity implements View.OnClickListener{

	private TextView userPseudo;
	private EditText userAccountName;
	private EditText userAccountPassword1;
	private EditText userAccountPassword2;

	private CheckBox password;
	private CheckBox connectionAuto;

	private Button validate;
	private Button cancel;
	
	private HttpURLConnection connection;

	
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

		this.connectionAuto = (CheckBox) findViewById(R.id.NewAccountOnLineCheckBoxConnection);
		this.connectionAuto.setOnClickListener(this);

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
	 * Connection to the server giving it the username and password, and contacting the
	 * servlet for inscription
	 * @return userKey
	 */
	public String inscription() {
		URL url;
		String password = md5(userAccountPassword1.getText().toString());
		String userName = userAccountName.getText().toString();
		String response = "";
		try {
			// adresse du serveur et de la servlet correspondante
			url = new URL("http://10.0.2.2:8181/BomberklobServer/inscription");
			System.out.println(url.toString());
			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.connect();
				
				//on commence déjà par tester l'envoie d'idents 
				String[] identifier = { userName, password };

				// message à envoyer à la servlet
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				
				JSONSerializer jsonSerializer = new JSONSerializer();
				jsonSerializer.serialize(identifier, writer);
				writer.flush();

				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					Log.e("connectionError",
							"Message retour connexion invalide");
					throw new IOException("Response not OK Version"
							+ connection.getResponseCode());
				}

				// message reçu provenant de la servlet
				InputStreamReader in = new InputStreamReader(
						connection.getInputStream());
				BufferedReader reader = new BufferedReader(in);
				StringBuffer sbf = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sbf.append(line);
				}
				response = sbf.toString();
				
				// récupération de l'identification de Session
				String cookies = connection.getHeaderField("Set-Cookie");
				String userKey = null;
				if (cookies!=null){
					StringTokenizer st = new StringTokenizer(cookies, ";");
					if (st.hasMoreTokens()){
						String token = st.nextToken();					
						userKey = token.substring(token.indexOf("=") + 1, token.length()).trim();						
					}
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		connection.disconnect();
		return response;
	}
	
	
	

	public void onClick(View view) {

		Intent intent = null;
		
		// validation de l'inscription
		if( view == this.validate ) {
			if (!testString(userAccountName.getText().toString())
					|| !testString(userAccountPassword1.getText().toString())
					|| !testString(userAccountPassword2.getText().toString())) {
				Toast.makeText(MultiplayerNewAccount.this,
						R.string.ErrorIdentification, Toast.LENGTH_SHORT).show();
			}
			else if (!userAccountPassword1.getText().toString()
					.equals(userAccountPassword2.getText().toString())) {
				Toast.makeText(MultiplayerNewAccount.this, R.string.ErrorIdentification,
						Toast.LENGTH_SHORT).show();
			} 
			else {
				String retour = this.inscription();
				if( retour.equals("OK")){
					Toast.makeText(MultiplayerNewAccount.this, "Inscrit", Toast.LENGTH_SHORT).show();
									
					 int userId = Model.getSystem().getDatabase().getLastUserId();
					 String pwd = md5(userAccountPassword1.getText().toString());
					
					 Model.getSystem().getDatabase().addAccountMulti(userId,
					 userAccountName.getText().toString(), pwd);
					 Model.getUser().setUserName(userAccountName.getText().toString());
					 Model.getUser().setPassword(pwd);
					 
					 intent = new Intent(MultiplayerNewAccount.this,MultiplayerHome.class);
					 startActivity(intent);
					
					// save password 
					if (password.isChecked()) {
						 Model.getUser().setRememberPassword(true);
						 Model.getSystem().getDatabase().updateSavePwdUser(userId,
						 1);
					}
					// auto connect 
					if (connectionAuto.isChecked()) {
						 Model.getUser().setConnectionAuto(true);
						 Model.getUser().setRememberPassword(true);
						 Model.getSystem().getDatabase().updateAutoConnectUser(userId,
						 1);
					}
	
					Toast.makeText(MultiplayerNewAccount.this,
							"Inscription réalisée avec succès", Toast.LENGTH_SHORT)
							.show();
					 intent = new Intent(MultiplayerNewAccount.this,MultiplayerHome.class);
					startActivity(intent);
				}
				else if(retour.equals("BU") ){
					Toast.makeText(MultiplayerNewAccount.this,  R.string.ErrorPseudo, Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(MultiplayerNewAccount.this, R.string.ErrorAuth, Toast.LENGTH_SHORT).show();
				}
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
