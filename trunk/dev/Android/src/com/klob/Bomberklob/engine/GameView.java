package com.klob.Bomberklob.engine;

import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameView extends Thread {
	
	private SurfaceHolder surfaceHolder;
    private GameController gameController;
    private boolean run = false;
    
    /* Constructeur -------------------------------------------------------- */

	public GameView(SurfaceHolder surfaceHolder, GameController gameController) {
        this.surfaceHolder = surfaceHolder;
        this.gameController = gameController;
    }

    /* Getteurs ------------------------------------------------------------ */
    
    public SurfaceHolder getSurfaceHolder() {
		return surfaceHolder;
	}

	public GameController getGameController() {
		return gameController;
	}

	public boolean isRun() {
		return run;
	}

	 /* Setteurs ------------------------------------------------------------ */

	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	/* Methodes publiques -------------------------------------------------- */

	@Override
    public void run() {
        Canvas c;
        while (this.run) {
            c = null;            
            try {
                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {
               		this.gameController.onDraw(c, ResourcesManager.getSize());
                	this.gameController.update();
                }
            } finally {
                if (c != null) {
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
}
