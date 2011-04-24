package com.klob.Bomberklob.resources;

import java.io.Serializable;

public class Rect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int	bottom = 0;
	public int	left = 0;
	public int	right = 0;
	public int	top = 0;
	
	public Rect() {}

	public Rect(int left, int top, int right, int bottom) {
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.top = top;
	}
	
	Rect(Rect r) {
		this.bottom = r.bottom;
		this.left = r.left;
		this.right = r.right;
		this.top = r.top;		
	}	
}
