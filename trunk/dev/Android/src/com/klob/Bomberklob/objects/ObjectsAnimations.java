package com.klob.Bomberklob.objects;

public enum ObjectsAnimations {
	
	IDLE("idle"),
	ANIMATE("animate"),
	DESTROY("destroy");

	protected String label;

	ObjectsAnimations(String oLabel) {
		this.label = oLabel;
	}

	public String getLabel() {
		return this.label;
	}

}
