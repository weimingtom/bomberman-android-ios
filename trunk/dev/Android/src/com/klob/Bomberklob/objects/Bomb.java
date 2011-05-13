package com.klob.Bomberklob.objects;

import java.util.Hashtable;

import android.graphics.Canvas;

import com.klob.Bomberklob.resources.ResourcesManager;

public class Bomb extends Destructible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int power;
	protected int time;
	protected Player player;
	//FIXME protected ??? type

	public Bomb(String imageName, Hashtable<String, AnimationSequence> animations, ObjectsAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, Player player) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages, life);
		this.player = player;
		if ( this.player != null ) {
			this.power = this.player.powerExplosion;
			this.time = this.player.timeExplosion;
		}
	}

	public Bomb(Bomb bombs) {
		super(bombs);
		this.power = bombs.power;
		this.time = bombs.time;
		this.player = bombs.player;
	}

	public int getPower() {
		return power;
	}

	public int getTime() {
		return time;
	}

	public Player getPlayer() {
		return player;
	}

	/* Méthodes publiques -------------------------------------------------- */

	@Override
	public void onDraw(Canvas canvas,int size) {

		this.rect.left = this.position.x;
		this.rect.top = this.position.y;
		this.rect.right = (this.position.x)+size;
		this.rect.bottom = (this.position.y)+size;

		canvas.drawBitmap(ResourcesManager.getBitmaps().get("bombs"), this.getRect(), this.rect, null);
	}

	// FIXME Pour les mines
	@Override
	public boolean isDestructible() {
		return true;
	}

	@Override
	public Bomb copy() {
		return new Bomb(this);
	}

	@Override
	public void destroy() {
		time = 0;               
	}

	public boolean timeElapsed() {
		return (time == 0);
	}

	public void updateTime() {
		if ( time > 0 ) {
			time--;
		}
	}
}
