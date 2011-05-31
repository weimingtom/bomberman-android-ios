package com.klob.Bomberklob.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.klob.Bomberklob.R;
import com.klob.Bomberklob.game.Game;
import com.klob.Bomberklob.model.Model;
import flexjson.JSONDeserializer;
import flexjson.JSONException;

public class MultiplayerHome extends Activity implements View.OnClickListener{
	
	private Button previous;
	private Button createGame;
	private ImageButton refresh;
	
	ListView listeGamesView;
	List<Game> games = new ArrayList<Game>() ;
	

	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.multiplayerhome);

	        previous = (Button)findViewById(R.id.buttonCancel);
			previous.setOnClickListener(this);
			
			createGame = (Button)findViewById(R.id.buttonCreateGame);
			createGame.setOnClickListener(this);
			
			refresh = (ImageButton) findViewById(R.id.buttonRefresh);
			refresh.setOnClickListener(this);

			try{
				if(this.recoveryGameList()){
	        		listeGamesView = (ListView)findViewById(R.id.listGamesView);
	        		
	        		// TODO adaptateur à implémenter
	        		//GameAdapter adapter = new GameAdapter(this, games);
	        		//listeGamesView.setAdapter(adapter);
	
	                
	                listeGamesView.setOnItemClickListener(new OnItemClickListener() {
	
	        			@Override
	        			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	        					long arg3) {
	        				AlertDialog.Builder adb = new AlertDialog.Builder(MultiplayerHome.this);
	                		//on attribut un titre à notre boite de dialogue
	                		adb.setTitle("Selection");
	                		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
	                		//adb.setMessage("Votre choix : "+ games.get(arg2).getName());
	                		//on indique que l'on veut le bouton ok à notre boite de dialogue
	                		adb.setPositiveButton("Ok", null);
	                		//on affiche la boite de dialogue
	                		adb.show();	
	        				
	        			}
	                 });
	                
	                //adapter.notifyDataSetChanged();
	        	}
	        	else{
	        		Toast.makeText(MultiplayerHome.this,"Erreur de récupération des parties", Toast.LENGTH_SHORT).show();
	        	}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
	 }
	
	/**
	 * Connection to the server and recovery of games list
	 * @return true if ok false else
	 * @throws JSONException
	 */
	public  boolean recoveryGameList() throws JSONException{
	 URL url;
	 boolean response = false;
		try {
			
			url = new URL("http://10.0.2.2:8181/BomberklobServer/gameslist");
			System.out.println(url.toString());
	    	HttpURLConnection connection;
			try {
				connection = (HttpURLConnection) url.openConnection();
				
				connection.setDoOutput(true);
				connection.connect();
				
				// message à envoyer à la servlet
				/**
				 * TODO à remplacer par le userKey récupéré lors de l'identification ou inscription
				 */
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write("79D38B218F097CEAB7A1E23CB1309A5F");							
				writer.flush();
	               
	               
				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					Log.e("connectionError", "Message retour connexion invalide");
					   throw new IOException("Response not OK Version" + connection.getResponseCode());
				}
				
				// message reçu provenant de la servlet
				InputStreamReader in= new InputStreamReader(connection.getInputStream());
				BufferedReader reader= new BufferedReader(in);
				StringBuffer sbf= new StringBuffer();
				String line= null;
				while((line=reader.readLine())!=null){
		    	   sbf.append(line);
				}
				
				if(!sbf.toString().equals("ERROR")){
					JSONDeserializer<ArrayList<Game> > jsonDeserializer = new JSONDeserializer<ArrayList<Game>>();
					games = jsonDeserializer.deserialize(sbf.toString());
					
					response = true;
				}
					connection.disconnect();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    	  
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return response;
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

		public void onClick(View v) {
			Intent intent = null;
			if(v == refresh){
				Toast.makeText(MultiplayerHome.this, "Rafraichissement des parties", Toast.LENGTH_SHORT).show();

			}
			else if (v == createGame) {
				//intent = new Intent(MultiplayerHome.this, CreateGameMulti.class);
				//startActivity(intent);
			}
			else if(v == previous){
				if(Model.getUser().getConnectionAuto()){
					intent = new Intent(MultiplayerHome.this, Home.class);
				}
				else{
					intent = new Intent(MultiplayerHome.this, Multiplayer.class);
				}
				
			}			
			if ( intent != null ) {
				startActivity(intent);
				this.finish();
			}	
		}
}


