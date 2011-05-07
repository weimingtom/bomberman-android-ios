package com.klob.Bomberklob.game;

import android.graphics.Canvas;

import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.resources.GameMap;

public abstract class Game {

	protected GameMap map;
	protected Player[] players;
	protected GameType gameType;

	protected boolean random;

	/* Constructeurs  ------------------------------------------------------ */

	public Game(String mapName, int enemies, String gametype, boolean random) {
		this.map = new GameMap();
		if ( this.map.loadMap(mapName) ) {
			this.players = new Player[enemies+1];
			this.random = random;			

			if ( gametype.equals("FreeForAll") ) {
				this.gameType = new FreeForAll();
			}
			else {
				
			}
		}
		else {
			//FIXME Erreur de chargement de la carte
		}
			
	}


	/* Getters ------------------------------------------------------------- */

	public GameMap getMap() {
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
		
		this.map.onDraw(canvas, size);

		for (int i = 0 ; i < this.players.length ; i++ ) {
			if ( this.players[i].getPosition() != null ) {
				this.players[i].onDraw(canvas, size);
			}
		}
	}

	public abstract void update();
}
