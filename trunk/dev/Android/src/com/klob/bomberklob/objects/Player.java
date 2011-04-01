package com.klob.bomberklob.objects;

import java.util.Vector;
import java.util.Map.Entry;

import com.klob.bomberklob.engine.Point;
import com.klob.bomberklob.objects.exceptions.BombPowerException;
import com.klob.bomberklob.objects.exceptions.PlayersSpeedException;
import com.klob.bomberklob.objects.exceptions.ShieldException;
import com.klob.bomberklob.objects.exceptions.TimeBombException;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Player extends Animated {
	
	protected Vector<Bombs> bombsPlanted = new Vector<Bombs>();
	//FIXME private ???[] bombsTypes;
	protected String color;
	protected int lifeNumber;
	protected int powerExplosion;
	protected int timeExplosion;
	protected int speed;
	protected int shield;
	protected int bombNumber;
	
	
	/* Getters ------------------------------------------------------------- */
	
	public Player(String imageName, Point position, String color, int lifeNumber, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber) {
		super(imageName, false, 1, false, position);
		this.color = color;
		this.lifeNumber = lifeNumber;
		this.powerExplosion = powerExplosion;
		this.timeExplosion = timeExplosion;
		this.speed = speed;
		this.shield = shield;
		this.bombNumber = bombNumber;
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

	public String getColor() {
		return this.color;
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
	public void resize() {
		for(Entry<String, AnimationSequence> entry : this.animations.entrySet()) {
		    String cle = entry.getKey();
		    AnimationSequence valeur = entry.getValue();
		    int tileSize = this.rm.getTileSize();
		    for (int i = 0; i < valeur.sequence.size(); i++ ) { // FIXME
		    	FrameInfo fi = valeur.sequence.get(i);
		    	fi.rect = new Rect(fi.point.x*tileSize, fi.point.y*tileSize, (fi.point.x*tileSize)+tileSize, (fi.point.y*tileSize)+tileSize+(tileSize/2));
		    	valeur.sequence.add(i, fi);
		    	i++;
		    }
		    this.animations.put(cle, valeur);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(this.rm.getBitmaps().get("player"), this.getRect(), new Rect(this.position.x, this.position.y-(this.rm.getTileSize()/2), this.position.x+this.rm.getTileSize(), this.position.y+this.rm.getTileSize()), null);
	}

}
