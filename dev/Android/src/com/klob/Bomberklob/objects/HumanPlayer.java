package com.klob.Bomberklob.objects;

import java.util.Hashtable;

public class HumanPlayer extends Player {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a player using parameters entered
	 * 
	 * @param imageName The name of the image representing the player
	 * @param animations The HashMap animation player
	 * @param currentAnimation The current animation player
	 * @param hit If the player is traversable
	 * @param level The level which is the player
	 * @param fireWall If the player blocks fire
	 * @param damages The number of damage that makes the player
	 * @param life Life player
	 * @param powerExplosion The explosive power of its bombs
	 * @param timeExplosion The time of explosion of his bombs
	 * @param speed The speed of the player
	 * @param shield The number of player's shield
	 * @param bombNumber The number of bombs
	 * @param immortal Time of immortality
	 */
	
	public HumanPlayer(String imageName, Hashtable<String, AnimationSequence> animations, PlayerAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber, int immortal) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages, life, powerExplosion, timeExplosion, speed, shield, bombNumber, immortal);
	}

	/**
	 * Creates a copy of the player in parameter
	 * 
	 * @param botPlayer The player to copy
	 */
	public HumanPlayer(Player Player) {
		super(Player);
	}

	@Override
	public HumanPlayer copy() {
		return new HumanPlayer(this);
	}
}
