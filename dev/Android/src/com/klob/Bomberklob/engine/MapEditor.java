package com.klob.Bomberklob.engine;

import android.content.Context;

import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class MapEditor {
	
	private Map map;
	
	/* Constructeur -------------------------------------------------------- */
	
	public MapEditor () {
		this.map = new Map();
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
			
			if ( map.getBlocks()[point.x][point.y] != null && objects.getImageName().equals(map.getBlocks()[point.x][point.y].getImageName())) {
				map.getBlocks()[point.x][point.y] = null;
			}
			else {
				this.map.addBlock(objects, point);
			}
		}	
	}	
	
	public void loadMap(Context context, String mapName) {
		
		if ( !map.loadMap(context, mapName) ) {
			
			map.setName(mapName);
			for (int j = 0 ; j < 15 ; j ++) {
				for (int k = 0 ; k < 13 ; k++ ) {
					Objects o = ResourcesManager.getObject("grass");
					map.addGround(o, new Point(j,k));
				}
			}
		}
	}
	
}