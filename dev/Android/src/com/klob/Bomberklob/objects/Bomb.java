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

	/**
	 * Create a bomb with the following parameters
	 * 
	 * @param imageName The name of the image representing the object
	 * @param animations The HashMap animation the object
	 * @param currentAnimation The current animation the object
	 * @param hit If the object is traversable
	 * @param level The level which is the object
	 * @param fireWall If the object blocks fire
	 * @param damages The number of damage that makes the object
	 * @param life Object life
	 * @param player The player who planted the bomb
	 */
	
	public Bomb(String imageName, Hashtable<String, AnimationSequence> animations, ObjectsAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, Player player) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages, life);
		this.player = player;
		if ( this.player != null ) {
			this.power = this.player.powerExplosion;
			this.time = this.player.timeExplosion;
		}
	}

	/**
	 * Create a copy of the bomb passed in parameter
	 * @param bombs Bomb object
	 */
	public Bomb(Bomb bombs) {
		super(bombs);
		this.power = bombs.power;
		this.time = bombs.time;
		this.player = bombs.player;
	}

	/**
	 * Return the power of the bomb
	 * @return The power of the bomb
	 */
	public int getPower() {
		return power;
	}

	/**
	 * Return the remaining time before the explosion of the bomb
	 * @return The remaining time before the explosion of the bomb
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Return the player possessing the bomb
	 * @return The player possessing the bomb
	 */
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

	/**
	 * Return true if the bomb is destructible
	 * 
	 * @return True if the bomb is destructible
	 */	
	@Override
	public boolean isDestructible() {
		return true;
	}
	
	/**
	 * Creates a copy of the bomb
	 * 	 * 
	 * @return A copy of the bomb
	 */
	@Override
	public Bomb copy() {
		return new Bomb(this);
	}

	
	/**
	 * Destroy the bomb
	 */
	@Override
	public void destroy() {
		time = 0;               
	}

	/**
	 * Return true if the time of explosion of the bomb is over
	 * 
	 * @return True if the time of explosion of the bomb is over
	 */
	public boolean timeElapsed() {
		return (time == 0);
	}

	/**
	 * Updates the time bomb
	 */
	public void updateTime() {
		if ( time > 0 ) {
			time--;
		}
	}
}
