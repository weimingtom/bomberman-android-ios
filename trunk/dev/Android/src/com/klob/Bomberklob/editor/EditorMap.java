package com.klob.Bomberklob.editor;

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
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;
import com.klob.Bomberklob.objects.Objects;

/**
 *	Map of the map editor
 */
public class EditorMap extends Map {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Objects[][] grounds;
	private Objects[][] blocks;
	
	/* Constructeur -------------------------------------------------------- */

	/**
	 * Default constructor
	 */
	
	public EditorMap() {
		super();
		this.grounds = new Objects[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
		this.blocks  = new Objects[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	/**
	 * Returns Objects present at ground level
	 * @return Objects present at ground level
	 */
	public Objects[][] getGrounds() {
		return this.grounds;
	}

	/**
	 * Returns Objects present at the first level
	 * @return Objects present at the first level
	 */
	public Objects[][] getBlocks() {
		return this.blocks;
	}
	
	/* Setteurs ------------------------------------------------------------ */
	
	/**
	 * Updates the table of ground object
	 * @param grounds The new table of ground object
	 */
	public void setGround(Objects[][] grounds) {
		this.grounds = grounds;
	}

	/**
	 * Updates the table of block object
	 * @param blocks The new table of block object
	 */
	public void setBlocks(Objects[][] blocks) {
		this.blocks = blocks;
	}
	
	/* onDraws ------------------------------------------------------------- */

	/**
	 * Draw all objects of the map
	 * @param canvas A canvas
	 * @param size The desired size
	 */
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

	/**
	 * Draw only items on the ground
	 * @param canvas A canvas
	 * @param size The desired size
	 */
	public void groundsOnDraw(Canvas canvas, int size) {
		for (int i = 0; i < this.grounds.length ; i++) {
			for (int j = 0; j < this.grounds[0].length ; j++) {
				if ( this.grounds[i][j] != null ) {
					this.grounds[i][j].onDraw(canvas, size);
				}
			}
		}
	}

	/**
	 * Draw only items on the first level
	 * @param canvas A canvas
	 * @param size The desired size
	 */
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
	
	/**
	 * Save the current map
	 * @return Returns true if the map is saved false otherwise
	 */
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
	
	/**
	 * Load a map
	 * @param s Name of the map
	 * @return Returns true if the map is loaded false otherwise
	 */
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
	
	/**
	 * Resize the map according to screen resolution
	 */
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
	
	/**
	 * Add a ground object in the map
	 * @param o Ground object
	 * @param p Poistion of the ground object
	 */
	public void addGround(Objects o, Point p) {
		o.setPosition(new Point(p.x*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
		this.grounds[p.x][p.y] = o;
	}
	
	/**
	 * Add a block object in the map
	 * @param o Block object
	 * @param p Position of the block object
	 */
	public void addBlock(Objects o, Point p) {
		o.setPosition(new Point(p.x*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
		this.blocks[p.x][p.y] = o;
	}

	
	/**
	 * Add a new player in the current map
	 * @param p Position of the player
	 * @return The player's position in the tabular of player
	 */
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

	/**
	 * Delete a ground object in the map
	 * @param p Position in tile of the ground object
	 */
	public void deleteGround(Point p) {
		this.grounds[p.x][p.y] = null;
	}

	/**
	 * Delete a block object in the map
	 * @param p Position in tile of the block object
	 */
	public void deleteBlock(Point p) {
		this.blocks[p.x][p.y] = null;
	}

	/**
	 * Delete a player in the map
	 * @param p Position in pixel of the player
	 * @return integer representing the position of the player in the tabular otherwise -1
	 */
	public int deletePlayer(Point p) {
		for (int i = 0 ; i < this.players.length ; i++ ) {
			if (this.players[i] != null && this.players[i].x == p.x && this.players[i].y == p.y ) {
				this.players[i] = null;
				return i;
			}
		}
		return -1;
	}

	/**
	 * Destroy the block
	 * @param p Position in tile of the block
	 */
	public void destroyBlock(Point p) {
		this.blocks[p.x][p.y].destroy();
	}

}
