package com.klob.Bomberklob.objects.exceptions;

public class BombPowerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BombPowerException() {
		System.out.println("The power of the bomb must be greater than or equal to 1");
	}
}