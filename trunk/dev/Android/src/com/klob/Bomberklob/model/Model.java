package com.klob.Bomberklob.model;

import java.io.IOException;
import java.util.Locale;

import android.content.Context;

public class Model {

	private static Model model;

	private static System system;
	private static User user;

	/**
	 * Constructor of Model
	 * @param context
	 * @throws IOException
	 */
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
		
		/**
		 * Constructor of System
		 * @param context
		 * @throws IOException
		 */
		private System(Context context) throws IOException {
			this.context = context;
			this.database = new Database(context);
			this.lastUser = this.database.getLastUser();
			this.language = this.database.getLanguage();
			this.volume = this.database.getVolume();
		}	
		
		/* Getteurs -------------------------------------------------------- */
		/**
		 * Getter of attribute database
		 * @return Database database
		 */
		public Database getDatabase() {
			return this.database;
		}
		
		/**
		 * Getter of attribute lastUser
		 * @return int last UserId
		 */
		public int getLastUser() {
			return this.lastUser;
		}
		
		/**
		 * Getter of attribute Volume
		 * @return int volume
		 */
		public int getVolume() {
			return this.volume;
		}

		/**
		 * Getter of attribute language
		 * @return String language
		 */
		public String getLanguage() {		
			return this.language;
		}
		
		/**
		 * Getter of attribute contect
		 * @return Context context
		 */
		public Context getContext() {
			return this.context;
		}
		
		/* Setteurs -------------------------------------------------------- */

		/**
		 * Update of attribute lastUser
		 */
		public void setLastUser() {
			this.lastUser = this.database.getLastUser();
		}

		/**
		 * Setter of attribute volume
		 * @param int volume
		 */
		public void setVolume(int volume) {
			this.volume = volume;
		}

		/**
		 * Setter of attribute language
		 * @param String language
		 */
		public void setLanguage(String language) {
			// FIXME Exception sur le langage passé en paramètre ?
			this.language = language;
		}
		
		/* Méthodes publiques ---------------------------------------------- */
		
		/**
		 * Return the local language
		 * @return Locale language
		 */
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
	
	/**
	 * Setter of attribute context
	 * @param Context context
	 */
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
	
	/**
	 * Getter of the system
	 * @return System system
	 */
	public static System getSystem() {
		return system;
	}
	
	/**
	 * Return the current user
	 * @return User user
	 */
	public static User getUser() {
		return user;
	}
	
	/* Setteurs -------------------------------------------------------- */

	/**
	 * Setter of attribute pseudonym account
	 * @param String pseudonymAccount
	 */
	public static void setUser(String pseudonymAccount) {
		user = system.getDatabase().getUser(pseudonymAccount);
	}

}
