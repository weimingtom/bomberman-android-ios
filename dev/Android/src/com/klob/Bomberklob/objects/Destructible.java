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
	
	public Destructible(Destructible destructible) {
		super(destructible);
		this.life = destructible.life;
	}
	
	/* Setters ------------------------------------------------------------- */
	
	public void setLife(int life) {
		this.life = life;
	}
	
	/* Getters ------------------------------------------------------------- */
	
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
		currentAnimation = ObjectsAnimations.ANIMATE.getLabel();
	}
}
