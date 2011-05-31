package com.klob.Bomberklob.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Hashtable;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.klob.Bomberklob.resources.Paint;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public abstract class Objects implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the image
	 */
	protected String imageName;
	
	/**
	 * Boolean saying if the object is traversable or not
	 */
	protected boolean hit;
	
	/**
	 * Level of the object on the map
	 */
	protected int level;
	
	/**
	 * Damage Item
	 */
	protected int damages;
	
	/**
	 * 
	 */
	protected boolean fireWall;
	
	/**
	 * Position of the object
	 */
	protected Point position;

	/**
	 * Object's animations
	 */
	protected Hashtable<String, AnimationSequence> animations = new Hashtable<String, AnimationSequence>();
	
	/**
	 * Current animation
	 */
	protected String currentAnimation;
	
	/**
	 * Current frame
	 */
	protected int currentFrame;
	
	/**
	 * Delay for the next animation
	 */
	protected int waitDelay;
	
	/**
	 * Sound of the current animation
	 */
	protected String sound = "";
	
	/**
	 * Rectangular coordinates of the image of the current animation
	 */
	protected transient Rect rect;

	/**
	 * 
	 */
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

	/**
	 * Returns the object level in the map
	 * @return The object level in the map
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * Returns true if the object blocks the fire
	 * @return True if the object blocks the fire
	 */
	public boolean isFireWall() {
		return this.fireWall;
	}

	/**
	 * Returns the object's position
	 * @return An point containing the object's position
	 */
	public Point getPosition() {
		return this.position;
	}

	/**
	 * Returns the image name of the object
	 * @return The image name of the object
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Returns animations of the object
	 * @return Animations of the object
	 */
	public Hashtable<String, AnimationSequence> getAnimations() {
		return this.animations;
	}

	/**
	 * Return the current animation
	 * @return The current animation
	 */
	public String getCurrentAnimation() {
		return this.currentAnimation;
	}

	/**
	 * Return the current frame
	 * @return The current frame
	 */
	public int getCurrentFrame() {
		return this.currentFrame;
	}

	/**
	 * Return the delay of the current frame
	 * @return The delay of the current frame
	 */
	public int getWaitDelay() {
		return this.waitDelay;
	}

	/**
	 * Returns damages of the object
	 * @return Damages of the object
	 */
	public int getDamage() {
		return this.damages;
	}

	/**
	 * Return the rectangle corresponding to the coordinates of the image of the object
	 * @return The rectangle corresponding to the coordinates of the image of the object
	 */
	public Rect getRect() {
		return this.animations.get(this.currentAnimation).sequence.get(this.currentFrame).rect;
	}

	/* Setteurs ------------------------------------------------------------ */

	/**
	 * Updates the position of the object
	 * @return position The position of the object
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
	
	/**
	 * Updates the position of the object
	 * @param x The position on the abscissa
	 * @param y The position on the orderly
	 */
	public void setPosition(int x, int y) {
		this.position.x = x;
		this.position.y = y;
	}

	/**
	 * Updates animations of the object
	 * @param animations of the object
	 */
	public void setAnimations(Hashtable<String, AnimationSequence> animations) {
		this.animations = animations;
	}

	/**
	 * Updates the current animation
	 * @param animation The animation required
	 */
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

	/**
	 * Returns a copy of the current object
	 * @return A copy of the current object
	 */
	public abstract Objects copy();

	/**
	 * Returns true if the current object is destructible
	 * @return True if the current object is destructible
	 */
	public abstract boolean isDestructible();

	
	/**
	 * Update the current objet
	 */	
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

	/**
	 * Destroys the object
	 */
	public abstract void destroy();

	/**
	 * Returns true if the current animation is over
	 * 
	 * @return True if the current animation is over
	 */
	public boolean hasAnimationFinished() {
		AnimationSequence as = animations.get(currentAnimation);
		if(currentFrame == as.sequence.size()-1 && this.waitDelay == 0) {
			return true;
		}
		return false;
	}

	
	/**
	 * Draws the current object in the canvas according to the desired size
	 * 
	 * @param canvas A canvas
	 * @param size The desired size
	 */
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

	/**
	 * Plays the sound of the current animation
	 */
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
