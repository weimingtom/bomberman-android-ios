package com.klob.bomberklob.objects;

import com.klob.bomberklob.engine.Point;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Inanimate extends Objects {
	
	protected Rect rect;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Inanimate (String imageName, boolean hit, int level, boolean fireWall, Point position) {
		super(imageName, hit, level, fireWall, position);
	}

	@Override
	public void destroy() {}

	@Override
	public boolean hasAnimationFinished() {
		return true;
	}

	@Override
	public void resize() {
		int tileSize = this.rm.getTileSize();
		this.rect = new Rect(this.position.x*tileSize, this.position.y*tileSize, (this.position.x*tileSize)+tileSize, (this.position.y*tileSize)+tileSize);
	}

	@Override
	public void update() {}

	@Override
	public void onDraw(Canvas canvas) {
		int tileSize = this.rm.getTileSize();
		canvas.drawBitmap(this.rm.getBitmaps().get("inanimate"), this.rect, new Rect(this.position.x*tileSize, this.position.y*tileSize, (this.position.x*tileSize)+tileSize, (this.position.y*tileSize)+tileSize), this.paint);
	}
}
