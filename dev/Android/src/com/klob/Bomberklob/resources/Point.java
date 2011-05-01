package com.klob.Bomberklob.resources;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Point extends android.graphics.Point implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public Point() {
    	super();
    }
    
    public Point(int x, int y) {
    	super(x, y);
    }
    
    public Point(Point src) {
    	super(src);
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
		// appel des mécanismes de sérialisation par défaut
		out.defaultWriteObject();
		
		// on sérialise les attributs normalement non sérialisés
		out.writeInt(x); 
		out.writeInt(y); 
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// appel des mécanismes de désérialisation par défaut
		in.defaultReadObject();

		// on désérialise les attributs normalement non désérialisés
		x = in.readInt(); 
		y = in.readInt(); 
	}
	
	@Override
	public boolean equals(Object point) {
		
		if (point instanceof Point) {
			if ( this.x == ((Point) point).x && this.y == ((Point) point).y ) {
				return true;
			}
		}
		
		return false;		
	}
	
	@Override
	public String toString() {
		
		return "("+x+","+y+")";		
	}
}
