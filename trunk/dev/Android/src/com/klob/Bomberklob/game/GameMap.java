package com.klob.Bomberklob.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.klob.Bomberklob.resources.Map;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;
import com.klob.Bomberklob.editor.EditorMap;
import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.ObjectsAnimations;

public class GameMap extends Map {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Bitmap bm;
	private ColisionMapObjects[][] colisionMap;

	private ConcurrentHashMap<Point, Objects> animatedObjects;      
	private ConcurrentHashMap<Point, Objects> animatedObjectsBackUp;
	private Point objectPosition;
	
	private Iterator<Objects> objectsIterator;

	public GameMap() {
		super();
		this.colisionMap = new ColisionMapObjects[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
		this.animatedObjects = new ConcurrentHashMap<Point, Objects>();         
		this.animatedObjectsBackUp = new ConcurrentHashMap<Point, Objects>();
		this.objectPosition = new Point();
	}

	/* Getteurs ------------------------------------------------------------ */

	public ConcurrentHashMap<Point, Objects> getAnimatedObjects() {
		return animatedObjects;
	}

	public ColisionMapObjects[][] getColisionMap() {
		return colisionMap;
	}

	/* Setteurs ------------------------------------------------------------ */

	public void setColisionMap(ColisionMapObjects[][] colisionMap) {
		this.colisionMap = colisionMap;
	}

	/* Méthodes publiques -------------------------------------------------- */

	public boolean loadMap(String s) {

		EditorMap map = null;   
		File f =  new File (ResourcesManager.getContext().getFilesDir().getAbsolutePath()+"/maps/"+s+"/"+s+".klob");
		Log.i("Map", "File loaded : " + f.getAbsolutePath());   

		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);

			try {
				map  = (EditorMap) ois.readObject(); 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					ois.close();
				} finally {
					fis.close();
				}
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}               

		if ( map != null ) {

			this.players = map.getPlayers();
			this.name = map.getName();
			this.width = map.getGrounds().length;
			this.height = map.getGrounds()[0].length;

			this.bm = Bitmap.createBitmap(ResourcesManager.getSize()*this.width, ResourcesManager.getSize()*this.height, Bitmap.Config.ARGB_8888);


			Canvas pictureCanvas = new Canvas(this.bm);

			for (int i = 0; i < map.getGrounds().length ; i++) {
				for (int j = 0; j < map.getGrounds()[0].length ; j++) {

					if ( map.getGrounds()[i][j] != null) {
						map.getGrounds()[i][j].onDraw(pictureCanvas, ResourcesManager.getSize());
						if ( map.getGrounds()[i][j].isHit() ) {
							this.colisionMap[i][j] = ColisionMapObjects.GAPE;
						}
					}

					if ( map.getBlocks()[i][j] != null) {   
						this.colisionMap[i][j] = ColisionMapObjects.BLOCK;
						if ( !map.getBlocks()[i][j].isDestructible()) {
							map.getBlocks()[i][j].onDraw(pictureCanvas, ResourcesManager.getSize());
						}
						else {
							this.animatedObjectsBackUp.put(new Point(i,j), map.getBlocks()[i][j].copy());
							this.animatedObjects.put(new Point(i,j), map.getBlocks()[i][j].copy());
						}
					}
					else {
						this.colisionMap[i][j] = ColisionMapObjects.EMPTY;
					}
				}
			}                       
			return true;
		}
		return false;
	}

	/* Méthodes publiques -------------------------------------------------- */

	public void onDraw(Canvas canvas, int size) {

		canvas.drawBitmap(bm, 0, 0, null);
		
		for (this.objectsIterator = animatedObjects.values().iterator() ; this.objectsIterator.hasNext() ;){
			this.objectsIterator.next().onDraw(canvas, size); 
		}
	}

	public void update() {
		for(Point entry : animatedObjects.keySet()) {
			Objects o = animatedObjects.get(entry);
			if (o.getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) && o.hasAnimationFinished()) {
				this.objectPosition = ResourcesManager.coToTile(o.getPosition().x, o.getPosition().y);
				this.colisionMap[this.objectPosition.x][this.objectPosition.y] = ColisionMapObjects.EMPTY;
				this.animatedObjects.remove(entry);
			}
			else {
				o.update();
			}
		}
	}

	public void restart() {         
		this.animatedObjects.clear();

		for (int i = 0 ; i < ResourcesManager.MAP_WIDTH ; i++ ) {
			for (int j = 0 ; j < ResourcesManager.MAP_HEIGHT ; j++ ) {
				if ( this.colisionMap[i][j] == ColisionMapObjects.DANGEROUS_AREA || this.colisionMap[i][j] == ColisionMapObjects.BOMB ) {
					this.colisionMap[i][j] = ColisionMapObjects.EMPTY;
				}
			}
		}

		for(Entry<Point, Objects> entry : this.animatedObjectsBackUp.entrySet()) {
			this.animatedObjects.put(entry.getKey(), entry.getValue().copy());
			this.colisionMap[entry.getKey().x][entry.getKey().y] = ColisionMapObjects.BLOCK;
		}
	}

	public void colisionMapUpdate(Bomb bomb) {

		Point bombPosition = ResourcesManager.coToTile(bomb.getPosition().x, bomb.getPosition().y);
		boolean up, down, left, right;
		up = down = left = right = true;

		/* CENTER */
		this.colisionMap[bombPosition.x][bombPosition.y] = ColisionMapObjects.BOMB;

		for ( int k = 1 ; k < bomb.getPower() ; k++ ) {

			if ( up ) {
				if ( colisionMap[bombPosition.x][bombPosition.y-k] != ColisionMapObjects.BLOCK && colisionMap[bombPosition.x][bombPosition.y-k] != ColisionMapObjects.BOMB && colisionMap[bombPosition.x][bombPosition.y-k] != ColisionMapObjects.DAMAGE && colisionMap[bombPosition.x][bombPosition.y-k] != ColisionMapObjects.FIRE) {
					colisionMap[bombPosition.x][bombPosition.y-k] = ColisionMapObjects.DANGEROUS_AREA;
				}
				else {
					up = false;
				}
			}

			if ( down ) {
				if ( colisionMap[bombPosition.x][bombPosition.y+k] != ColisionMapObjects.BLOCK && colisionMap[bombPosition.x][bombPosition.y+k] != ColisionMapObjects.BOMB && colisionMap[bombPosition.x][bombPosition.y+k] != ColisionMapObjects.DAMAGE && colisionMap[bombPosition.x][bombPosition.y+k] != ColisionMapObjects.FIRE) {
					colisionMap[bombPosition.x][bombPosition.y+k] = ColisionMapObjects.DANGEROUS_AREA;
				}
				else {
					down = false;
				}
			}

			if ( left ) {
				if ( colisionMap[bombPosition.x-k][bombPosition.y] != ColisionMapObjects.BLOCK && colisionMap[bombPosition.x-k][bombPosition.y] != ColisionMapObjects.BOMB && colisionMap[bombPosition.x-k][bombPosition.y] != ColisionMapObjects.DAMAGE && colisionMap[bombPosition.x-k][bombPosition.y] != ColisionMapObjects.FIRE) {
					colisionMap[bombPosition.x-k][bombPosition.y] = ColisionMapObjects.DANGEROUS_AREA;
				}
				else {
					left = false;
				}
			}

			if ( right ) {
				if ( colisionMap[bombPosition.x+k][bombPosition.y] != ColisionMapObjects.BLOCK && colisionMap[bombPosition.x+k][bombPosition.y] != ColisionMapObjects.BOMB && colisionMap[bombPosition.x+k][bombPosition.y] != ColisionMapObjects.DAMAGE && colisionMap[bombPosition.x+k][bombPosition.y] != ColisionMapObjects.FIRE) {
					colisionMap[bombPosition.x+k][bombPosition.y] = ColisionMapObjects.DANGEROUS_AREA;
				}
				else {
					right = false;
				}
			}
		}
	}
}
