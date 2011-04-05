package com.klob.Bomberklob.model;

import java.io.IOException;
import java.util.Locale;

import android.content.Context;

public class Model {

	private static Model model;

	private static System system;
	private static User user;

	private Model (Context context) throws IOException {
		system = new System(context);
		user = system.getDatabase().getUser(system.getLastUser());

	}
	
	/* Classes privées ----------------------------------------------------- */
	
	public class System {

		private int volume;
		private String language;
		private int lastUser;
		private Database database;
		private Context context;
		
		private System(Context context) throws IOException {
			this.context = context;
			this.database = new Database(context);
			this.lastUser = this.database.getLastUser();
			this.language = this.database.getLanguage();
			this.volume = this.database.getVolume();
		}	
		
		/* Getteurs -------------------------------------------------------- */
		
		public Database getDatabase() {
			return this.database;
		}
		
		public int getLastUser() {
			return this.lastUser;
		}
		
		public int getVolume() {
			return this.volume;
		}

		public String getLanguage() {		
			return this.language;
		}
		
		public Context getContext() {
			return this.context;
		}
		
		/* Setteurs -------------------------------------------------------- */

		public void setLastUser() {
			this.lastUser = this.database.getLastUser();
		}

		public void setVolume(int volume) {
			// FIXME Exception sur le volume passé en paramètre ? (entre 0 et 100)
			this.volume = volume;
		}

		public void setLanguage(String language) {
			// FIXME Exception sur le langage passé en paramètre ?
			this.language = language;
		}
		
		/* Méthodes publiques ---------------------------------------------- */
		
		public Locale getLocalLanguage() {		
			if (this.language.equals("Français")) {
				return Locale.FRENCH;
			}
			else {
				return Locale.ENGLISH;
			}
		}
		
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public static Model setInstance(Context context){
		if (null == model) {
			try {
				model = new Model(context);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return model;
	}
	
	public static System getSystem() {
		return system;
	}
	
	public static User getUser() {
		return user;
	}
	
	/* Setteurs -------------------------------------------------------- */

	public static void setUser(String pseudonymAccount) {
		user = system.getDatabase().getUser(pseudonymAccount);
	}

}
