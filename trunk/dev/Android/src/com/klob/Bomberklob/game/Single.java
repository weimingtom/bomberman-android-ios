package com.klob.Bomberklob.game;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.BotPlayer;
import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

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
		Point position;
		int size = ResourcesManager.getSize();
		
		for(Entry<String, Hashtable<String, AnimationSequence>> entry : ResourcesManager.getPlayersAnimations().entrySet()) {
			colors.add(entry.getKey());
		}
		
		if ( colors.size() >= this.players.length ) {
			this.players[0] = new HumanPlayer(Model.getUser().getColor(), ResourcesManager.getPlayersAnimations().get(Model.getUser().getColor()), PlayerAnimations.IDLE,gameType.hit, 1, gameType.fireWall, gameType.damages, gameType.life, gameType.powerExplosion, gameType.timeExplosion, gameType.speed, gameType.shield, gameType.bombNumber, gameType.immortal);
			position = this.map.getPlayers()[0];
			this.players[0].setPosition(new Point(position.x*size, position.y*size));
			colors.remove(this.players[0].getImageName());
			
			for (int i = 1 ; i < this.players.length ; i++ ) {
				int j = (int)(Math.random() * (colors.size()));
				this.players[i] = new BotPlayer(colors.get(j), ResourcesManager.getPlayersAnimations().get(colors.get(j)), PlayerAnimations.IDLE,gameType.hit, 1, gameType.fireWall, gameType.damages, gameType.life, gameType.powerExplosion, gameType.timeExplosion, gameType.speed, gameType.shield, gameType.bombNumber, gameType.immortal, this.difficulty);
				position = this.map.getPlayers()[i];
				this.players[i].setPosition(new Point(position.x*size, position.y*size));
				this.players[i].setObjectif(new Point(position.x*size, position.y*size));
				colors.remove(j);
			}
		}
	}
	
	public void pauseGame() {

	}
	
	public void restartGame() {

		this.map.restart();
		this.players = new Player[this.players.length];
		
		/* Nouvelle initialisation de la partie */
		initGame();
	}

	@Override
	public void pushBomb(Player player) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		this.map.update();
	}
}
