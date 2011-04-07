package com.klob.Bomberklob.objects;

import java.util.HashMap;

public class HashMapObjects extends HashMap<String, Objects> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Objects get(String objects) {
		
		Objects o = super.get(objects);
		
		if ( o instanceof Inanimate ) {
			o = new Inanimate((Inanimate) o);
		}
		else if ( o instanceof Destructible ) {
			o = new Destructible((Destructible) o);
		}
		else if ( o instanceof Undestructible) {
			o = new Undestructible((Undestructible) o);
		}
		else if ( o instanceof Player) {
			o = new Player((Player) o);
		}
		
		return o;
		
	}

}
