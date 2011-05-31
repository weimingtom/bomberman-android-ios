package com.klob.Bomberklob.game;

public enum ColisionMapObjects {

	/**
	 * An empty area
	 */
	EMPTY(0),
	/**
	 * An area containing a block
	 */
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
