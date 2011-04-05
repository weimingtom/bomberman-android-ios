package com.klob.Bomberklob.objects;

import java.io.Serializable;

import com.klob.Bomberklob.engine.Point;

public class FrameInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Point point = new Point();
	public int nextFrameDelay;
}
