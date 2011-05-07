package com.klob.Bomberklob.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.klob.Bomberklob.resources.Map;
import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.ObjectsAnimations;

public class GameMap extends Map {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Bitmap bm;
	private ConcurrentHashMap<Point, ColisionMapObjects> colisionMap;

	private ConcurrentHashMap<Point, Objects> animatedObjects;
	
	private ConcurrentHashMap<Point, Objects> animatedObjectsBackUp;

	public GameMap() {
		super();
		this.colisionMap = new ConcurrentHashMap<Point, ColisionMapObjects>();
		this.animatedObjects = new ConcurrentHashMap<Point, Objects>();		
		this.animatedObjectsBackUp = new ConcurrentHashMap<Point, Objects>();
		loadMap(name);
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public ConcurrentHashMap<Point, Objects> getAnimatedObjects() {
		return animatedObjects;
	}
	
	public ConcurrentHashMap<Point, ColisionMapObjects> getColisionMap() {
		return colisionMap;
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
							this.colisionMap.put(new Point(i,j),ColisionMapObjects.GAPE);
						}
					}
					
					if ( map.getBlocks()[i][j] != null) {	
						this.colisionMap.put(new Point(i,j),ColisionMapObjects.BLOCK);
						if ( !map.getBlocks()[i][j].isDestructible()) {
							map.getBlocks()[i][j].onDraw(pictureCanvas, ResourcesManager.getSize());
						}
						else {
							this.animatedObjectsBackUp.put(new Point(i,j), map.getBlocks()[i][j].copy());
							this.animatedObjects.put(new Point(i,j), map.getBlocks()[i][j].copy());
						}
					}
					else {
						this.colisionMap.put(new Point(i,j),ColisionMapObjects.EMPTY);
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

		for(Entry<Point, Objects> entry : animatedObjects.entrySet()) {
			animatedObjects.get(entry.getKey()).onDraw(canvas, size);
		}
	}
	
	public void update() {
		/* Pour tous les objets animés */
		for(Entry<Point, Objects> entry : animatedObjects.entrySet()) {
			Objects o = animatedObjects.get(entry.getKey());
			/* Si sont animation est DESTROY et qu'elle est finie */
			if (o.getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) && o.hasAnimationFinished()) {
				this.colisionMap.put(ResourcesManager.coToTile(o.getPosition().x, o.getPosition().y), ColisionMapObjects.EMPTY);
				/* Et du vecteur d'objets animés */
				animatedObjects.remove(entry.getKey());
			}
			else {
				o.update();
			}
		}
	}
	
	public void restart() {		
		this.animatedObjects.clear();
		int i = 1;
		
		for(Entry<Point, Objects> entry : this.animatedObjectsBackUp.entrySet()) {
			System.out.println("CPT : " + i);
			this.animatedObjects.put(entry.getKey(), entry.getValue().copy());
			this.colisionMap.put(entry.getKey(), ColisionMapObjects.BLOCK);
			i++;
		}
	}
	
	public void colisionMapUpdate(Bomb bomb) {
		
		Point bombPosition = ResourcesManager.coToTile(bomb.getPosition().x, bomb.getPosition().y);
		boolean up, down, left, right;
		up = down = left = right = true;
		
		/* CENTER */
		this.colisionMap.put(bombPosition,ColisionMapObjects.BLOCK);
		
		for ( int k = 1 ; k < bomb.getPower() ; k++ ) {

			if ( up ) {
				if ( this.colisionMap.get(new Point(bombPosition.x, bombPosition.y-k)) != ColisionMapObjects.BLOCK ) {
					this.colisionMap.put(new Point(bombPosition.x, bombPosition.y-k),ColisionMapObjects.DANGEROUS_AREA);
				}
				else {
					up = false;
				}
			}

			if ( down ) {
				if ( this.colisionMap.get(new Point(bombPosition.x, bombPosition.y+k)) != ColisionMapObjects.BLOCK ) {
					this.colisionMap.put(new Point(bombPosition.x, bombPosition.y+k),ColisionMapObjects.DANGEROUS_AREA);
				}
				else {
					down = false;
				}
			}
			
			if ( left ) {
				if ( this.colisionMap.get(new Point(bombPosition.x-k, bombPosition.y)) != ColisionMapObjects.BLOCK ) {
					this.colisionMap.put(new Point(bombPosition.x-k, bombPosition.y),ColisionMapObjects.DANGEROUS_AREA);
				}
				else {
					left = false;
				}
			}
			
			if ( right ) {
				if ( this.colisionMap.get(new Point(bombPosition.x+k, bombPosition.y)) != ColisionMapObjects.BLOCK ) {
					this.colisionMap.put(new Point(bombPosition.x+k, bombPosition.y),ColisionMapObjects.DANGEROUS_AREA);
				}
				else {
					right = false;
				}
			}
		}
	}
}
