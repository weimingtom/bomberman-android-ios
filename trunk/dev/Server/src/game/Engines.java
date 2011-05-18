package game;

import java.util.ArrayList;

public class Engines {
	
	private ArrayList<Engine> engines;
	
	
	public Engines(){
		engines = new ArrayList<Engine>();
	}
	
	
	public Engines(ArrayList<Engine> engines) {
		super();
		this.engines = engines;
	}
	
	/**
	 * Ajoût d'une game via un nouvel Engine dans le tableau d'Engine
	 * @param name
	 * @param type
	 * @param map
	 * @param playerNumberConnected
	 **/
	public void addGame(String name, String type, String map, int playerNumberConnected){
		this.engines.add(new Engine(new Game(name, type, map, playerNumberConnected)));
	}

	/**
	 * Accesseur récupérant une Game depuis les Engines
	 * @param name
	 * @return Game
	 **/
	public Game getGame(String name){
		Game g = null;
		for(int i=0; i < engines.size(); i++){
			if(engines.get(i).getGame().getName().equals(name)){
				g = engines.get(i).getGame();
			}
		}
		return g;
	}
	
	/**
	 * Listage des games depuis les Engines
	 * @return ArrayList<Game>
	 **/
	public ArrayList<Game> gamesList(){
		ArrayList<Game> liste = new ArrayList<Game>();
		for(int i=0; i < engines.size(); i++){
			liste.add(engines.get(i).getGame());
		}
		return liste;
	}
	
	public ArrayList<Engine> getEngines() {
		return engines;
	}

	public void setEngines(ArrayList<Engine> engines) {
		this.engines = engines;
	}
	
	

}
