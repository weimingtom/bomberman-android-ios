package com.klob.Bomberklob.objects;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.klob.Bomberklob.objects.exceptions.BombPowerException;
import com.klob.Bomberklob.objects.exceptions.PlayersSpeedException;
import com.klob.Bomberklob.objects.exceptions.ShieldException;
import com.klob.Bomberklob.objects.exceptions.TimeBombException;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class Player extends Animated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Vector<Bombs> bombsPlanted = new Vector<Bombs>();
	//FIXME private ???[] bombsTypes;
	protected int lifeNumber;
	protected int powerExplosion;
	protected int timeExplosion;
	protected int speed;
	protected int shield;
	protected int bombNumber;
	
	
	/* Getters ------------------------------------------------------------- */
	
	public Player(String imageName, int lifeNumber, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber) {
		super(imageName, false, 1, false);
		this.lifeNumber = lifeNumber;
		this.powerExplosion = powerExplosion;
		this.timeExplosion = timeExplosion;
		this.speed = speed;
		this.shield = shield;
		this.bombNumber = bombNumber;
	}
	
	public Player(Player player) {
		super(player);
		this.lifeNumber = player.lifeNumber;
		this.powerExplosion = player.powerExplosion;
		this.timeExplosion = player.timeExplosion;
		this.speed = player.speed;
		this.shield = player.shield;
		this.bombNumber = player.bombNumber;
	}
	
	/* Setters ------------------------------------------------------------- */
	
	public void setLife(int life) {
		this.lifeNumber = life;
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
	
	public void setPointX(int x) {
		this.position.x = x;
	}
	
	public void setPointY(int y) {
		this.position.y = y;
	}
	
	/* Getters ------------------------------------------------------------- */
	
	public Vector<Bombs> getBombsPlanted() {
		return this.bombsPlanted;
	}

	public int getLifeNumber() {
		return this.lifeNumber;
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

	public int getShield() {
		return this.shield;
	}

	public int getBombNumber() {
		return this.bombNumber;
	}
	
	/* Méthodes publiques -------------------------------------------------- */

	@Override
	public void onDraw(Canvas canvas, int size) {
		int tileSize = ResourcesManager.getTileSize();
		canvas.drawBitmap(ResourcesManager.getBitmaps().get("players"), new Rect((this.getPoint().x*tileSize), (this.getPoint().y*(tileSize+(tileSize/2))), ((this.getPoint().x*tileSize)+tileSize), ( (this.getPoint().y*(tileSize+(tileSize/2)))+(tileSize+(tileSize/2)) )), new Rect(this.position.x, this.position.y-(size/2), this.position.x+size, this.position.y+size), null);
        update();
	}

	// FIXME Bonus immortel ?
	@Override
	public boolean isDestructible() {
		return true;
	}
}
