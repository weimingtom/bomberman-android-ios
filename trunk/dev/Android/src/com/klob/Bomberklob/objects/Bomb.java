package com.klob.Bomberklob.objects;

import java.util.Hashtable;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.klob.Bomberklob.resources.ResourcesManager;

public class Bomb extends Destructible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int power;
	protected int time;
	//FIXME protected ??? type

	public Bomb(String imageName, Hashtable<String, AnimationSequence> animations, ObjectsAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, int power, int time) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages, life);
		this.power = power;
		this.time = time;
	}

	public Bomb(Bomb bombs) {
		super(bombs);
		this.power = bombs.power;
		this.time = bombs.time;
	}

	public int getPower() {
		return power;
	}

	public int getTime() {
		return time;
	}

	/* Méthodes publiques -------------------------------------------------- */

	@Override
	public void onDraw(Canvas canvas,int size) {
		if(cf!=null) {
			//color filter code here
		}
		canvas.drawBitmap(ResourcesManager.getBitmaps().get("bombs"), this.getRect(), new Rect(this.position.x, this.position.y, (this.position.x)+size, (this.position.y)+size), null);
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
