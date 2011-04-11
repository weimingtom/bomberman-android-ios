package com.klob.Bomberklob.engine;

import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class GameController extends SurfaceView implements SurfaceHolder.Callback {
	
	protected GameView gameView;
	
	protected int objectsSize;
	
	/* Constructeurs  ------------------------------------------------------ */

	public GameController(Context context) {		
		super(context);
		this.gameView = new GameView(getHolder(), this);
		getHolder().addCallback(this);
	}
	
    /* Getteurs ------------------------------------------------------------ */
	
	public GameView getGameView() {
		return gameView;
	}

    /* Setteurs ------------------------------------------------------------ */
	
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}
	
	/* Méthodes publiques -------------------------------------------------- */

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (this.gameView.getState() == Thread.State.TERMINATED) {
			this.gameView = new GameView(getHolder(), this);
		}
		this.gameView.setRun(true);
		this.gameView.start();
		this.objectsSize = ResourcesManager.getSize();
		Log.i("GameController", "Thread started");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		this.gameView.setRun(false);
		while (retry) {
			try {
				this.gameView.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}
		Log.i("GameController", "Thread done");		
	}
	
	public abstract void onDraw(Canvas canvas, int size);

}
