package com.klob.Bomberklob.objects;

import java.io.Serializable;
import java.util.ArrayList;


public class AnimationSequence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArrayList<FrameInfo> sequence;
	public boolean canLoop;
	public String name;
	public String sound;
	
	public AnimationSequence() {
		this.sequence = new ArrayList<FrameInfo>();
		this.canLoop = false;
		this.name = "";
		this.sound = "";
	}
	
	public AnimationSequence(AnimationSequence animationSequence) {
		this.sequence = animationSequence.sequence;
		this.canLoop = animationSequence.canLoop;
		this.name = animationSequence.name;
		this.sound = animationSequence.sound;
	}
	
	public AnimationSequence copy() {
		return new AnimationSequence(this);
	}
}
