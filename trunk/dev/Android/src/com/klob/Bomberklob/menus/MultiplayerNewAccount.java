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

	
	
	public String inscription() {
		URL url;
		String password = md5(userAccountPassword1.getText().toString());
		String userName = userAccountName.getText().toString();
		String response = "";
		try {
//			"http://klob.s20.eatj.com/BomberklobServer/inscription"
			url = new URL("http://10.0.2.2:8181/BomberklobServer/inscription");
			System.out.println(url.toString());
			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.connect();
				
				/*
				 * on commence deja par tester l'envoi d'idents TODO une fois la
				 * bd faites sur le serveur faire un test de disponibilité
				 * 
				 */
				String[] identifier = { userName, password };

				// message à envoyer à la servlet
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				
//				JSONSerializer jsonSerializer = new JSONSerializer();
//				jsonSerializer.serialize(identifier, writer);
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
				Log.i("@@@@@", "==> " + sbf);	
				response = sbf.toString();
				
				/**
				 * récupération de l'identification de Session
				 */
				String cookies = connection.getHeaderField("Set-Cookie");
				String userKey = null;
				if (cookies!=null){
					StringTokenizer st = new StringTokenizer(cookies, ";");
					if (st.hasMoreTokens()){
						String token = st.nextToken();					
						userKey = token.substring(token.indexOf("=") + 1, token.length()).trim();						
					}
				}
				Log.i("COOKIE", "** "+userKey);

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
		

		if( view == this.validate ) {
			if (!testerString(userAccountName.getText().toString())
					|| !testerString(userAccountPassword1.getText().toString())
					|| !testerString(userAccountPassword2.getText().toString())) {
				Toast.makeText(MultiplayerNewAccount.this,
						"ErrorAutoConnection", Toast.LENGTH_SHORT).show();
			}
			/** TODO factorisation a faire **/
			else if (!userAccountPassword1.getText().toString()
					.equals(userAccountPassword2.getText().toString())) {
				Toast.makeText(MultiplayerNewAccount.this, "ErrorPassword",
						Toast.LENGTH_SHORT).show();
			} else {
				/** test disponibilité sur serveur **/
				/** TODO insertion compte sur serveur **/

				String retour = this.inscription();
				if( retour.equals("OK")){
					Toast.makeText(MultiplayerNewAccount.this, "Inscrit", Toast.LENGTH_SHORT).show();
					//intent = new Intent(MultiplayerNewAccount.this,GetGamesList.class);
					//startActivity(intent);
					
					// int userId = Model.getSystem().getDatabase().getLastUserId();
					// String pwd = md5(userAccountPassword1.getText().toString());
					//
					// Model.getSystem().getDatabase().addAccountMulti(userId,
					// userAccountName.getText().toString(), pwd);
					// Model.getUser().setUserName(userAccountName.getText().toString());
					// Model.getUser().setPassword(pwd);
					
					/** save password **/
					if (password.isChecked()) {
						// Model.getUser().setRememberPassword(true);
						// Model.getSystem().getDatabase().updateSavePwdUser(userId,
						// 1);
					}
					/** auto connect **/
					if (connectionAuto.isChecked()) {
						// Model.getUser().setConnectionAuto(true);
						// Model.getUser().setRememberPassword(true);
						// Model.getSystem().getDatabase().updateAutoConnectUser(userId,
						// 1);
					}
	
					Toast.makeText(MultiplayerNewAccount.this,
							"Inscription réalisée avec succès", Toast.LENGTH_SHORT)
							.show();
					 intent = new Intent(MultiplayerNewAccount.this,MultiplayerHome.class);
					startActivity(intent);
				}
				else if(retour.equals("BU") ){
					Toast.makeText(MultiplayerNewAccount.this,"Ce pseudo est déjà utilisé", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(MultiplayerNewAccount.this,"Une erreur s'est produite", Toast.LENGTH_SHORT).show();
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
