package com.klob.bomberklob.model;

public class User {
	
	protected String pseudo;
	protected String userName;
	protected String password;
	protected boolean connectionAuto;
	protected boolean remenberPassword;
	protected String color;
	protected String menuPosition;
	protected int gameWon;
	protected int gameLost;
	
	/* Constructeur -------------------------------------------------------- */
	
	public User(String pseudo, String userName, String password,
			boolean connectionAuto, boolean remenberPassword, String color,
			String menuPosition, int gameWon, int gameLost) {
		this.pseudo = pseudo;
		this.userName = userName;
		this.password = password;
		this.connectionAuto = connectionAuto;
		this.remenberPassword = remenberPassword;
		this.color = color;
		this.menuPosition = menuPosition;
		this.gameWon = gameWon;
		this.gameLost = gameLost;
	}

	/* Getteurs ------------------------------------------------------------ */
	
	public String getPseudo() {
		return this.pseudo;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}

	public boolean getConnectionAuto() {
		return this.connectionAuto;
	}

	public boolean getRemenberPassword() {
		return this.remenberPassword;
	}

	public String getColor() {
		return this.color;
	}

	public String getMenuPosition() {
		return this.menuPosition;
	}

	public int getGameWon() {
		return this.gameWon;
	}

	public int getGameLost() {
		return this.gameLost;
	}
	
	/* Setteurs ------------------------------------------------------------ */
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConnectionAuto(boolean connectionAuto) {
		this.connectionAuto = connectionAuto;
	}

	public void setRemenberPassword(boolean remenberPassword) {
		this.remenberPassword = remenberPassword;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setMenuPosition(String menuPosition) {
		this.menuPosition = menuPosition;
	}

	public void setGameWon(int gameWon) {
		this.gameWon = gameWon;
	}

	public void setGameLost(int gameLost) {
		this.gameLost = gameLost;
	}	
}
