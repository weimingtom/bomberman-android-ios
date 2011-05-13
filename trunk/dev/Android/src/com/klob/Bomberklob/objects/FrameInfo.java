package com.klob.Bomberklob.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.graphics.Rect;

public class FrameInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public transient Rect rect = new Rect();
	public int nextFrameDelay;
	public transient int top, left, bottom, right;
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		// appel des mécanismes de sérialisation par défaut
		out.defaultWriteObject();
		
		this.left = this.rect.left;
		this.top = this.rect.top;
		this.right = this.rect.right;
		this.bottom = this.rect.bottom;
		
		out.writeInt(left); 
		out.writeInt(top); 
		out.writeInt(right); 
		out.writeInt(bottom); 
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// appel des mécanismes de désérialisation par défaut
		in.defaultReadObject();
		
		// on désérialise les attributs normalement non désérialisés
		this.left = in.readInt();
		this.top = in.readInt();
		this.right = in.readInt();
		this.bottom = in.readInt();
		
		this.rect = new Rect(this.left, this.top, this.right, this.bottom);
	}
}
