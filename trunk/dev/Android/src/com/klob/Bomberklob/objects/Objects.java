package com.klob.Bomberklob.objects;

import java.io.Serializable;

import com.klob.Bomberklob.engine.Point;

import android.graphics.Canvas;

public abstract class Objects implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String imageName;
	protected boolean hit;
	protected int level;
	protected int damages;
	protected boolean fireWall;
	protected Point position;
	
	/* Contructeur --------------------------------------------------------- */
	
	public Objects(String imageName, boolean hit, int level, boolean fireWall, int damages) {
		this.imageName = imageName;
		this.hit = hit;
		this.level = level;
		this.fireWall = fireWall;
		this.position = null;
		this.damages = damages;
	}
	
	public Objects(Objects objects) {
		this.imageName = objects.imageName;
		this.hit = objects.hit;
		this.level = objects.level;
		this.fireWall = objects.fireWall;
		this.position = objects.position;
		this.damages = objects.damages;
	}
	
	/* Getters ------------------------------------------------------------- */
	
	public boolean isHit() {
		return this.hit;
	}

	public int getLevel() {
		return this.level;
	}

	public boolean isFireWall() {
		return this.fireWall;
	}

	public Point getPosition() {
		return this.position;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	/* Setteurs ------------------------------------------------------------ */


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	/* Méthodes abstraites publiques --------------------------------------- */

	public abstract void update();
	
	public abstract Objects copy();
	
	public abstract void destroy();
	
	public abstract boolean hasAnimationFinished();
	
	public abstract void onDraw(Canvas canvas, int size);
	
	public abstract boolean isDestructible();
	
	public String toString() {
		return imageName;
	}

}
