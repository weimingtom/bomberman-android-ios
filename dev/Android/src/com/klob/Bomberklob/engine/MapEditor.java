package com.klob.Bomberklob.engine;

import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Canvas;

import com.klob.Bomberklob.objects.HashMapObjects;
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
			if ( objects instanceof Player ) {
				int i = 0;
				if ( (i = this.map.deletePlayer(point)) == -1 ) {
					this.map.addPlayer(point);
					for (i = 0 ; i < 4 ; i++ ) {
						if ( objects.getImageName().equals(this.players[i].getImageName()) ) {
							this.players[i].setPosition(new Point(point.x*ResourcesManager.getSize() , point.y*ResourcesManager.getSize()));
							map.getBlocks()[point.x][point.y] = null;
							break;
						}
					}
				}
				else {
					for (i = 0 ; i < 4 ; i++ ) {
						if ( players[i].getPosition() != null ) {
							if ( (point.x*ResourcesManager.getSize()) == players[i].getPosition().x && (point.y*ResourcesManager.getSize()) == players[i].getPosition().y) {
								players[i].setPosition(null);
								break;
							}
						}
					}
				}
			}
			else {
				if ( map.getBlocks()[point.x][point.y] != null && objects.getImageName().equals(map.getBlocks()[point.x][point.y].getImageName())) {
					map.getBlocks()[point.x][point.y] = null;
				}
				else {
					this.map.addBlock(objects, point);
					for (int i = 0 ; i < 4 ; i++ ) {
						if ( players[i].getPosition() != null ) {
							if ( (point.x*ResourcesManager.getSize()) == players[i].getPosition().x && (point.y*ResourcesManager.getSize()) == players[i].getPosition().y) {
								players[i].setPosition(null);
								break;
							}
						}
					}
				}
			}
		}
	}	

	public void loadMap(Context context, String mapName) {

		if ( !map.loadMap(context, mapName) ) {
			map = new Map();
			map.setName(mapName);

			HashMapObjects hmo = ResourcesManager.getObjects();

			for (int j = 0 ; j < map.getGrounds().length ; j++) {
				map.addBlock(hmo.get("bloc"), new Point(j,0));
				map.addBlock(hmo.get("bloc"), new Point(j,14));
			}
			for (int j = 1 ; j < map.getGrounds()[0].length-1 ; j++) {
				map.addBlock(hmo.get("bloc"), new Point(0,j));
				map.addBlock(hmo.get("bloc"), new Point(16,j));
			}
			for (int j = 1 ; j < map.getGrounds().length-1 ; j++) {
				for (int k = 1 ; k < map.getGrounds()[0].length-1 ; k++ ) {
					map.addGround(hmo.get("grass"), new Point(j,k));
				}
			}
		}

		int i = 0;
		for(Entry<String, Objects> entry : ResourcesManager.getPlayers().entrySet()) {
			if ( i < 4 ) {
				Objects valeur = ResourcesManager.getPlayers().get(entry.getKey());
				if ( this.map.getPlayers()[i] != null ) {
					valeur.setPosition(new Point(this.map.getPlayers()[i].x*ResourcesManager.getSize() , this.map.getPlayers()[i].y*ResourcesManager.getSize()));
				}
				else {
					valeur.setPosition(null);	
				}
				this.players[i] = valeur;
			}
			i++;
		}
	}

	public void onDraw(Canvas canvas, boolean level) {
		if ( !level ) {
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