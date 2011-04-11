package com.klob.Bomberklob.objects;

import java.util.Hashtable;


public class Undestructible extends AnimatedObjects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* Constructeurs ------------------------------------------------------- */

	public Undestructible(String imageName, boolean hit, int level, boolean fireWall, Hashtable<String, AnimationSequence> animations, String currentAnimation) {
		super(imageName, hit, level, fireWall, animations, currentAnimation);
	}
	
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

}
