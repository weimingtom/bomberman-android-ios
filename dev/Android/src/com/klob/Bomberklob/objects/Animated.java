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
	
	protected Hashtable<String, AnimationSequence> animations = new Hashtable<String, AnimationSequence>();
	protected String currentAnimation="idle";
	protected int currentFrame=0;
	protected int waitDelay=0;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Animated (String imageName, boolean hit, int level, boolean fireWall) {
		super(imageName, hit, level, fireWall);
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
	
	public void setAnimations(Hashtable<String, AnimationSequence> animations) {
		this.animations = animations;
	}

	
	/* Méthodes Publiques -------------------------------------------------- */
	
	@Override
	public void update() {
		
		if(waitDelay==0) {

			if(animations.get(currentAnimation).canLoop && currentFrame == animations.get(currentAnimation).sequence.size()-1) {
				currentFrame=0;
			}
			else if ( currentFrame < animations.get(currentAnimation).sequence.size()-1 ){
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
	public void onDraw(Canvas canvas,int size) {
		int tileSize = ResourcesManager.getTileSize();
        canvas.drawBitmap(ResourcesManager.getBitmaps().get("animate"), new Rect(this.getPoint().x*tileSize, this.getPoint().y*tileSize, (this.getPoint().x*tileSize)+tileSize, (this.getPoint().y*tileSize)+tileSize), new Rect(this.position.x, this.position.y, (this.position.x)+size, (this.position.y)+size), null);
        update();
	}
}
