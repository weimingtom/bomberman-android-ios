package com.klob.Bomberklob.objects;

import java.util.Hashtable;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.klob.Bomberklob.objects.exceptions.BombPowerException;
import com.klob.Bomberklob.objects.exceptions.PlayersSpeedException;
import com.klob.Bomberklob.objects.exceptions.ShieldException;
import com.klob.Bomberklob.objects.exceptions.TimeBombException;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public abstract class Player extends Destructible {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Vector<Bomb> bombsPlanted = new Vector<Bomb>();
	//FIXME private ???[] bombsTypes;
	protected int powerExplosion;
	protected int timeExplosion;
	protected int speed;
	protected int shield;
	protected int bombNumber;
	protected boolean immortal;	
	
	/* Constructeurs ------------------------------------------------------- */
	
	public Player(String imageName, Hashtable<String, AnimationSequence> animations, String currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber, boolean immortal) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages, life);
		this.powerExplosion = powerExplosion;
		this.timeExplosion = timeExplosion;
		this.speed = speed;
		this.shield = shield;
		this.bombNumber = bombNumber;
		this.immortal = immortal;
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
	}
	
	/* Setters ------------------------------------------------------------- */
	
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
	
	public void setImmortal(boolean immortal) {
		this.immortal = immortal;
	}
	
	public void setPointX(int x) {
		this.position.x = x;
	}
	
	public void setPointY(int y) {
		this.position.y = y;
	}
	
	/* Getters ------------------------------------------------------------- */
	
	public Vector<Bomb> getBombsPlanted() {
		return this.bombsPlanted;
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
	
	public Bomb plantingBomb() {
		
		Point p = ResourcesManager.coToTile(position.x+(ResourcesManager.getSize()/2), position.y+(ResourcesManager.getSize()/2));
		p = ResourcesManager.tileToCo(p.x, p.y);
		
		for (int i = 0 ; i < bombsPlanted.size() ; i++) {
			if ( bombsPlanted.get(i).getPosition().x == p.x && bombsPlanted.get(i).getPosition().y == p.y) {
				return null;
			}
		}
		
		if ( bombNumber > bombsPlanted.size() ) {
			Bomb b = new Bomb("normal", ResourcesManager.getBombsAnimations().get("normal"), "destroy", true, 1, false, 1, 1, powerExplosion, timeExplosion);
			b.setPosition(p);
			bombsPlanted.add(b);
			return b;
		}
		return null;
	}

	@Override
	public void onDraw(Canvas canvas, int size) {
		int tileSize = ResourcesManager.getTileSize();
		canvas.drawBitmap(ResourcesManager.getBitmaps().get("players"), new Rect((this.getPoint().x*tileSize), (this.getPoint().y*(tileSize+(tileSize/2))), ((this.getPoint().x*tileSize)+tileSize), ( (this.getPoint().y*(tileSize+(tileSize/2)))+(tileSize+(tileSize/2)) )), new Rect(this.position.x, this.position.y-(size/2), this.position.x+size, this.position.y+size), null);
	}

	@Override
	public boolean isDestructible() {
		if ( this.immortal ) {
			return false;
		}
		return true;
	}
}
