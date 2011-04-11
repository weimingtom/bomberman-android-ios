package com.klob.Bomberklob.engine;

import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.BotPlayer;
import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class Single extends Game {

	private int difficulty;

	public Single(String mapName, int enemies, String gametype, boolean random, int difficulty) {
		super(mapName, enemies, gametype, random);
		this.difficulty = difficulty;
	}
	
	@Override
	public void initGame() {/*
		this.players[0] = new HumanPlayer((Player) ResourcesManager.getPlayers().get(Model.getUser().getColor()));
		
		for (int i = 1 ; i < this.players.length ; i++ ) {
			this.players[i] = new BotPlayer[];
			(BotPlayer) this.players[i].setDifficulty(this.difficulty);
		}
		
		if ( random ) {
			for (int i = 0 ; i < this.players.length ; i++ ) {
				
			}
		}
		else {
			for (int i = 0 ; i < this.players.length ; i++ ) {
				this.players[i].setPosition(this.map.getPlayers()[i]);
			}
		}*/
	}

}
