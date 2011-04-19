package com.klob.Bomberklob.objects;

import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Inanimate extends Objects {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Point tilePosition;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Inanimate (String imageName, boolean hit, int level, boolean fireWall, int damages, Point tilePosition) {
		super(imageName, hit, level, fireWall, damages);
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
		return false;
	}

	@Override
	public void update() {}

	@Override
	public void onDraw(Canvas canvas, int size) {
		int tileSize = ResourcesManager.getTileSize();
		canvas.drawBitmap(ResourcesManager.getBitmaps().get("inanimate"), new Rect(this.tilePosition.x*tileSize, this.tilePosition.y*tileSize, (this.tilePosition.x*tileSize)+tileSize, (this.tilePosition.y*tileSize)+tileSize), new Rect(this.position.x, this.position.y, (this.position.x)+size, (this.position.y)+size), null);
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public boolean isDestructible() {
		return false;
	}

	@Override
	public Inanimate copy() {
		return new Inanimate(this);
	}
}
