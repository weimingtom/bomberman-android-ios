package com.klob.Bomberklob.objects.exceptions;

public class ShieldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShieldException () {
		System.out.println("The player's shield must be greater than or equal to 0");
	}

}
