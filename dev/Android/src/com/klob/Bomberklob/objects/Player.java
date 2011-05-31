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

	/**
	 * Bombs selected by the player
	 */
	protected String bombSelected = "normal";
	
	/**
	 * Explosive power of its bombs
	 */
	protected int powerExplosion;
	
	/**
	 * Time of explosion of his bombs
	 */
	protected int timeExplosion;
	
	/**
	 * Player's speed
	 */
	protected int speed;
	
	/**
	 * Player's life
	 */
	protected int life;
	
	/**
	 * Player's shield
	 */
	protected int shield;
	
	/**
	 * Number of bombs the player
	 */
	protected int bombNumber;
	
	/**
	 * Number of seconds of immortality Player
	 */
	protected int immortal; 

	/**
	 * Objective of the player
	 */
	protected Point objective;


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
		this.objective = new Point();
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
		this.objective = player.objective;
	}

	/* Setters ------------------------------------------------------------- */

	/**
	 * Updates the player's objective
	 * @param objective The player's objective
	 */
	public void setObjectif(Point objective) {
		this.objective.x = objective.x;
		this.objective.y = objective.y;
	}

	/**
	 * Updates the player's speed
	 * @param speed The player's speed
	 */	
	public void setSpeed(int speed) throws PlayersSpeedException {
		if ( speed > 0 ) {
			this.speed = speed;
		}
		else {
			throw new PlayersSpeedException();
		}
	}

	/**
	 * Updates the player's power bombs
	 * @param bombPower The player's power bombs
	 */	
	public void setBombPower(int bombPower) throws BombPowerException {
		if ( bombPower > 0) {
			this.powerExplosion = bombPower;
		}
		else {
			throw new BombPowerException();
		}
	}       

	/**
	 * Updates the number of bombs the player
	 * @param bombPower The number of bombs the player
	 */	
	public void setBombNumber(int bombNumber) throws BombPowerException {
		if ( bombNumber >= 0) {
			this.bombNumber = bombNumber;
		}
		else {
			throw new BombPowerException();
		}
	}

	/**
	 * Updates the player's time bombs
	 * @param bombTime The player's time bombs
	 */	
	public void setBombTime(int bombTime) throws TimeBombException {
		if ( bombTime > 1 ) {
			this.timeExplosion = bombTime;
		}
		else {
			throw new TimeBombException();
		}
	}

	/**
	 * Updates the player's shield
	 * @param shield The player's shield
	 */	
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
		return objective;
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

	/**
	 * Returns the value of the player shield
	 * @return The value of the player shield
	 */
	public int getShield() {
		return this.shield;
	}

	/**
	 * Returns the number of bombs the player
	 * @return The number of bombs the player
	 */
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
	
	/**
	 * Move the player one pixel up
	 */
	public void moveUp() {
		this.position.y--;
	}
	
	/**
	 * Move the player one pixel down
	 */
	public void moveDown() {
		this.position.y++;
	}
	
	/**
	 * Move the player from one pixel to the left
	 */
	
	public void moveLeft() {
		this.position.x--;
	}
	
	/**
	 * Move the player from one pixel to the right
	 */
	public void moveRight() {
		this.position.x++;
	}

	@Override
	public boolean isDestructible() {
		if ( this.immortal != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Reduces the player's life
	 */
	public void decreaseLife() {
		if ( this.life > 0 ) {
			this.life--;
		}
	}

	/**
	 * Increases the player's bombs
	 */
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
	
	/**
	 * Stop player
	 */
	public void stopPlayer() {

		if ( this.currentAnimation ==  PlayerAnimations.RIGHT.getLabel()) {
			setCurrentAnimation(PlayerAnimations.STOP_RIGHT);
		}
		else if ( this.currentAnimation ==  PlayerAnimations.LEFT.getLabel()) {
			setCurrentAnimation(PlayerAnimations.STOP_LEFT);
		}
		else if ( this.currentAnimation ==  PlayerAnimations.UP.getLabel()) {
			setCurrentAnimation(PlayerAnimations.STOP_UP);
		}
		else if ( this.currentAnimation ==  PlayerAnimations.DOWN.getLabel()) {
			setCurrentAnimation(PlayerAnimations.STOP_DOWN);
		}
		else if ( this.currentAnimation ==  PlayerAnimations.DOWN_RIGHT.getLabel()) {
			setCurrentAnimation(PlayerAnimations.STOP_DOWN_RIGHT);
		}
		else if ( this.currentAnimation ==  PlayerAnimations.DOWN_LEFT.getLabel()) {
			setCurrentAnimation(PlayerAnimations.STOP_DOWN_LEFT);
		}
		else if ( this.currentAnimation ==  PlayerAnimations.UP_RIGHT.getLabel()) {
			setCurrentAnimation(PlayerAnimations.STOP_UP_RIGHT);
		}
		else if ( this.currentAnimation ==  PlayerAnimations.UP_LEFT.getLabel()) {
			setCurrentAnimation(PlayerAnimations.STOP_UP_LEFT);
		}
	}
}
