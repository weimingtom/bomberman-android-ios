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
	/**
	 * An area containing a gape
	 */
	GAPE(2),
	/**
	 * A damage zone
	 */
	DAMAGE(3),
	/**
	 * A dangerous area
	 */
	DANGEROUS_AREA(4),
	/**
	 * An area containing a bomb
	 */
	BOMB(5),
	/**
	 * An area containing fire
	 */
	FIRE(6);
	
	protected int label;
	
	ColisionMapObjects(int oLabel) {
		this.label = oLabel;
	}

	public int getLabel() {
		return this.label;
	}
}
