package com.klob.Bomberklob.objects;

import java.util.Hashtable;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.klob.Bomberklob.resources.ResourcesManager;

public class Bomb extends Animated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected int power;
	protected int time;
	//FIXME protected ??? type

	public Bomb(String imageName, boolean hit, int level, boolean fireWall, int damages, int power, int time2, Hashtable<String, AnimationSequence> animations, String currentAnimation) {
		super(imageName, hit, level, fireWall, damages, animations, currentAnimation);
		this.power = power;
		this.time = time2;
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
	// FIXME Pour les mines
	@Override
	public boolean isDestructible() {
		return true;
	}
	
	@Override
	public void onDraw(Canvas canvas,int size) {
		int tileSize = ResourcesManager.getTileSize();
        canvas.drawBitmap(ResourcesManager.getBitmaps().get("bombs"), new Rect(this.getPoint().x*tileSize, this.getPoint().y*tileSize, (this.getPoint().x*tileSize)+tileSize, (this.getPoint().y*tileSize)+tileSize), new Rect(this.position.x, this.position.y, (this.position.x)+size, (this.position.y)+size), null);
	}

	@Override
	public Bomb copy() {
		return new Bomb(this);
	}
	
	@Override
	public void destroy() {
		time = 0;		
	}

	@Override
	public boolean hasAnimationFinished() {
		return (time == 0);
	}
	
	public void updateTime() {
		if ( time > 0 ) {
			time--;
		}
	}
}
