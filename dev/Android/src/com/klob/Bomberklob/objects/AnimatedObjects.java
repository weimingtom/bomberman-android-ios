package com.klob.Bomberklob.objects;

import java.util.Hashtable;


public abstract class AnimatedObjects extends Animated {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public AnimatedObjects(String imageName, boolean hit, int level, boolean fireWall, Hashtable<String, AnimationSequence> animations, String currentAnimation) {
		super(imageName, hit, level, fireWall, animations, currentAnimation);
	}
	
	public AnimatedObjects(AnimatedObjects animatedObjects) {
		super(animatedObjects);
	}

}
