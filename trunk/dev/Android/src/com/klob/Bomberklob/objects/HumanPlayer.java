package com.klob.Bomberklob.objects;

import java.util.Hashtable;

public class HumanPlayer extends Player {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HumanPlayer(String imageName, Hashtable<String, AnimationSequence> animations, PlayerAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber, int immortal) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages, life, powerExplosion, timeExplosion, speed, shield, bombNumber, immortal);
	}
	
	public HumanPlayer(Player Player) {
		super(Player);
	}

	@Override
	public HumanPlayer copy() {
		return new HumanPlayer(this);
	}

}
