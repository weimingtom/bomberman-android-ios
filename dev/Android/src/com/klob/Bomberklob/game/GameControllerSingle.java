package com.klob.Bomberklob.game;

import com.klob.Bomberklob.objects.PlayerAnimations;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

public class GameControllerSingle extends GameController {
	
	private Engine engine;

	private Thread onTouchEventThread;
	private boolean onTouchEventBoolean;
	
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
		this.onTouchEventBoolean = true;
		this.onTouchEventThread = new Thread() {
			@Override
			public void run() {
				while (onTouchEventBoolean && engine.getSingle().getPlayers()[0] != null) {
					if ( !engine.getSingle().getPlayers()[0].getCurrentAnimation().equals(PlayerAnimations.TOUCHED.getLabel()) && !engine.getSingle().getPlayers()[0].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel())) {
						engine.move(animation);
					}
					try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Log.i("GameControllerSingle","Thread done");
			};
		};
		this.onTouchEventThread.start();
		this.engine.setBombThreadRunning(true);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
		this.onTouchEventBoolean = false;
		this.engine.setBombThreadRunning(false);
	}

	@Override
	public void onDraw(Canvas canvas, int size) {
		this.engine.onDraw(canvas,size);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		return true;
	}

	@Override
	public void update() {
		this.engine.update();
	}

	@Override
	public void pushBomb() {
		if ( this.engine.getSingle().getPlayers()[0] != null ) {
			this.engine.pushBomb(this.engine.getSingle().getPlayers()[0]);	
		}
	}
}