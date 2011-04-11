package com.klob.Bomberklob.engine;

import android.graphics.Canvas;

public class Engine {
	
	private Single single;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Engine(String mapName, int enemies, String gametype, boolean random, int difficulty) {
		this.single = new Single(mapName, enemies, gametype, random, difficulty);
	}
	
	/* Getters ------------------------------------------------------------- */

	public Single getSingle() {
		return single;
	}
	
	/* Setters ------------------------------------------------------------- */

	public void setSingle(Single single) {
		this.single = single;
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	public void onDraw(Canvas canvas, int size) {
		this.single.onDraw(canvas,size);
	}

}
