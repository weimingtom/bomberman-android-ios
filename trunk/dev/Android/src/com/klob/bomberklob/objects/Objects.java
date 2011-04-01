package com.klob.bomberklob.objects;

import java.io.IOException;

import com.klob.bomberklob.engine.Point;
import com.klob.bomberklob.resourcesmanager.ResourcesManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Objects {
	
	protected ResourcesManager rm;
	
	protected Paint paint = new Paint();
	
	protected Context context;
	protected String imageName;
	protected boolean hit;
	protected int level;
	protected boolean fireWall;
	protected Point position;
	
	/* Contructeur --------------------------------------------------------- */
	
	public Objects(String imageName, boolean hit, int level, boolean fireWall, Point position) {
		
		this.rm = ResourcesManager.getInstance();
		
		this.context = this.rm.getContext();
		this.imageName = imageName;
		this.hit = hit;
		this.level = level;
		this.fireWall = fireWall;
		this.position = position;
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
	
	public abstract void resize();

	public abstract void update();
	
	public abstract void destroy();
	
	public abstract boolean hasAnimationFinished();
	
	public abstract void onDraw(Canvas canvas);

}
