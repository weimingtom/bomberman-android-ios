package com.klob.bomberklob.objects;

import com.klob.bomberklob.engine.Point;

public class Bombs extends Animated {
	
	protected int power;
	protected int time;
	//FIXME protected ??? type

	public Bombs(String imageName, boolean hit, int level, boolean fireWall,Point position, int power, int time) {
		super(imageName, hit, level, fireWall, position);
		this.power = power;
		this.time = time;
	}

	public int getPower() {
		return power;
	}

	public int getTime() {
		return time;
	}

}
