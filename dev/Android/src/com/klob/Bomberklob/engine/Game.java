package com.klob.Bomberklob.engine;

import android.graphics.Canvas;

import com.klob.Bomberklob.objects.Player;

public abstract class Game {
	
	protected Map map;
	protected Player[] players;
	protected GameType gameType;
	
	protected boolean random;
	
	/* Constructeurs  ------------------------------------------------------ */
	
	public Game(String mapName, int enemies, String gametype, boolean random) {
		if ( this.map.loadMap(mapName) ) {
			this.players = new Player[enemies+1];
			this.random = random;
			
			if ( gametype.equals("FreeForAll") ) {
				this.gameType = new FreeForAll();
			}
			else {
				
			}			
		}
	}
	
	/* Getters ------------------------------------------------------------- */

	public Map getMap() {
		return map;
	}

	public Player[] getPlayers() {
		return players;
	}

	public GameType getGameType() {
		return gameType;
	}

	public boolean isRandom() {
		return random;
	}
	
	/* Setters ------------------------------------------------------------- */
	
	public void setMap(Map map) {
		this.map = map;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	public abstract void initGame();

	public void onDraw(Canvas canvas, int size) {
		this.map.onDraw(canvas, size);
	}

}
