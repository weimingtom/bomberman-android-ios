package com.klob.Bomberklob.resources;

import java.io.Serializable;

public class Point implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int x;
	public int y;

	public Point () {}
	
	public Point (int x, int y) {
		this.x = x;
		this.y = y;
	}
}
