package com.klob.Bomberklob.game;

public enum ColisionMapObjects {

	EMPTY(0),
	BLOCK(1),
	GAPE(2),
	DAMAGE(3),
	DANGEROUS_AREA(4),
	BOMB(5),
	FIRE(6);
	
	protected int label;
	
	ColisionMapObjects(int oLabel) {
		this.label = oLabel;
	}

	public int getLabel() {
		return this.label;
	}
}
