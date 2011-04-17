package com.klob.Bomberklob.engine;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Vector;

import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.BotPlayer;
import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class Single extends Game {

	private int difficulty;

	public Single(String mapName, int enemies, String gametype, boolean random, int difficulty) {
		super(mapName, enemies, gametype, random);
		this.difficulty = difficulty;
		initGame();
	}
	
	@Override
	public void initGame() {
		ArrayList<String> colors = new ArrayList<String>();
		
		for(Entry<String, Hashtable<String, AnimationSequence>> entry : ResourcesManager.getPlayersAnimations().entrySet()) {
			colors.add(entry.getKey());
		}
		
		if ( colors.size() >= this.players.length ) {		
			this.players[0] = new HumanPlayer(Model.getUser().getColor(), ResourcesManager.getPlayersAnimations().get(Model.getUser().getColor()), "idle",gameType.lifeNumber, gameType.powerExplosion, gameType.timeExplosion, gameType.speed, gameType.shield, gameType.bombNumber, gameType.damages);
			this.players[0].setPosition(new Point(this.map.getPlayers()[0].x*ResourcesManager.getSize(), this.map.getPlayers()[0].y*ResourcesManager.getSize()));
			colors.remove(this.players[0].getImageName());
			
			for (int i = 1 ; i < this.players.length ; i++ ) {
				int j = (int)(Math.random() * (colors.size()));
				this.players[i] = new BotPlayer(colors.get(j), ResourcesManager.getPlayersAnimations().get(colors.get(j)), "idle",gameType.lifeNumber, gameType.powerExplosion, gameType.timeExplosion, gameType.speed, gameType.shield, gameType.bombNumber, this.difficulty, gameType.damages);
				this.players[i].setPosition(new Point(this.map.getPlayers()[i].x*ResourcesManager.getSize(), this.map.getPlayers()[i].y*ResourcesManager.getSize()));
				colors.remove(j);
			}
		}
	}
	
	public void pauseGame() {
		for (int i = 0 ; i < this.players.length ; i++ ) {
			Vector<Bomb> bombs = this.players[i].getBombsPlanted();
			for (int j = 0; j < bombs.size() ; j++ ) {
				
			}
		}
	}
}
