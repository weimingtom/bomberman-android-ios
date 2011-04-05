package com.klob.Bomberklob.objects;


public abstract class AnimatedObjects extends Animated {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public AnimatedObjects(int tileSize, int size, String imageName, boolean hit, int level, boolean fireWall) {
		super(tileSize, size, imageName, hit, level, fireWall);
	}
	
	public AnimatedObjects(AnimatedObjects animatedObjects) {
		super(animatedObjects);
	}

}
