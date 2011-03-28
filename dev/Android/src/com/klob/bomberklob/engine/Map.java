package com.klob.bomberklob.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;


public class Map {
	
	private String name;
	private Objects[][] grounds;
	private Objects[][] blocks;
	
	public Map() {
		// TODO Que faire ?
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public String getName() {
		return this.name;
	}

	public Objects[][] getGrounds() {
		return this.grounds;
	}

	public Objects[][] getBlocks() {
		return this.blocks;
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
	
	/* Méthodes publiques -------------------------------------------------- */

	// FIXME Context ?
	public boolean saveMap(Context context) {
		
		// On crée le répertoire quoi qu'il arrive
		File dir = context.getDir("maps", Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
		
		// On dit où l'on voudra créer notre fichier
		File f = new File (dir.getAbsolutePath()+"/"+this.name+".klob");
		
		try {
			
			// on crée le nouveau fichier;
			f.createNewFile();
			
			// ouverture d'un flux de sortie vers le fichier "map.getName()+".klob""
			FileOutputStream fos = new FileOutputStream(f);

			// création d'un "flux objet" avec le flux fichier
			ObjectOutputStream oos= new ObjectOutputStream(fos);

			try {
				// sérialisation : écriture de l'objet dans le flux de sortie
				oos.writeObject(this); 
				// on vide le tampon
				oos.flush();
			} finally {
				//fermeture des flux
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
	
	// FIXME Context ?
	public boolean loadMap(Context context, String s) throws FileNotFoundException, IOException {
		
		File dir = context.getDir("maps", Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE); // FIXME Autre façon de faire ?
		File f = new File (dir.getAbsolutePath()+"/"+s+".klob");
		Map map = null;
		
		try {
			// ouverture d'un flux d'entrée depuis le fichier "dir.getAbsolutePath()+"/"+s+".klob""
			FileInputStream fis = new FileInputStream(f);
			// création d'un "flux objet" avec le flux fichier
			ObjectInputStream ois= new ObjectInputStream(fis);
			try {	
				// désérialisation : lecture de l'objet depuis le flux d'entrée
				map  = (Map) ois.readObject(); 
			} finally {
				// on ferme les flux
				try {
					ois.close();
				} finally {
					fis.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		if(map != null) {
			// FIXME Moche ?
			this.grounds = map.getGrounds();
			this.blocks = map.getBlocks();
			this.name = map.getName();
			return true;
		}
		return false;
	}
	
	public void addGround(Objects o, Point p) {
		this.grounds[p.x][p.y] = o;
	}
	
	public void addBlock(Objects o, Point p) {
		this.blocks[p.x][p.y] = o;
	}
	
	public void deleteGround(Point p) {
		this.grounds[p.x][p.y] = null;
	}
	
	public void deleteBlock(Point p) {
		this.blocks[p.x][p.y] = null;
	}
	
	public void destroyBlock(Point p) {
		/* TODO lancer l'animation de destruction
		if (this.blocks[p.x][p.y] instanceof Animated) {
			this.blocks[p.x][p.y].setCurrentAnimation("destroy");
		}
		*/
	}
	
	public void onDraw(Canvas canvas) {
		for (int i = 0; i < this.grounds.length ; i++) {
			for (int j = 0; j < this.grounds[0].length ; j++) {
				if ( this.grounds[i][j] != null ) {
					this.grounds[i][j].onDraw(canvas);
				}
				// if (this.blocks[i][j] != null && this.blocks[i][j] instanceof Animated && this.blocks[i][j].hasAnimationFinished() && this.blocks[i][j].getCurrentAnimation().equals("destroy") ) {
				//		this.deleteBlock(new Point(i,j));
				// }
				if ( this.blocks[i][j] != null ) {
					this.blocks[i][j].onDraw(canvas);
				}
			}
		}
	}
}
