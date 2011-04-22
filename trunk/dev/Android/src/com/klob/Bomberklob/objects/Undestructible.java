package com.klob.Bomberklob.objects;

import java.util.Hashtable;


public class Undestructible extends Objects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* Constructeurs ------------------------------------------------------- */

	public Undestructible(String imageName, Hashtable<String, AnimationSequence> animations, String currentAnimation,boolean hit, int level, boolean fireWall, int damages) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages);
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
