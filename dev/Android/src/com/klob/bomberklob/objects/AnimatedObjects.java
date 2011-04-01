package com.klob.bomberklob.objects;

import com.klob.bomberklob.engine.Point;

public abstract class AnimatedObjects extends Animated {

	public AnimatedObjects(String imageName, boolean hit, int level, boolean fireWall, Point position) {
		super(imageName, hit, level, fireWall, position);
	}

}
