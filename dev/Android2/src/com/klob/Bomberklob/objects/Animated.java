package com.klob.Bomberklob.objects;

import java.util.Hashtable;

import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Animated extends Objects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Hashtable<String, AnimationSequence> animations = new Hashtable<String, AnimationSequence>();
	protected String currentAnimation;
	protected int currentFrame;
	protected int waitDelay;
	
	/* Constructeur -------------------------------------------------------- */
	
	public Animated (String imageName, boolean hit, int level, boolean fireWall, int damages, Hashtable<String, AnimationSequence> animations, String currentAnimation) {
		super(imageName, hit, level, fireWall, damages);
		this.animations = animations;
		this.currentAnimation = currentAnimation;
		this.currentFrame = 0;
		this.waitDelay = animations.get(currentAnimation).sequence.get(currentFrame).nextFrameDelay;
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
		this.waitDelay = animations.get(currentAnimation).sequence.get(currentFrame).nextFrameDelay;
		
	}
	
	public void setAnimations(Hashtable<String, AnimationSequence> animations) {
		this.animations = animations;
	}

	
	/* Méthodes Publiques -------------------------------------------------- */
	
	@Override
	public void update() {
		
		if(waitDelay==0) {
			if(animations.get(currentAnimation).canLoop) {
				if ( currentFrame == animations.get(currentAnimation).sequence.size()-1) {
					this.currentFrame=0;
				}
				else {
					this.currentFrame++;
				}
				this.waitDelay = animations.get(currentAnimation).sequence.get(currentFrame).nextFrameDelay;
			}
			else {
				if ( currentFrame < animations.get(currentAnimation).sequence.size()-1 ) {
					this.currentFrame++;
					this.waitDelay = animations.get(currentAnimation).sequence.get(currentFrame).nextFrameDelay;
				}
			}
		}
		else {
			waitDelay--;
		}
	}
	
	@Override
	public void destroy() {
		if ( !getCurrentAnimation().equals("destroy") ) {
			setCurrentAnimation("destroy");
		}
				
	}
	
	@Override
	public boolean hasAnimationFinished() {
		AnimationSequence as = animations.get(currentAnimation);
		if(currentFrame == as.sequence.size() -1 && currentAnimation.equals("destroy") ) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onDraw(Canvas canvas,int size) {
		int tileSize = ResourcesManager.getTileSize();
        canvas.drawBitmap(ResourcesManager.getBitmaps().get("animate"), new Rect(this.getPoint().x*tileSize, this.getPoint().y*tileSize, (this.getPoint().x*tileSize)+tileSize, (this.getPoint().y*tileSize)+tileSize), new Rect(this.position.x, this.position.y, (this.position.x)+size, (this.position.y)+size), null);
	}
}
