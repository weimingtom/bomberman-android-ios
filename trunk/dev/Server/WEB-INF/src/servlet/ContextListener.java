package servlet;

import game.Engines;
import game.Game;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
	}
}