package com.klob.Bomberklob.model;

public class User {
	
	protected String pseudo;
	protected String userName;
	protected String password;
	protected boolean connectionAuto;
	protected boolean rememberPassword;
	protected String color;
	protected int menuPosition;
	protected int gameWon;
	protected int gameLost;
	
	/* Constructeur -------------------------------------------------------- */
	/**
	 * Constructor of User
	 * @param pseudo
	 * @param userName
	 * @param password
	 * @param connectionAuto
	 * @param remenberPassword
	 * @param color
	 * @param menuPosition
	 * @param gameWon
	 * @param gameLost
	 */
	public User(String pseudo, String userName, String password,
			boolean connectionAuto, boolean remenberPassword, String color,
			int menuPosition, int gameWon, int gameLost) {
		this.pseudo = pseudo;
		this.userName = userName;
		this.password = password;
		this.connectionAuto = connectionAuto;
		this.rememberPassword = remenberPassword;
		this.color = color;
		this.menuPosition = menuPosition;
		this.gameWon = gameWon;
		this.gameLost = gameLost;
	}

	/* Getteurs ------------------------------------------------------------ */
	
	/**
	 * Getter of the pseudo
	 * @return String pseudo
	 */
	public String getPseudo() {
		return this.pseudo;
	}

	/**
	 * Getter of the userName
	 * @return String userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Getter of the password
	 * @return String password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Getter of the connectionAuto status
	 * @return boolean connectionAuto
	 */
	public boolean getConnectionAuto() {
		return this.connectionAuto;
	}

	/**
	 * Getter of rememberPassord status
	 * @return boolean rememberPassword
	 */
	public boolean getRememberPassword() {
		return this.rememberPassword;
	}

	/**
	 * Getter of color preference
	 * @return String color
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * Getter of menu position
	 * @return int menuPosition
	 */
	public int getMenuPosition() {
		return this.menuPosition;
	}

	/**
	 * Getter of number of game won
	 * @return int gameWon
	 */
	public int getGameWon() {
		return this.gameWon;
	}

	/**
	 * Getter of number of game lost
	 * @return int gameLost
	 */
	public int getGameLost() {
		return this.gameLost;
	}
	
	/* Setteurs ------------------------------------------------------------ */
	
	/**
	 * Setter of attribute pseudo
	 * @param String pseudo
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Setter of username
	 * @param String userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Setter of attribute password
	 * @param String password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Setter of attribute connectionAuto
	 * @param boolean connectionAuto
	 */
	public void setConnectionAuto(boolean connectionAuto) {
		this.connectionAuto = connectionAuto;
	}

	/**
	 * Setter of attribute rememberPassword
	 * @param boolean rememberPassword
	 */
	public void setRememberPassword(boolean rememberPassword) {
		this.rememberPassword = rememberPassword;
	}

	/**
	 * Setter of attribute color
	 * @param String color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Setter of attribute menuPosition
	 * @param int menuPosition
	 */
	public void setMenuPosition(int menuPosition) {
		this.menuPosition = menuPosition;
	}


	/**
	 * Setter of number of game won
	 * @param int gameWon
	 */
	public void setGameWon(int gameWon) {
		this.gameWon = gameWon;
	}

	/**
	 * Setter of number of game lost
	 * @param int gameLost
	 */
	public void setGameLost(int gameLost) {
		this.gameLost = gameLost;
	}	
}
