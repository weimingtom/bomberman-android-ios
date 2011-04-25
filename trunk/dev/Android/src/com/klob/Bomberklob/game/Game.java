package com.klob.Bomberklob.game;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.klob.Bomberklob.objects.Destructible;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.ObjectsAnimations;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public abstract class Game {

	protected Map map;
	protected Player[] players;
	protected GameType gameType;

	protected boolean random;

	Bitmap bm;
	ArrayList<Objects> blocks;

	/* Constructeurs  ------------------------------------------------------ */

	public Game(String mapName, int enemies, String gametype, boolean random) {
		this.map = new Map();
		if ( this.map.loadMap(mapName) ) {
			this.players = new Player[enemies+1];
			this.random = random;
			this.blocks = new ArrayList<Objects>();

			bm = Bitmap.createBitmap(ResourcesManager.getSize()*this.map.getGrounds().length, ResourcesManager.getSize()*this.map.getGrounds()[0].length, Bitmap.Config.ARGB_8888);

			Canvas pictureCanvas = new Canvas(bm);

			//FIXME prendre le cas où on aurait de l'eau ou un sol animé !
			this.map.groundsOnDraw(pictureCanvas, ResourcesManager.getSize());

			for (int i = 0; i < this.map.getGrounds().length ; i++) {
				for (int j = 0; j < this.map.getGrounds()[0].length ; j++) {
					if ( this.map.getBlocks()[i][j] != null) {
						if ( !this.map.getBlocks()[i][j].isDestructible()) {
							this.map.getBlocks()[i][j].onDraw(pictureCanvas, ResourcesManager.getSize());
						}
						else {
							blocks.add(this.map.getBlocks()[i][j]);
						}
					}
				}
			}
		}

		if ( gametype.equals("FreeForAll") ) {
			this.gameType = new FreeForAll();
		}
		else {

		}			
	}


	/* Getters ------------------------------------------------------------- */

	public Map getMap() {
		return map;
	}

	public Player[] getPlayers() {
		return players;
	}

	public GameType getGameType() {
		return gameType;
	}

	public boolean isRandom() {
		return random;
	}
	
	public ArrayList<Objects> getBlocks() {
		return blocks;
	}

	/* Méthodes publiques -------------------------------------------------- */

	public abstract void initGame();

	public abstract void pushBomb(Player player);

	public void onDraw(Canvas canvas, int size) {
		canvas.drawBitmap(bm, 0, 0, null);

		for (int i = 0 ; i < blocks.size() ; i++ ) {
			blocks.get(i).onDraw(canvas, size);
		}

		for (int i = 0 ; i < this.players.length ; i++ ) {
			if ( this.players[i].getPosition() != null ) {
				this.players[i].onDraw(canvas, size);
			}
		}
	}

	public void update() {
		
		for (int i = 0 ; i < blocks.size() ; i++ ) {
			if (blocks.get(i).getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) && blocks.get(i).hasAnimationFinished()) {
				this.map.deleteBlock(new Point(blocks.get(i).getPosition().x/ResourcesManager.getSize(),blocks.get(i).getPosition().y/ResourcesManager.getSize()));
				blocks.remove(i);
			}
			else {
				blocks.get(i).update();
			}
		}
	}
}
