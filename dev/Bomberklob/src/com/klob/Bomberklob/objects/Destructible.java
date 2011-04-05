package com.klob.Bomberklob.objects;


public class Destructible extends AnimatedObjects {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int life;
	
	/* Constructeurs ------------------------------------------------------- */
	
	public Destructible(int tileSize, int size, String imageName, boolean hit, int level, boolean fireWall, int life) {
		super(tileSize, size, imageName, hit, level, fireWall);
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

}
