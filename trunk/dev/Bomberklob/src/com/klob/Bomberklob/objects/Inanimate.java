package com.klob.Bomberklob.objects;

import com.klob.Bomberklob.engine.Point;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Inanimate extends Objects {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Point tilePosition;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Inanimate (int tileSize, int size, String imageName, boolean hit, int level, boolean fireWall, Point tilePosition) {
		super(tileSize, size, imageName, hit, level, fireWall);
		this.tilePosition = tilePosition;
	}
	
	public Inanimate (Inanimate inanimate) {
		super(inanimate);
		this.tilePosition = inanimate.tilePosition;
	}
	
	/* Getters ------------------------------------------------------------- */
	
	public Point getTilePosition() {
		return this.tilePosition;
	}

	@Override
	public void destroy() {}

	@Override
	public boolean hasAnimationFinished() {
		return true;
	}

	@Override
	public void update() {}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(ResourcesManager.getBitmaps().get("inanimate"), new Rect(this.tilePosition.x*this.tileSize, this.tilePosition.y*this.tileSize, (this.tilePosition.x*this.tileSize)+this.tileSize, (this.tilePosition.y*this.tileSize)+this.tileSize), new Rect(this.position.x*this.size, this.position.y*this.size, (this.position.x*this.size)+this.size, (this.position.y*this.size)+this.size), null);
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public boolean isDestructible() {
		return false;
	}
}
