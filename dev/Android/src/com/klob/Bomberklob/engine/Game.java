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
	
	/* Méthodes publiques -------------------------------------------------- */
	
	public abstract void initGame();
	
	public void onDraw(Canvas canvas, int size) {
		this.map.onDraw(canvas, size);
	}

}
