package com.klob.Bomberklob.objects;

import java.util.Hashtable;

/**
 * 
 * Class describing undestructible objects
 *
 */
public class Undestructible extends Objects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* Constructeurs ------------------------------------------------------- */

	/**
	 * Create an undestructible object with the following parameters
	 * 
	 * @param imageName The name of the image representing the object
	 * @param animations The HashMap animation the object
	 * @param currentAnimation The current animation the object
	 * @param hit If the object is traversable
	 * @param level The level which is the object
	 * @param fireWall If the object blocks fire
	 * @param damages The number of damage that makes the object
	 */
	
	public Undestructible(String imageName, Hashtable<String, AnimationSequence> animations, ObjectsAnimations currentAnimation,boolean hit, int level, boolean fireWall, int damages) {
		super(imageName, animations, currentAnimation.getLabel(), hit, level, fireWall, damages);
	}
	
	/**
	 * Create a copy of the passed as undestructible parameter
	 * @param undestructible Undestructible object
	 */
	public Undestructible(Undestructible undestructible) {
		super(undestructible);
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public boolean isDestructible() {
		return false;
	}

	@Override
	public Undestructible copy() {
		return new Undestructible(this);
	}

	@Override
	public void destroy() {}
}
