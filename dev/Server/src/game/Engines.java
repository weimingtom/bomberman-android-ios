package game;

import java.util.ArrayList;

import objects.Player;

public class Engines {
	
	private ArrayList<Engine> engines;
	
	/**
	 * Creator of the Engines
	 */
	public Engines(){
		engines = new ArrayList<Engine>();
	}
	
	/**
	 * Creator of the Engines with parameters
	 * @param engines list of Engine
	 */
	public Engines(ArrayList<Engine> engines) {
		super();
		this.engines = engines;
	}
	
	/**
	 * Add a new game  by creating a new Engine in the ArrayList
	 * @param mapGame
	 * @param players
	 * @param name
	 * @param type
	 * @param map
	 * @param playerNumberConnected
	 */
	public void addGame(Map mapGame,Player[] players,String name, String type, String map, int playerNumberConnected){
		this.engines.add(new Engine(new Game(mapGame, players, name, type, map, playerNumberConnected)));
		
	}

	/**
	 * Accessor giving a game by her name
	 * @param name
	 * @return Game
	 */
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
	 * Listing of the current games int an arraylist
	 * @return ArrayList<Game>
	 **/
	public ArrayList<Game> gamesList(){
		ArrayList<Game> liste = new ArrayList<Game>();
		for(int i=0; i < engines.size(); i++){
			liste.add(engines.get(i).getGame());
		}
		return liste;
	}
	
	/**
	 * Getter of the Engines
	 * @return ArrayList<Engine> engines
	 */
	public ArrayList<Engine> getEngines() {
		return engines;
	}

	/**
	 * Setter of list of the Engines
	 * @param ArrayList<Engine> engines
	 */
	public void setEngines(ArrayList<Engine> engines) {
		this.engines = engines;
	}
	
	

}
