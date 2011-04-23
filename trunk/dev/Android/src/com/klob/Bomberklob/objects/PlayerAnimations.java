package com.klob.Bomberklob.objects;

public enum PlayerAnimations {

	IDLE("idle"),
	RIGHT("right"),
	LEFT("left"),
	UP("up"),
	DOWN("down"),
	DOWN_RIGHT("down_right"),
	DOWN_LEFT("down_left"),
	UP_RIGHT("up_right"),
	UP_LEFT("up_left"),
	STOP_UP("stop_up"),
	STOP_DOWN("stop_down"),
	STOP_RIGHT("stop_right"),
	STOP_LEFT("stop_left"),
	STOP_UP_RIGHT("stop_up_right"),
	STOP_UP_LEFT("stop_up_left"),
	STOP_DOWN_RIGHT("stop_down_right"),
	STOP_DOWN_LEFT("stop_down_left"),
	KILL("kill");
	

	private String label;

	PlayerAnimations(String pLabel) {
		this.label = pLabel;
	}

	public String getLabel() {
		return this.label;
	}
}