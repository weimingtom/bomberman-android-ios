package com.klob.Bomberklob.resources;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Paint extends android.graphics.Paint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Paint() {
		super();
	}

	public Paint(int flags) {
		super(flags);
	}

	public  Paint(Paint paint) {
		super(paint);
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		// appel des mécanismes de sérialisation par défaut
		out.defaultWriteObject();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// appel des mécanismes de désérialisation par défaut
		in.defaultReadObject();
	}

}
