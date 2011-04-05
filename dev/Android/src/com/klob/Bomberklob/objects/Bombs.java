package com.klob.Bomberklob.objects;

public class Bombs extends Animated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected int power;
	protected int time;
	//FIXME protected ??? type

	public Bombs(String imageName, boolean hit, int level, boolean fireWall, int power, int time) {
		super(imageName, hit, level, fireWall);
		this.power = power;
		this.time = time;
	}
	
	public Bombs(Bombs bombs) {
		super(bombs);
		this.power = bombs.power;
		this.time = bombs.time;
	}

	public int getPower() {
		return power;
	}

	public int getTime() {
		return time;
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	// FIXME Pour les mines
	@Override
	public boolean isDestructible() {
		return true;
	}

}
