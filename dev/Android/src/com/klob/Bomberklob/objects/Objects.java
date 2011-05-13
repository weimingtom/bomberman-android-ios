package com.klob.Bomberklob.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Hashtable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;

import com.klob.Bomberklob.resources.Paint;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

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
	protected String sound = "";
	protected transient Rect rect;

	//TODO
	protected ColorFilter cf;
	protected Paint paint;


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
		this.sound = animations.get(this.currentAnimation).sound;

		this.paint = new Paint();
		this.rect = new Rect();
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
		this.sound = this.animations.get(this.currentAnimation).sound;

		this.paint = objects.paint;
		this.rect = objects.rect;
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

	public Paint getPaint() {
		return this.paint;
	}

	public ColorFilter getColorFilter() {
		return this.cf;
	}

	public int getDamage() {
		return this.damages;
	}

	public Rect getRect() {
		return this.animations.get(this.currentAnimation).sequence.get(this.currentFrame).rect;
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
			this.sound = animations.get(currentAnimation).sound;

			if ( !sound.equals("") ) {
				ResourcesManager.playSoundPool(this.sound);
			}
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
		if(currentFrame == as.sequence.size()-1 && this.waitDelay == 0) {
			return true;
		}
		return false;
	}

	public void onDraw(Canvas canvas,int size) {

		Rect rect = this.getRect();
		int i = (rect.right - rect.left) - ResourcesManager.getTileSize();

		if ( i != 0 ) {
			i = (i*ResourcesManager.getSize())/ResourcesManager.getTileSize();
		}

		this.rect.left = this.position.x-i;
		this.rect.top = this.position.y;
		this.rect.right = (this.position.x)+size+i;
		this.rect.bottom = (this.position.y)+size;

		canvas.drawBitmap(ResourcesManager.getBitmaps().get("objects"), rect, this.rect, this.paint);
	}

	public void playCurrentAnimationSound() {
		if ( !sound.equals("") ) {
			ResourcesManager.playSoundPool(this.sound);
		}
	}

	@Override
	public String toString() {
		return "Name : " +imageName+ "| Position (" + position.x + "," + position.y + ")";
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// appel des mécanismes de désérialisation par défaut
		in.defaultReadObject();

		this.rect = new Rect();
	}
}
