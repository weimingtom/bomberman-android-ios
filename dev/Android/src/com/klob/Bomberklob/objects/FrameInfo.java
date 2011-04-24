package com.klob.Bomberklob.objects;

import java.io.Serializable;

import com.klob.Bomberklob.resources.Rect;

public class FrameInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Rect rect = new Rect();
	public int nextFrameDelay;
}
