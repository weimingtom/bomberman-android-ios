package com.klob.Bomberklob.model;

public class Map {
	
	private String name;
	private String owner;
	private boolean official;
	
	/* Constructeur -------------------------------------------------------- */
	
	/**
	 * Constructor Map
	 * @param name
	 * @param owner
	 * @param official
	 */
	public Map(String name, String owner, boolean official) {
		this.name = name;
		this.owner = owner;
		this.official = official;
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	/**
	 * Getter of attribute name of map
	 * @return String map name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter of attribute owner of map
	 * @return String map owner
	 */
	public String getOwner() {
		return this.owner;
	}

	/**
	 * Getter of attribute official of map
	 * @return boolean true if OK, false else
	 */
	public boolean isOfficial() {
		return this.official;
	}
	
	/* Setteurs ------------------------------------------------------------ */
	
	/**
	 * Setter of attribute official of map
	 * @param String map name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter of attribute owner of map
	 * @param String owner name
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Setter of attribute official of map
	 * @param boolean official
	 */
	public void setOfficial(boolean official) {
		this.official = official;
	}
	
}
