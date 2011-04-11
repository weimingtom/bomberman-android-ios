package com.klob.Bomberklob.engine;

import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

public class GameControllerSingle extends GameController {
	
	private Engine engine;
	
	private int objectsSize;
	
	/* Constructeurs  ------------------------------------------------------ */
	
	public GameControllerSingle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GameControllerSingle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public Engine getEngine() {
		return engine;
	}
	
	/* Setteurs ------------------------------------------------------------ */

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		this.objectsSize = ResourcesManager.getSize();
		//this.setLayoutParams(new FrameLayout.LayoutParams(this.engine.getSingle().getMap().getBlocks().length*this.objectsSize, this.engine.getSingle().getMap().getBlocks()[0].length*this.objectsSize));
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {

	}

	@Override
	public void onDraw(Canvas canvas, int size) {
		this.engine.onDraw(canvas,size);
	}
}