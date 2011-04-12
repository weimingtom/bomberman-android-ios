package com.klob.Bomberklob.engine;

import com.klob.Bomberklob.objects.PlayerAnimations;

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
	
	public void move(PlayerAnimations playerAnimation) {
		if ( playerAnimation == PlayerAnimations.UP) {

		}
		else if ( playerAnimation == PlayerAnimations.DOWN) {

		}
		else if ( playerAnimation == PlayerAnimations.LEFT) {

		}
		else if ( playerAnimation == PlayerAnimations.RIGHT) {

		}
		else if ( playerAnimation == PlayerAnimations.DOWN_LEFT) {

		}
		else if ( playerAnimation == PlayerAnimations.DOWN_RIGHT) {

		}
		else if ( playerAnimation == PlayerAnimations.UP_LEFT) {

		}
		else if ( playerAnimation == PlayerAnimations.UP_RIGHT) {

		}
		else {
			this.single.getPlayers()[0].setCurrentAnimation(playerAnimation.getLabel());
		}
	}
	
	public void onDraw(Canvas canvas, int size) {
		this.single.onDraw(canvas,size);
	}

}
