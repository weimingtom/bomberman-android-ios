package com.klob.Bomberklob.game;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Canvas;
import android.util.Log;

import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.resources.Point;

public abstract class Game {

	protected GameMap map;
	protected Player[] players;
	protected GameType gameType;
	protected String time;

	protected boolean random;	
	
	private boolean bombBoolean = true;
	private Thread bombThread;	
	protected ConcurrentHashMap<Point, Bomb> bombs;
	private Iterator<Bomb> bombsIterator;

	/* Constructeurs  ------------------------------------------------------ */

	public Game(String mapName, int enemies, String gametype, boolean random) {
		this.map = new GameMap();
		this.bombs = new ConcurrentHashMap<Point, Bomb>();
		
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
	
	/* Threads ------------------------------------------------------------- */
	
	public void setBombThreadRunning(boolean bombBoolean2) {
		this.bombBoolean = bombBoolean2;
		if ( this.bombBoolean && (this.bombThread == null || this.bombThread.getState() == Thread.State.TERMINATED)) {
			this.bombThread = new Thread() {
				@Override
				public void run() {
					Log.i("Bombs Thread","Thread started");
					while (bombBoolean) {
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}						
						for(Entry<Point, Bomb> entry : bombs.entrySet()) {
							entry.getValue().updateTime();
							map.colisionMapUpdate(entry.getValue());
						}
					}
					Log.i("Bombs Thread","Thread done");
				};
			};
			this.bombThread.start();
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
		
	public ConcurrentHashMap<Point, Bomb> getBombs() {
		return this.bombs;
	}

	/* Méthodes publiques -------------------------------------------------- */

	public abstract void initGame();

	public abstract void pushBomb(Player player);

	public void onDraw(Canvas canvas, int size) {
		
		this.map.onDraw(canvas, size);
		
		/* FIXME */
		for (this.bombsIterator = bombs.values().iterator() ; this.bombsIterator.hasNext() ;){
			this.bombsIterator.next().onDraw(canvas, size); 
		}

		for (int i = 0 ; i < this.players.length ; i++ ) {
			if ( this.players[i].getPosition() != null ) {
				this.players[i].onDraw(canvas, size);
			}
		}
	}

	public abstract void update();
}
