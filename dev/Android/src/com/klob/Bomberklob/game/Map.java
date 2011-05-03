package com.klob.Bomberklob.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.ObjectsAnimations;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class Map implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private Point[] players;
	private Objects[][] grounds;
	private Objects[][] blocks;

	private transient Bitmap bm;
	private transient ConcurrentHashMap<Point, Objects> animatedObjects;
	private transient ConcurrentHashMap<Point, Integer> zoneDangereuses;

	public Map() {
		this.players = new Point[4];
		this.grounds = new Objects[21][15];
		this.blocks  = new Objects[21][15];
		this.animatedObjects = new ConcurrentHashMap<Point, Objects>();
		this.zoneDangereuses = new ConcurrentHashMap<Point, Integer>();
	}

	/* Getteurs ------------------------------------------------------------ */

	public ConcurrentHashMap<Point, Objects> getAnimatedObjects() {
		return animatedObjects;
	}

	public String getName() {
		return this.name;
	}

	public Objects[][] getGrounds() {
		return this.grounds;
	}

	public Objects[][] getBlocks() {
		return this.blocks;
	}

	public Point[] getPlayers() {
		return players;
	}

	public ConcurrentHashMap<Point, Integer> getZoneDangereuses() {
		return zoneDangereuses;
	}

	/* Setteurs ------------------------------------------------------------ */

	public void setName(String name) {
		this.name = name;
	}

	public void setGround(Objects[][] ground) {
		this.grounds = ground;
	}

	public void setBlocks(Objects[][] blocks) {
		this.blocks = blocks;
	}

	public void setPlayers(Point[] players) {
		this.players = players;
	}

	/* Méthodes publiques -------------------------------------------------- */

	public boolean saveMap() {

		for (int i = 0 ; i < this.players.length ; i ++ ) {
			if ( this.players[i] == null ) {
				return false;
			}
		}		

		File f =  new File (ResourcesManager.getContext().getFilesDir().getAbsolutePath()+"/maps");
		f.mkdir();
		f =  new File (f.getAbsolutePath()+"/"+this.name);
		f.mkdir();
		f = new File (f.getAbsolutePath()+"/"+this.name+".klob");

		try {
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos= new ObjectOutputStream(fos);

			try {
				oos.writeObject(this); 
				oos.flush();
			} finally {
				try {
					oos.close();
				} finally {
					fos.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return true;
	}

	public boolean loadMap(String s) {

		Map map = null;	
		File f =  new File (ResourcesManager.getContext().getFilesDir().getAbsolutePath()+"/maps/"+s+"/"+s+".klob");
		Log.i("Map", "File loaded : " + f.getAbsolutePath());	

		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);

			try {
				long l = System.currentTimeMillis();
				Log.i("MAP", "Start loading");
				map  = (Map) ois.readObject(); 
				Log.i("MAP", "End loading at : " + (System.currentTimeMillis()-l));
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
			this.grounds = map.getGrounds();
			this.blocks = map.getBlocks();
			this.players = map.getPlayers();
			this.name = map.getName();			

			restart();

			f =  null;
			map = null;

			return true;
		}
		return false;
	}

	public void addGround(Objects o, Point p) {
		o.setPosition(new Point(p.x*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
		this.grounds[p.x][p.y] = o;
	}

	public void addBlock(Objects o, Point p) {
		o.setPosition(new Point(p.x*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
		this.blocks[p.x][p.y] = o;
	}

	public int addPlayer(Point p) {
		if ( this.blocks[p.x][p.y] != null ) {
			this.blocks[p.x][p.y] = null;
		}
		for (int i = 0 ; i < this.players.length ; i++) {
			if ( this.players[i] == null ) {
				this.players[i] = p;
				return i;
			}
		}
		return -1;
	}

	public void deleteGround(Point p) {
		this.grounds[p.x][p.y] = null;
	}

	public void deleteBlock(Point p) {
		this.blocks[p.x][p.y] = null;
	}

	public int deletePlayer(Point p) {
		for (int i = 0 ; i < this.players.length ; i++ ) {
			if (this.players[i] != null && this.players[i].x == p.x && this.players[i].y == p.y ) {
				this.players[i] = null;
				return i;
			}
		}
		return -1;
	}

	public void destroyBlock(Point p) {
		this.blocks[p.x][p.y].destroy();
	}

	public void update() {

		/* Pour tous les objets animés */
		for(Entry<Point, Objects> entry : animatedObjects.entrySet()) {
			Objects o = animatedObjects.get(entry.getKey());
			/* Si sont animation est DESTROY et qu'elle est finie */
			if (o.getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) && o.hasAnimationFinished()) {
				/* Si l'objet était dangereux on l'enlève de la hashmap de zones dangereuses */
				if ( o.getDamage() != 0 ) {
					zoneDangereuses.put(entry.getKey(),0);
				}
				/* Et du vecteur d'objets animés */
				animatedObjects.remove(entry.getKey());
			}
			else {
				o.update();
			}
		}
	}

	/* onDraws ------------------------------------------------------------- */

	public void editorOnDraw(Canvas canvas, int size) {
		for (int i = 0; i < this.grounds.length ; i++) {
			for (int j = 0; j < this.grounds[0].length ; j++) {
				if ( this.grounds[i][j] != null ) {
					this.grounds[i][j].onDraw(canvas, size);
				}

				if ( this.blocks[i][j] != null ) {
					this.blocks[i][j].onDraw(canvas, size);
				}
			}
		}
	}

	public void gameOnDraw(Canvas canvas, int size) {
		canvas.drawBitmap(bm, 0, 0, null);

		for(Entry<Point, Objects> entry : animatedObjects.entrySet()) {
			animatedObjects.get(entry.getKey()).onDraw(canvas, size);
		}
	}

	public void groundsOnDraw(Canvas canvas, int size) {
		for (int i = 0; i < this.grounds.length ; i++) {
			for (int j = 0; j < this.grounds[0].length ; j++) {
				if ( this.grounds[i][j] != null ) {
					this.grounds[i][j].onDraw(canvas, size);
				}
			}
		}
	}

	public void blocksOnDraw(Canvas canvas, int size) {
		for (int i = 0; i < this.blocks.length ; i++) {
			for (int j = 0; j < this.blocks[0].length ; j++) {
				if ( this.blocks[i][j] != null ) {
					this.blocks[i][j].onDraw(canvas, size);
				}
			}
		}
	}

	public void resize() {
		for (int i = 0; i < this.grounds.length ; i++) {
			for (int j = 0; j < this.grounds[0].length ; j++) {
				if ( this.grounds[i][j] != null ) {
					this.grounds[i][j].setPosition(new Point(i*ResourcesManager.getSize(), j*ResourcesManager.getSize()));
				}

				if ( this.blocks[i][j] != null ) {
					this.blocks[i][j].setPosition(new Point(i*ResourcesManager.getSize(), j*ResourcesManager.getSize()));
				}
			}
		}
		Log.i("Map", "--------- Map resized --------");
	}

	public void restart() {
		this.bm = Bitmap.createBitmap(ResourcesManager.getSize()*this.grounds.length, ResourcesManager.getSize()*this.grounds[0].length, Bitmap.Config.ARGB_8888);

		this.animatedObjects.clear();
		
		Canvas pictureCanvas = new Canvas(this.bm);

		//FIXME prendre le cas où on aurait de l'eau ou un sol animé !
		this.groundsOnDraw(pictureCanvas, ResourcesManager.getSize());

		for (int i = 0; i < this.grounds.length ; i++) {
			for (int j = 0; j < this.grounds[0].length ; j++) {
				if ( this.blocks[i][j] != null) {
					if ( !this.blocks[i][j].isDestructible()) {
						this.blocks[i][j].onDraw(pictureCanvas, ResourcesManager.getSize());
					}
					else {
						this.animatedObjects.put(new Point(i,j), this.blocks[i][j].copy());
					}
				}
			}
		}
	}
}
