package com.klob.bomberklob.objects.exceptions;

public class TimeBombException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeBombException() {
		System.out.println("The time of explosion of the bomb must be greater than or equal to 1");
	}
}
