package com.klob.Bomberklob.objects;

import java.io.Serializable;
import java.util.Hashtable;

import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Objects implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String imageName;
	protected boolean hit;
	protected int level;
	protected int damages;
	protected boolean fireWall;
	protected Point position;
	
	protected Hashtable<String, AnimationSequence> animations = new Hashtable<String, AnimationSequence>();
	protected String currentAnimation;
	protected int currentFrame;
	protected int waitDelay;
	
	/* Contructeur --------------------------------------------------------- */
	
	public Objects(String imageName, Hashtable<String, AnimationSequence> animations, String currentAnimation, boolean hit, int level, boolean fireWall, int damages) {
		this.imageName = imageName;
		this.hit = hit;
		this.level = level;
		this.fireWall = fireWall;
		this.position = null;
		this.damages = damages;
		
		this.animations = animations;
		this.currentAnimation = currentAnimation;
		this.currentFrame = 0;
		this.waitDelay = animations.get(this.currentAnimation).sequence.get(this.currentFrame).nextFrameDelay;
	}
	
	public Objects(Objects objects) {
		this.imageName = objects.imageName;
		this.hit = objects.hit;
		this.level = objects.level;
		this.fireWall = objects.fireWall;
		this.position = objects.position;
		this.damages = objects.damages;
		
		this.animations = objects.animations;
		this.currentAnimation = objects.currentAnimation;
		this.currentFrame = objects.currentFrame;
		this.waitDelay = this.animations.get(this.currentAnimation).sequence.get(this.currentFrame).nextFrameDelay;
	}
	
	/* Getters ------------------------------------------------------------- */
	
	public boolean isHit() {
		return this.hit;
	}

	public int getLevel() {
		return this.level;
	}

	public boolean isFireWall() {
		return this.fireWall;
	}

	public Point getPosition() {
		return this.position;
	}
	
	public String getImageName() {
		return imageName;
	}
	
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
	
	/* Setteurs ------------------------------------------------------------ */

	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void setAnimations(Hashtable<String, AnimationSequence> animations) {
		this.animations = animations;
	}
	
	public void setCurrentAnimation(ObjectsAnimations animation) {
		if ( animations.get(currentAnimation) != null ) {
			this.currentAnimation = animation.getLabel();
			this.currentFrame = 0;
			this.waitDelay = animations.get(currentAnimation).sequence.get(currentFrame).nextFrameDelay;
		}
	}
	
	/* Méthodes abstraites publiques --------------------------------------- */
	
	public abstract Objects copy();
	
	public abstract boolean isDestructible();
	
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
	
	public abstract void destroy();
	
	public boolean hasAnimationFinished() {
		AnimationSequence as = animations.get(currentAnimation);
		if(currentFrame == as.sequence.size()-1 ) {
			return true;
		}
		return false;
	}
	
	public void onDraw(Canvas canvas,int size) {
		int tileSize = ResourcesManager.getTileSize();
        canvas.drawBitmap(ResourcesManager.getBitmaps().get("objects"), new Rect(this.getPoint().x*tileSize, this.getPoint().y*tileSize, (this.getPoint().x*tileSize)+tileSize, (this.getPoint().y*tileSize)+tileSize), new Rect(this.position.x, this.position.y, (this.position.x)+size, (this.position.y)+size), null);
	}
	
	@Override
	public String toString() {
		return "Name : " +imageName+ "| Position (" + position.x + "," + position.y + ")";
	}
}
