package com.klob.Bomberklob.objects;

import java.util.Hashtable;

public class Destructible extends Objects {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int life;
	
	/* Constructeurs ------------------------------------------------------- */
	
	public Destructible(String imageName, Hashtable<String, AnimationSequence> animations, ObjectsAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life) {
		super(imageName, animations, currentAnimation.getLabel(), hit, level, fireWall, damages);
		this.life = life;
	}
	
	/**
	 * Creates a copy of the destructible object
	 * @param destructible The destructible object to copy
	 */
	
	public Destructible(Destructible destructible) {
		super(destructible);
		this.life = destructible.life;
	}
	
	/* Setters ------------------------------------------------------------- */
	
	/**
	 * Updates the life of the object
	 * @return The life of the object
	 */
	public void setLife(int life) {
		this.life = life;
	}
	
	/* Getters ------------------------------------------------------------- */
	
	/**
	 * Returns the object life
	 * @return The object life
	 */
	
	public int getLifeNumber() {
		return this.life;
	}

	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public boolean isDestructible() {
		return true;
	}

	@Override
	public Destructible copy() {
		return new Destructible(this);
	}

	@Override
	public void destroy() {
		setCurrentAnimation(ObjectsAnimations.DESTROY);
	}
}
