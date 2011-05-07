package game;

import java.awt.Canvas;

import objects.Player;

public class Game {

	// protected Map map;
	// protected Player[] players;
	protected String name;
	protected String type;
	protected String map;
	protected int playerNumberConnected;

	public Game(String name, String type, String map, int playerNumberConnected) {
		this.name = name;
		this.type = type;
		this.map = map;
		this.playerNumberConnected = playerNumberConnected;
	}
	
	public Game(){
	}

	// public Player[] getPlayers() {
	// return players;
	// }
	// public void setPlayers(Player[] players) {
	// this.players = players;
	// }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public int getPlayerNumberConnected() {
		return playerNumberConnected;
	}

	public void setPlayerNumberConnected(int playerNumberConnected) {
		this.playerNumberConnected = playerNumberConnected;
	}

}
