package com.klob.Bomberklob.objects;

import java.util.Hashtable;

public class BotPlayer extends Player {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  int difficulty;

	public BotPlayer(String imageName, Hashtable<String, AnimationSequence> animations, String currentAnimation, int lifeNumber, int powerExplosion,	int timeExplosion, int speed, int shield, int bombNumber, int difficulty) {
		super(imageName, animations, currentAnimation, lifeNumber, powerExplosion, timeExplosion, speed, shield, bombNumber);
		this.difficulty = difficulty;
	}

	public BotPlayer(BotPlayer botPlayer) {
		super(botPlayer);
		this.difficulty = botPlayer.difficulty;
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
