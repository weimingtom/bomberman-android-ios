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
	protected boolean fireWall;
	protected Point position;
	protected int tileSize;
	protected int size;
	
	/* Contructeur --------------------------------------------------------- */
	
	public Objects(int tileSize, int size, String imageName, boolean hit, int level, boolean fireWall) {
		this.tileSize = tileSize;
		this.imageName = imageName;
		this.hit = hit;
		this.level = level;
		this.fireWall = fireWall;
		this.size = size;
	}
	
	public Objects(Objects objects) {
		this.tileSize = objects.tileSize;
		this.imageName = objects.imageName;
		this.hit = objects.hit;
		this.level = objects.level;
		this.fireWall = objects.fireWall;
		this.size = objects.size;
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
	
	public int getSize() {
		return size;
	}
	
	public int getTileSize() {
		return this.tileSize;
	}
	
	/* Setteurs ------------------------------------------------------------ */


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	/* Méthodes abstraites publiques --------------------------------------- */

	public abstract void update();
	
	public abstract void destroy();
	
	public abstract boolean hasAnimationFinished();
	
	public abstract void onDraw(Canvas canvas);
	
	public abstract boolean isDestructible();

}
