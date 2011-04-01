package com.klob.bomberklob.objects;

import java.util.Hashtable;
import java.util.Map.Entry;

import com.klob.bomberklob.engine.Point;

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
	
	public Animated (String imageName, boolean hit, int level, boolean fireWall, Point position) {
		super(imageName, hit, level, fireWall, position);
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
	
	public Rect getRect() {
		return this.animations.get(this.currentAnimation).sequence.get(this.currentFrame).rect;
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public void setCurrentAnimation(String s) {
		this.currentAnimation = s;
		this.currentFrame = 0;
	}

	
	/* Méthodes Publiques -------------------------------------------------- */
	

	@Override
	public void resize() {
		
		for(Entry<String, AnimationSequence> entry : this.animations.entrySet()) {
		    String cle = entry.getKey();
		    AnimationSequence valeur = entry.getValue();
		    int tileSize = this.rm.getTileSize();
		    for (int i = 0; i < valeur.sequence.size(); i++ ) { // FIXME
		    	FrameInfo fi = valeur.sequence.get(i);
		    	fi.rect = new Rect(fi.point.x*tileSize, fi.point.y*tileSize, (fi.point.x*tileSize)+tileSize, (fi.point.y*tileSize)+tileSize);
		    	valeur.sequence.add(i, fi);
		    	i++;
		    }
		    this.animations.put(cle, valeur);
		}
	}
	
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
		int tileSize = this.rm.getTileSize();
        canvas.drawBitmap(this.rm.getBitmaps().get("animate"), this.getRect(), new Rect(this.position.x*tileSize, this.position.y*tileSize, (this.position.x*tileSize)+tileSize, (this.position.y*tileSize)+tileSize), this.paint);
	}
}
