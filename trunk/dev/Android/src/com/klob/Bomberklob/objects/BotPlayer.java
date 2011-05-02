package com.klob.Bomberklob.objects;

import java.util.Hashtable;

import com.klob.Bomberklob.resources.Point;

public class BotPlayer extends Player {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  int difficulty;
	private Point objectif;

	public BotPlayer(String imageName, Hashtable<String, AnimationSequence> animations, PlayerAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber, int immortal, int difficulty) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages, life, powerExplosion, timeExplosion, speed, shield, bombNumber, immortal);
		this.difficulty = difficulty;
		this.objectif = null;
	}

	public BotPlayer(BotPlayer botPlayer) {
		super(botPlayer);
		this.difficulty = botPlayer.difficulty;
	}
	
	public Point getObjectif() {
		return objectif;
	}

	public void setObjectif(Point objectif) {
		this.objectif = objectif;
	}
	
	public void setDifficulty(int difficulty) {
		if ( difficulty > 0 && difficulty < 4 ) {
			this.difficulty = difficulty;
		}
	}

	@Override
	public BotPlayer copy() {
		return new BotPlayer(this);
	}

}
