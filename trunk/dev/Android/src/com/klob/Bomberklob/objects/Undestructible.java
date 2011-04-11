package com.klob.Bomberklob.objects;


public class Undestructible extends AnimatedObjects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* Constructeurs ------------------------------------------------------- */

	public Undestructible(String imageName, boolean hit, int level, boolean fireWall) {
		super(imageName, hit, level, fireWall);
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
