package com.klob.bomberklob.model;

public class User {
	
	protected String pseudo;
	protected String userName;
	protected String password;
	protected int connectionAuto;
	protected int remenberPassword;
	protected String color;
	protected String menuPosition;
	protected int gameWon;
	protected int gameLost;
	public User(String pseudo, String userName, String password,
			int connectionAuto, int remenberPassword, String color,
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
}
