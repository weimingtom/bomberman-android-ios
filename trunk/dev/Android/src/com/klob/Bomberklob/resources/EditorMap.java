package com.klob.Bomberklob.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.graphics.Canvas;
import android.util.Log;

import com.klob.Bomberklob.resources.Map;
import com.klob.Bomberklob.objects.Objects;

public class EditorMap extends Map {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Objects[][] grounds;
	private Objects[][] blocks;
	
	/* Constructeur -------------------------------------------------------- */

	public EditorMap() {
		super();
		this.grounds = new Objects[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
		this.blocks  = new Objects[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public Objects[][] getGrounds() {
		return this.grounds;
	}

	public Objects[][] getBlocks() {
		return this.blocks;
	}
	
	/* Setteurs ------------------------------------------------------------ */
	
	public void setGround(Objects[][] ground) {
		this.grounds = ground;
	}

	public void setBlocks(Objects[][] blocks) {
		this.blocks = blocks;
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
			this.grounds = map.getGrounds();
			this.blocks = map.getBlocks();
			this.players = map.getPlayers();
			this.name = map.getName();			


			f =  null;
			map = null;

			return true;
		}
		return false;
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

}
