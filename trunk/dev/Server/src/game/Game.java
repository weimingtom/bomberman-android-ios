package game;

import objects.Player;


public class Game {

	protected Map mapGame;
	protected Player[] players;
	protected String name;
	protected String type;
	protected String map;
	protected int playerNumberConnected;

	/**
	 * Constructor of a game
	 * @param mapGame
	 * @param players
	 * @param name
	 * @param type
	 * @param map
	 * @param playerNumberConnected
	 */
	public Game(Map mapGame,Player[] players, String name, String type, String map, int playerNumberConnected) {
		this.name = name;
		this.type = type;
		this.mapGame = mapGame;
		this.playerNumberConnected = playerNumberConnected;
		this.players = players; 
	}
	
	public Game(){
	}

	/**
	 * Getter of list of player connected
	 * @return Player[] players
	 */
	 public Player[] getPlayers() {
		 return players;
	 }
	 
	 /**
	  * Getter of the game name
	  * @return String name of game
	  */
	public String getName() {
		return name;
	}

	/**
	  * Setter of the game name
	  * @param String name of game
	  */
	public void setName(String name) {
		this.name = name;
	}
	
	 /**
	  * Getter of the game type
	  * @return String name of type
	  */
	public String getType() {
		return type;
	}

	/**
	  * Setter of the game type
	  * @param String name of type
	  */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter of the name of map
	 * @return String map name
	 */
	public String getMap() {
		return map;
	}

	/**
	 * Setter of the name of map
	 * @param String map name
	 */
	public void setMap(String map) {
		this.map = map;
	}

	/**
	 * Getter of the number of player connected
	 * @return int playerNumberConnected
	 */
	public int getPlayerNumberConnected() {
		return playerNumberConnected;
	}

	/**
	 * Setter of the number of player connected
	 * @param playerNumberConnected playerNumberConnected
	 */
	public void setPlayerNumberConnected(int playerNumberConnected) {
		this.playerNumberConnected = playerNumberConnected;
	}

}
