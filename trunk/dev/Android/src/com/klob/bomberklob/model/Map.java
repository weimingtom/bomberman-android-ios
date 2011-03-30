package com.klob.bomberklob.model;

public class Map {
	
	private String name;
	private String owner;
	private boolean official;
	private int id;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Map(String name, String owner, boolean official, int id) {
		this.name = name;
		this.owner = owner;
		this.official = official;
		this.id = id;
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
	
	public int getId() {
		return this.id;
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
	
	public void setId(int id) {
		this.id = id;
	}
	
}
