package com.klob.Bomberklob.game;

public class FreeForAll extends GameType {
	
	public FreeForAll() {
		hit = false;
		life = 2;
		fireWall = false;
		powerExplosion = 5;
		timeExplosion = 30000;
		shield = 0;
		speed = 1;
		bombNumber = 20;
		damages = 0;
		immortal = 0;
	}
}