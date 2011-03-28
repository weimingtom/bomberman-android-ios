package com.klob.bomberklob.model;

import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

import android.content.Context;

public class Model {

	private static Model model;

	private Vector<Map> map = new Vector<Map>();
	private System system;
	private User user;

	private Model (Context context) throws IOException {
		this.system = new System(context);
		this.user = this.system.getDatabase().getUser(this.system.getDatabase().getLastUser());
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
	
	public System getSystem() {
		return this.system;
	}
	
	public User getUser() {
		return this.user;
	}
	
	
	public Vector<Map> getMap() {
		return map;
	}	
	
	/* Setteurs -------------------------------------------------------- */

	public void setUser(String pseudonymAccount) {
		this.user = this.system.getDatabase().getUser(pseudonymAccount);
	}
	
	/* Méthodes publiques -------------------------------------------------- */

	public static Model getInstance(Context context) throws IOException {
		if (null == model) {
			model = new Model(context);
		}
		return model;
	}
}
