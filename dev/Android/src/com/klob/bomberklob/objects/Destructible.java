package com.klob.bomberklob.objects;

import com.klob.bomberklob.engine.Point;

public class Destructible extends AnimatedObjects {
	
	private int life;
	
	public Destructible(String imageName, boolean hit, int level, boolean fireWall, Point position, int life) {
		super(imageName, hit, level, fireWall, position);
		this.life = life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public int getLifeNumber() {
		return this.life;
	}

}
