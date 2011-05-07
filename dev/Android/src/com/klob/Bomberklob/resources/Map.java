package com.klob.Bomberklob.resources;

import java.io.Serializable;

public abstract class Map implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int width, height;

	protected String name;
	protected Point[] players;
	
	/* Constructeur -------------------------------------------------------- */
	public Map () {
		this.players = new Point[4];
	}
	
	/* Getters ------------------------------------------------------------- */
	
	public String getName() {
		return this.name;
	}
	
	public Point[] getPlayers() {
		return players;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	/* Setters ------------------------------------------------------------- */
	
	public void setName(String name) {
		this.name = name;
	}

}
