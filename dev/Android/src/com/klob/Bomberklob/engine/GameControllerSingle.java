package com.klob.Bomberklob.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

public class GameControllerSingle extends GameController {
	
	private Engine engine;

	
	/* Constructeurs  ------------------------------------------------------ */
	
	public GameControllerSingle(Context context, String mapName, int enemies, String gametype, boolean random, int difficulty) {
		super(context);
		this.engine = new Engine(mapName, enemies, gametype, random, difficulty);
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public Engine getEngine() {
		return engine;
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		this.setLayoutParams(new FrameLayout.LayoutParams(this.engine.getSingle().getMap().getBlocks().length*this.objectsSize, this.engine.getSingle().getMap().getBlocks()[0].length*this.objectsSize));
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
	}

	@Override
	public void onDraw(Canvas canvas, int size) {
		this.engine.onDraw(canvas,size);
	}
}