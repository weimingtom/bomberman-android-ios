package com.klob.Bomberklob.objects.exceptions;

public class PlayersSpeedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayersSpeedException () {
		System.out.println("The player's speed must be greater than or equal to 1");
	}
}
