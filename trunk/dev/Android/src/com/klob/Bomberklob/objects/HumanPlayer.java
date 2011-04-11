package com.klob.Bomberklob.objects;

import java.util.Hashtable;

public class HumanPlayer extends Player {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HumanPlayer(String imageName, Hashtable<String, AnimationSequence> animations, int lifeNumber, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber) {
		super(imageName, animations, lifeNumber, powerExplosion, timeExplosion, speed, shield, bombNumber);
	}
	
	public HumanPlayer(Player Player) {
		super(Player);
	}

	@Override
	public HumanPlayer copy() {
		return new HumanPlayer(this);
	}

}
