package com.klob.Bomberklob.objects;

import java.util.Hashtable;

public class HumanPlayer extends Player {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HumanPlayer(String imageName, Hashtable<String, AnimationSequence> animations, String currentAnimation, int lifeNumber, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber, int damages) {
		super(imageName, animations, currentAnimation, lifeNumber, powerExplosion, timeExplosion, speed, shield, bombNumber, damages);
	}
	
	public HumanPlayer(Player Player) {
		super(Player);
	}

	@Override
	public HumanPlayer copy() {
		return new HumanPlayer(this);
	}

}
