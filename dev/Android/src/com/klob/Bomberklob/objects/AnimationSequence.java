package com.klob.Bomberklob.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * AnimationSequence class
 *
 */
public class AnimationSequence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Table of FrameInfo
	 */
	public ArrayList<FrameInfo> sequence;
	
	/**
	 * True if the animation loop false otherwise
	 */
	public boolean canLoop;
	
	/**
	 * Animation name
	 */
	public String name;
	
	/**
	 * Animation sound
	 */
	public String sound;
	
	/**
	 * Default constructor
	 */
	public AnimationSequence() {
		this.sequence = new ArrayList<FrameInfo>();
		this.canLoop = false;
		this.name = "";
		this.sound = "";
	}
	
	/**
	 * Copy constructor
	 * @param animationSequence AnimationSequence to be copied
	 */
	public AnimationSequence(AnimationSequence animationSequence) {
		this.sequence = animationSequence.sequence;
		this.canLoop = animationSequence.canLoop;
		this.name = animationSequence.name;
		this.sound = animationSequence.sound;
	}
	
	/**
	 * Return a copy of the current instance
	 * @return A copy of the current instance
	 */
	public AnimationSequence copy() {
		return new AnimationSequence(this);
	}
}
