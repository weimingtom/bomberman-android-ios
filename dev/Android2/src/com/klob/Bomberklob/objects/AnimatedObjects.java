package com.klob.Bomberklob.objects;

import java.util.Hashtable;


public abstract class AnimatedObjects extends Animated {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public AnimatedObjects(String imageName, boolean hit, int level, boolean fireWall, int damages, Hashtable<String, AnimationSequence> animations, String currentAnimation) {
		super(imageName, hit, level, fireWall, damages, animations, currentAnimation);
	}
	
	public AnimatedObjects(AnimatedObjects animatedObjects) {
		super(animatedObjects);
	}

}
