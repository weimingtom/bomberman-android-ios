package com.klob.Bomberklob.objects;

import java.util.Hashtable;

import com.klob.Bomberklob.engine.Point;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Animated extends Objects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Hashtable<String, AnimationSequence > animations = new Hashtable<String, AnimationSequence>();
	protected String currentAnimation="idle";
	protected int currentFrame=0;
	protected int waitDelay=0;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Animated (int tileSize, int size, String imageName, boolean hit, int level, boolean fireWall) {
		super(tileSize, size, imageName, hit, level, fireWall);
	}
	
	public Animated (Animated animated) {
		super(animated);
		this.animations = animated.animations;
		this.currentAnimation = animated.currentAnimation;
		this.currentFrame = animated.currentFrame;
		this.waitDelay = animated.waitDelay;
	}
	
	/* Getteurs ------------------------------------------------------------ */

	public Hashtable<String, AnimationSequence> getAnimations() {
		return this.animations;
	}

	public String getCurrentAnimation() {
		return this.currentAnimation;
	}

	public int getCurrentFrame() {
		return this.currentFrame;
	}

	public int getWaitDelay() {
		return this.waitDelay;
	}
	
	public Point getPoint() {
		return this.animations.get(this.currentAnimation).sequence.get(this.currentFrame).point;
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public void setCurrentAnimation(String s) {
		this.currentAnimation = s;
		this.currentFrame = 0;
	}

	
	/* Méthodes Publiques -------------------------------------------------- */
	
	@Override
	public void update() {
		
		if(waitDelay==0) {

			if(animations.get(currentAnimation).canLoop && currentFrame == animations.get(currentAnimation).sequence.size()-1) {
				currentFrame=0;
			}
			else {
				currentFrame++;
				FrameInfo frameinfo= animations.get(currentAnimation).sequence.get(currentFrame);
				waitDelay = frameinfo.nextFrameDelay;
			}
		}
		else {
			waitDelay--;
		}
	}
	
	@Override
	public void destroy() {
		setCurrentAnimation("destroy");		
	}
	
	@Override
	public boolean hasAnimationFinished() {
		AnimationSequence as = animations.get(currentAnimation);
		if(currentFrame == as.sequence.size() -1 && !as.canLoop ) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
        canvas.drawBitmap(ResourcesManager.getBitmaps().get("animate"), new Rect(this.getPoint().x*this.tileSize, this.getPoint().y*this.tileSize, (this.getPoint().x*this.tileSize)+this.tileSize, (this.getPoint().y*this.tileSize)+this.tileSize), new Rect(this.position.x*this.size, this.position.y*this.size, (this.position.x*this.size)+this.size, (this.position.y*this.size)+this.size), null);
	}
}
