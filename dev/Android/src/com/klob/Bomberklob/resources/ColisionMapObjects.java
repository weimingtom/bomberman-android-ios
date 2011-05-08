package com.klob.Bomberklob.resources;

public enum ColisionMapObjects {

	EMPTY(0), // Empty
	// Objects that are display on the screen
	BLOCK(1), // Objects that are not traversable by player and fire
	GAPE(2),  // Objects that are just traversable by fire and not by player
	DAMAGE(3), 
	   
	// Objects that are just for ai mannaging
	DANGEROUS_AREA(4), // It is where, there will be explosions fire
	
	BOMB(5);
	
	protected int label;
	
	ColisionMapObjects(int oLabel) {
		this.label = oLabel;
	}

	public int getLabel() {
		return this.label;
	}
}
