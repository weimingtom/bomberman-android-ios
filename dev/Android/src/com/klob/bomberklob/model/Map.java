package com.klob.bomberklob.model;

public class Map {
	
	protected String name;
	protected String owner;
	protected boolean official;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Map(String name, String owner, boolean official) {
		this.name = name;
		this.owner = owner;
		this.official = official;
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
	
}
