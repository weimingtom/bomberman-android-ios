package com.klob.Bomberklob.model;

public class Map {
	
	private String name;
	private String owner;
	private boolean official;
	private String bitmapPath;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Map(String name, String owner, boolean official, String bitmapPath) {
		this.name = name;
		this.owner = owner;
		this.official = official;
		this.bitmapPath = bitmapPath;
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public String getName() {
		return this.name;
	}

	public String getOwner() {
		return this.owner;
	}

	public boolean isOfficial() {
		return this.official;
	}
	
	public String getPath() {
		return this.bitmapPath;
	}
	
	/* Setteurs ------------------------------------------------------------ */
	
	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setOfficial(boolean official) {
		this.official = official;
	}
	
	public void setBitmapPath(String bitmapPath) {
		this.bitmapPath = bitmapPath;
	}
	
}
