package servlet;

import game.Engines;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



/**
 * Servlet implementation class ContextListener
 */
public class ContextListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent event) {}

	/**
	 * A l'initialisation du Serveur l'objet engines sera crée 
	 * les parties ajoûtés sont la pour le test, ce sera par la suite
	 * lors de création de parties que ce schémas sera appliqué
	 * engines est un objet partagé par toutes les servlet
	 */
	public void contextInitialized(ServletContextEvent event) {
		//Nous avons accès à l'objet ServletContext via l'objet ServletContextEvent
		Engines engines = new Engines();
		
		
		engines.addGame("Bezinga", "alarache", "torino", 2);
		engines.addGame("Mukata", "hardcore", "fired", 3);
		engines.addGame("Loufla", "free4all", "opened", 4);
		event.getServletContext().setAttribute("engines", engines);
		
		/**
		 * Connexion à la bdd, locale..
		 * Pas de système de pooling pour le moment
		 */
		String dbClassName = "com.mysql.jdbc.Driver";
		String CONNECTION = "jdbc:mysql://127.0.0.1/Bomberklob";
		
		try {
			Class.forName(dbClassName);
			Properties p = new Properties();
			p.put("user", "root");
			p.put("password", "ludo");

			Connection connection;
			try {
				connection = DriverManager.getConnection(CONNECTION, p);
				System.out.println("Connexion BDD Succeeded");
				
				/**
				 * création de la mini bdd
				 */
				Statement theStatement = connection.createStatement();
				boolean insert = theStatement.execute("Create Table IF NOT EXISTS Users(userName VARCHAR(20) PRIMARY KEY UNIQUE NOT NULL, password VARCHAR(50) DEFAULT NULL)");
				System.out.println("Creation table "+ insert);
				
				
				connection.close();
				event.getServletContext().setAttribute("connectionData", connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		/**
		 * HashMap des joueurs en lignes la clé étant leur userKey ou id de session
		 */
		HashMap<String, String> usersOnline = new HashMap<String, String>();
		event.getServletContext().setAttribute("usersOnline", usersOnline);
		
	}
}
