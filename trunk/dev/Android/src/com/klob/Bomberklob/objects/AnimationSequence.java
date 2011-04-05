package com.klob.Bomberklob.objects;

import java.io.Serializable;
import java.util.ArrayList;


public class AnimationSequence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArrayList<FrameInfo> sequence = new ArrayList<FrameInfo>();
	public boolean canLoop = false;
	public String name;
}
