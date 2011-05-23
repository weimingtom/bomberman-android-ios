package com.klob.Bomberklob.objects;

import java.util.Hashtable;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.klob.Bomberklob.objects.exceptions.BombPowerException;
import com.klob.Bomberklob.objects.exceptions.PlayersSpeedException;
import com.klob.Bomberklob.objects.exceptions.ShieldException;
import com.klob.Bomberklob.objects.exceptions.TimeBombException;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public abstract class Player extends Objects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//FIXME private ???[] bombsTypes;

	protected String bombSelected = "normal";

	protected int powerExplosion;
	protected int timeExplosion;
	protected int speed;
	protected int life;
	protected int shield;
	protected int bombNumber;
	protected int immortal; 

	protected Point objectif;


	/* Constructeurs ------------------------------------------------------- */

	public Player(String imageName, Hashtable<String, AnimationSequence> animations, PlayerAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber, int immortal) {
		super(imageName, animations, currentAnimation.getLabel(), hit, level, fireWall, damages);
		this.powerExplosion = powerExplosion;
		this.timeExplosion = timeExplosion;
		this.speed = speed;
		this.shield = shield;
		this.life = life;
		this.bombNumber = bombNumber;
		this.immortal = immortal;
		this.objectif = new Point();
	}

	public Player(Player player) {
		super(player);
		this.animations = player.animations;
		this.powerExplosion = player.powerExplosion;
		this.timeExplosion = player.timeExplosion;
		this.speed = player.speed;
		this.shield = player.shield;
		this.bombNumber = player.bombNumber;
		this.immortal = player.immortal;
		this.objectif = player.objectif;
	}

	/* Setters ------------------------------------------------------------- */


	public void setObjectif(Point objectif) {
		this.objectif.x = objectif.x;
		this.objectif.y = objectif.y;
	}

	public void setSpeed(int speed) throws PlayersSpeedException {
		if ( speed > 0 ) {
			this.speed = speed;
		}
		else {
			throw new PlayersSpeedException();
		}
	}

	public void setBombPower(int bombPower) throws BombPowerException {
		if ( bombPower > 0) {
			this.powerExplosion = bombPower;
		}
		else {
			throw new BombPowerException();
		}
	}       

	public void setBombNumber(int bombNumber) throws BombPowerException {
		if ( bombNumber >= 0) {
			this.bombNumber = bombNumber;
		}
		else {
			throw new BombPowerException();
		}
	}

	public void setBombTime(int bombTime) throws TimeBombException {
		if ( bombTime > 1 ) {
			this.timeExplosion = bombTime;
		}
		else {
			throw new TimeBombException();
		}
	}

	public void setShield(int shield) throws ShieldException {
		if ( shield >= 0 ) {
			this.shield = shield;
		}
		else {
			throw new ShieldException();
		}
	}

	public void setImmortal(int immortal) {
		this.immortal = immortal;
	}

	public void setPointX(int x) {
		this.position.x = x;
	}

	public void setPointY(int y) {
		this.position.y = y;
	}

	public void setCurrentAnimation(PlayerAnimations animation) {
		if ( animations.get(currentAnimation) != null && !currentAnimation.equals(animation.getLabel())) {
			this.currentAnimation = animation.getLabel();
			this.currentFrame = 0;
			this.waitDelay = animations.get(currentAnimation).sequence.get(currentFrame).nextFrameDelay;
		}
	}

	public String getBombSelected() {
		return bombSelected;
	}

	/* Getters ------------------------------------------------------------- */

	public Point getObjectif() {
		return objectif;
	}

	public int getPowerExplosion() {
		return this.powerExplosion;
	}

	public int getTimeExplosion() {
		return this.timeExplosion;
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getLife() {
		return this.life;
	}

	public int getShield() {
		return this.shield;
	}

	public int getBombNumber() {
		return this.bombNumber;
	}

	/* Méthodes publiques -------------------------------------------------- */

	@Override
	public void onDraw(Canvas canvas, int size) {

		Rect rect = this.getRect();
		int i = (rect.right - rect.left) - ResourcesManager.getTileSize();

		if ( i != 0 ) {
			i = ((i*ResourcesManager.getSize())/ResourcesManager.getTileSize())/2;
		}

		this.rect.left = this.position.x-i;
		this.rect.top = this.position.y-(size/2);
		this.rect.right = this.position.x+size+i;
		this.rect.bottom = this.position.y+size;

		canvas.drawBitmap(ResourcesManager.getBitmaps().get("players"), rect, this.rect, this.paint);
	}

	@Override
	public boolean isDestructible() {
		if ( this.immortal != 0) {
			return false;
		}
		return true;
	}

	public void decreaseLife() {
		if ( this.life > 0 ) {
			this.life--;
		}
	}

	public void increaseBombs() {
		this.bombNumber++;
	}

	@Override
	public void destroy() {
		currentAnimation = PlayerAnimations.KILL.getLabel();
	}

	@Override
	public void update() {

		super.update();

		if ( immortal%2 == 0) {
			this.paint.setAlpha(255);
		}
		else {
			this.paint.setAlpha(0);
		}

		if (immortal > 0 ){
			immortal--;
		}
	}
}
