package com.klob.Bomberklob.objects;

import java.util.Hashtable;
/**
 * 
 * Class describing destructible objects
 *
 */
public class Destructible extends Objects {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int life;
	
	/* Constructeurs ------------------------------------------------------- */
	
	/**
	 * Create a destructible object with the following parameters
	 * 
	 * @param imageName The name of the image representing the object
	 * @param animations The HashMap animation the object
	 * @param currentAnimation The current animation the object
	 * @param hit If the object is traversable
	 * @param level The level which is the object
	 * @param fireWall If the object blocks fire
	 * @param damages The number of damage that makes the object
	 * @param life Object life
	 */
	
	public Destructible(String imageName, Hashtable<String, AnimationSequence> animations, ObjectsAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life) {
		super(imageName, animations, currentAnimation.getLabel(), hit, level, fireWall, damages);
		this.life = life;
	}
	
	/**
	 * Create a copy of the destructible parameter
	 * @param destructible Destructible object
	 */
	public Destructible(Destructible destructible) {
		super(destructible);
		this.life = destructible.life;
	}
	
	/* Setters ------------------------------------------------------------- */
	
	/**
	 * Update the life of the destructible object
	 * @param life The new life of the destructible object
	 */
	public void setLife(int life) {
		this.life = life;
	}
	
	/* Getters ------------------------------------------------------------- */
	
	/**
	 * Returns the life of the destructible object
	 * @return The life of the destructible object
	 */
	public int getLifeNumber() {
		return this.life;
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public boolean isDestructible() {
		return true;
	}

	@Override
	public Destructible copy() {
		return new Destructible(this);
	}

	@Override
	public void destroy() {
		setCurrentAnimation(ObjectsAnimations.DESTROY);
	}
}
