package com.klob.Bomberklob.game;

import android.graphics.Canvas;

import com.klob.Bomberklob.objects.Player;

public abstract class Game {

	protected Map map;
	protected Player[] players;
	protected GameType gameType;

	protected boolean random;

	/* Constructeurs  ------------------------------------------------------ */

	public Game(String mapName, int enemies, String gametype, boolean random) {
		this.map = new Map();
		if ( this.map.loadMap(mapName) ) {
			this.players = new Player[enemies+1];
			this.random = random;
		}

		if ( gametype.equals("FreeForAll") ) {
			this.gameType = new FreeForAll();
		}
		else {
			//FIXME Erreur de chargement de la carte
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

	/* Méthodes publiques -------------------------------------------------- */

	public abstract void initGame();

	public abstract void pushBomb(Player player);

	public void onDraw(Canvas canvas, int size) {
		
		this.map.gameOnDraw(canvas, size);

		for (int i = 0 ; i < this.players.length ; i++ ) {
			if ( this.players[i].getPosition() != null ) {
				this.players[i].onDraw(canvas, size);
			}
		}
	}

	public abstract void update();
}
