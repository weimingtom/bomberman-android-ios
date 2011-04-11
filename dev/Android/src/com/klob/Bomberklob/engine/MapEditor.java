package com.klob.Bomberklob.engine;

import android.graphics.Canvas;

import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class MapEditor {

	private Map map;
	private Objects[] players;

	/* Constructeur -------------------------------------------------------- */

	public MapEditor () {
		this.map = new Map();
		this.players = new Player[4];
	}

	/* Getteurs ------------------------------------------------------------ */

	public Map getMap() {
		return map;
	}

	/* Setteurs ------------------------------------------------------------ */

	public void setMap(Map map) {
		this.map = map;
	}

	/* Méthodes publiques -------------------------------------------------- */

	public void addObject (Objects objects, Point point) {
		if ( objects.getLevel() == 0 ) {
			if ( map.getBlocks()[point.x][point.y] != null && map.getBlocks()[point.x][point.y].isDestructible() ) {
				this.map.addGround(objects, point);
			}
			else {
				map.getBlocks()[point.x][point.y] = null;
				this.map.addGround(objects, point);
			}
		}
		else {
			int j = 0,i = 0;
				
			for (i = 0 ; i < 4 ; i++) {
				if ( this.players[i].getImageName().equals(objects.getImageName())) {
					break;
				}
			}				
				
			if ( i < 4 ) {
				if ( (j = this.map.deletePlayer(point)) != -1 ) {
					this.players[j].setPosition(null);
				}
				else {
					map.getBlocks()[point.x][point.y] = null;
				}
				
				if ( j != i ) {
					this.players[i].setPosition(new Point(point.x*ResourcesManager.getSize() , point.y*ResourcesManager.getSize()));
					this.map.getPlayers()[i] = point;
				}
			}
			else {
				if ( map.getBlocks()[point.x][point.y] != null && objects.getImageName().equals(map.getBlocks()[point.x][point.y].getImageName())) {
					map.getBlocks()[point.x][point.y] = null;
				}
				else {
					if ( (j = this.map.deletePlayer(point)) != -1 ) {
						this.players[j].setPosition(null);
					}
					this.map.addBlock(objects, point);
				}
			}
		}
	}	

	public void loadMap(String mapName) {

		if ( !map.loadMap(mapName) ) {
			map = new Map();
			map.setName(mapName);

			Objects o = ResourcesManager.getObjects().get("bloc").copy();

			for (int j = 0 ; j < map.getGrounds()[0].length ; j++) {
				map.addBlock(o.copy(), new Point(0,j));
				map.addBlock(o.copy(), new Point(16,j));
			}
			for (int j = 1 ; j < map.getGrounds().length-1 ; j++) {
				map.addBlock(o.copy(), new Point(j,0));
				map.addBlock(o.copy(), new Point(j,14));
			}
			
			o = ResourcesManager.getObjects().get("grass").copy();

			for (int j = 1 ; j < map.getGrounds().length-1 ; j++) {
				for (int k = 1 ; k < map.getGrounds()[0].length-1 ; k++ ) {
					map.addGround(o.copy(), new Point(j,k));
				}
			}
		}
		
		this.players[0] = new HumanPlayer("white", ResourcesManager.getPlayersAnimations().get("white"), 1, 1, 1, 1, 1, 1);
		this.players[1] = new HumanPlayer("blue", ResourcesManager.getPlayersAnimations().get("blue"), 1, 1, 1, 1, 1, 1);
		this.players[2] = new HumanPlayer("black", ResourcesManager.getPlayersAnimations().get("black"), 1, 1, 1, 1, 1, 1);
		this.players[3] = new HumanPlayer("red", ResourcesManager.getPlayersAnimations().get("red"), 1, 1, 1, 1, 1, 1);

		for (int i = 0 ; i < 4 ; i++ ) {
			if ( this.map.getPlayers()[i] != null ) {
				this.players[i].setPosition(new Point(this.map.getPlayers()[i].x*ResourcesManager.getSize() , this.map.getPlayers()[i].y*ResourcesManager.getSize()));
			}
		}
	}

	public void onDraw(Canvas canvas, boolean level) {
		if ( !level ) {
			for (int j = 0 ; j < map.getGrounds()[0].length ; j++) {
				map.getBlocks()[0][j].onDraw(canvas,  ResourcesManager.getSize());
				map.getBlocks()[16][j].onDraw(canvas,  ResourcesManager.getSize());				
			}
			for (int j = 1 ; j < map.getGrounds().length-1 ; j++) {
				map.getBlocks()[j][0].onDraw(canvas,  ResourcesManager.getSize());
				map.getBlocks()[j][14].onDraw(canvas,  ResourcesManager.getSize());
			}
			
			this.map.groundsOnDraw(canvas, ResourcesManager.getSize());
		}
		else {
			this.map.onDraw(canvas, ResourcesManager.getSize());
			for (int i = 0 ; i < 4 ; i++) {
				if ( this.players[i].getPosition() != null ) {
					this.players[i].onDraw(canvas, ResourcesManager.getSize());
				}
			}
		}
	}
}