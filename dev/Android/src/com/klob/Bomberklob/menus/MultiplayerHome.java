package com.klob.Bomberklob.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;

public class MultiplayerHome extends Activity implements View.OnClickListener{
	
	private Button previous;
	private Button createGame;
	
	private ImageButton refresh;
	
//	ListView listeGamesView;
//	List<Game> games = new ArrayList<Game>() ;
	
	/** 
	 * menu d'accueil multijoueurs où sont listés les parties en cour
	 */
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.multiplayerhome);

	        previous = (Button)findViewById(R.id.buttonCancel);
			previous.setOnClickListener(this);
			
			createGame = (Button)findViewById(R.id.buttonCreateGame);
			createGame.setOnClickListener(this);
			
			refresh = (ImageButton) findViewById(R.id.buttonRefresh);
			refresh.setOnClickListener(this);
			
//			try {
//				this.recoveryGameList();
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			
//			listeGamesView = (ListView)findViewById(R.id.listGamesView);
//			
//			GameAdapter adapter = new GameAdapter(this, games);
//			listeGamesView.setAdapter(adapter);
//			
//			listeGamesView.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					AlertDialog.Builder adb = new AlertDialog.Builder(ContactServlet.this);
//	        		//on attribut un titre à notre boite de dialogue
//	        		adb.setTitle("Fiouut ?!");
//	        		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
//	        		adb.setMessage("Votre choix : "+ games.get(arg2).getName());
//	        		//on indique que l'on veut le bouton ok à notre boite de dialogue
//	        		adb.setPositiveButton("Ok", null);
//	        		//on affiche la boite de dialogue
//	        		adb.show();	
//					
//				}
//	         });
//	        
//	        adapter.notifyDataSetChanged();      
	 }
	
//	public  void recoveryGameList() throws JSONException{
//		 URL url;
//			try {
//				
//				url = new URL("http://10.0.2.2:8181/BomberklobServer/gameslist");
//				System.out.println(url.toString());
//		    	HttpURLConnection connection;
//				try {
//					connection = (HttpURLConnection) url.openConnection();
//					
//					connection.setDoOutput(true);
//					connection.connect();
//					
//					// message à envoyer à la servlet
//					OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
//					writer.write("Recovery games list");							
//					writer.flush();
//		               
//		               
//					if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//						Log.e("connectionError", "Message retour connexion invalide");
//						   throw new IOException("Response not OK Version" + connection.getResponseCode());
//					}
//					
//					// message reçu provenant de la servlet
//					InputStreamReader in= new InputStreamReader(connection.getInputStream());
//					BufferedReader reader= new BufferedReader(in);
//					StringBuffer sbf= new StringBuffer();
//					String line= null;
//					while((line=reader.readLine())!=null){
//			    	   sbf.append(line);
//					}
//					Log.i("@@@@@", "==> "+ sbf);
//					
//					JSONDeserializer<ArrayList<Game> > jsonDeserializer = new JSONDeserializer<ArrayList<Game>>();
//					games = jsonDeserializer.deserialize(sbf.toString());
//					
//					Log.i("JSON GamesList", games.size() + " parties en cour ===> " + games.get(0).getName());
//
//					
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//		    	  
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			}
//	    }
	 
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
				/*
				 * Nous sommes maintenant sûr que la vue ayant été cliquée est le bouton
				 * de notre interface. Il suffit donc de créer un nouvel Intent pour démarrer
				 * la seconde activité.
				 */
//				Toast.makeText(AccueilMulti.this, "Creation partie multi", Toast.LENGTH_SHORT).show();

				//Intent intent = new Intent(HomeMulti.this, CreateGameMulti.class);
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


