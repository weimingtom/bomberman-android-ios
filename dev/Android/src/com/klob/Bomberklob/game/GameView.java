package com.klob.Bomberklob.game;

import com.klob.Bomberklob.resources.ResourcesManager;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameView extends Thread {
	
	private SurfaceHolder surfaceHolder;
    private GameController gameController;
    private boolean run = false;
    
    // desired fps
	private final static int 	MAX_FPS = 30;	
	// maximum number of frames to be skipped
	private final static int	MAX_FRAME_SKIPS = 0;	
	// the frame period
	private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    
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
        
    	long beginTime;		// the time when the cycle begun
		long timeDiff;		// the time it took for the cycle to execute
		int sleepTime;		// ms to sleep (<0 if we're behind)
		int framesSkipped;	// number of frames being skipped 
		
        while (this.run) {
            c = null;
            
            
            try {
                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;	// resetting the frames skipped
               		this.gameController.onDraw(c, ResourcesManager.getSize());
					this.gameController.update();
					// calculate how long did the cycle take
					timeDiff = System.currentTimeMillis() - beginTime;
					// calculate sleep time
					sleepTime = (int)(FRAME_PERIOD - timeDiff);
					
					if (sleepTime > 0) {
						// if sleepTime > 0 we're OK
						try {
							// send the thread to sleep for a short period
							// very useful for battery saving
							Thread.sleep(sleepTime);	
						} catch (InterruptedException e) {}
					}
					
					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						// we need to catch up
						this.gameController.update(); // update without rendering
						sleepTime += FRAME_PERIOD;	// add frame period to check if in next frame
						framesSkipped++;
					}
                }
            } finally {
                if (c != null) {
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            
        }
    }
}
