package com.klob.Bomberklob.objects;

import java.util.Hashtable;


public class Destructible extends AnimatedObjects {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int life;
	
	/* Constructeurs ------------------------------------------------------- */
	
	public Destructible(String imageName, boolean hit, int level, boolean fireWall, int damages, int life, Hashtable<String, AnimationSequence> animations, String currentAnimation) {
		super(imageName, hit, level, fireWall, damages, animations, currentAnimation);
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

}
