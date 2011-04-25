package com.klob.Bomberklob.game;

import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resources.ResourcesManager;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class GameController extends SurfaceView implements SurfaceHolder.Callback {

	protected GameView gameView;

	protected int objectsSize;

	protected int x;
	protected int y;
	protected PlayerAnimations animation;

	/* Constructeurs  ------------------------------------------------------ */

	public GameController(Context context) {		
		super(context);
		this.gameView = new GameView(getHolder(), this);
		this.animation = PlayerAnimations.IDLE;
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

	public abstract void update();

	public abstract void pushBomb();

	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (this.isEnabled()) {
			switch (event.getAction()) {		
			case MotionEvent.ACTION_DOWN:
				this.x = (int) event.getX();
				this.y = (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				if ( (int) event.getX() > this.x+(this.objectsSize) && (int) event.getY() > this.y+(this.objectsSize) ) {
					animation = PlayerAnimations.DOWN_RIGHT;
					this.x = (int) event.getX();
					this.y = (int) event.getY();
				}
				else if ( (int) event.getX() > this.x+(this.objectsSize) && (int) event.getY() < this.y-(this.objectsSize) ) {
					animation = PlayerAnimations.UP_RIGHT;
					this.x = (int) event.getX();
					this.y = (int) event.getY();
				}
				else if ( (int) event.getX() < this.x-(this.objectsSize) && (int) event.getY() < this.y-(this.objectsSize) ) {
					animation = PlayerAnimations.UP_LEFT;
					this.x = (int) event.getX();
					this.y = (int) event.getY();
				}
				else if ( (int) event.getX() < this.x-(this.objectsSize) && (int) event.getY() > this.y+(this.objectsSize) ) {
					animation = PlayerAnimations.DOWN_LEFT;
					this.x = (int) event.getX();
					this.y = (int) event.getY();
				}
				else if ( (int) event.getY() > this.y+(this.objectsSize) ) {
					animation = PlayerAnimations.DOWN;
					this.x = (int) event.getX();
					this.y = (int) event.getY();
				}
				else if ( (int) event.getX() < this.x-(this.objectsSize) ) {
					animation = PlayerAnimations.LEFT;
					this.x = (int) event.getX();
					this.y = (int) event.getY();
				}
				else if ( (int) event.getX() > this.x+(this.objectsSize) ) {
					animation = PlayerAnimations.RIGHT;
					this.x = (int) event.getX();
					this.y = (int) event.getY();
				}
				else if ( (int) event.getY() < this.y-(this.objectsSize) ) {
					animation = PlayerAnimations.UP;
					this.x = (int) event.getX();
					this.y = (int) event.getY();
				}
				break;
			case MotionEvent.ACTION_UP:
				if ( animation == PlayerAnimations.RIGHT) {
					animation = PlayerAnimations.STOP_RIGHT;
				}
				else if ( animation == PlayerAnimations.LEFT) {
					animation = PlayerAnimations.STOP_LEFT;
				}
				else if ( animation == PlayerAnimations.UP) {
					animation = PlayerAnimations.STOP_UP;
				}
				else if ( animation == PlayerAnimations.DOWN) {
					animation = PlayerAnimations.STOP_DOWN;
				}
				else if ( animation == PlayerAnimations.DOWN_RIGHT) {
					animation = PlayerAnimations.STOP_DOWN_RIGHT;
				}
				else if ( animation == PlayerAnimations.DOWN_LEFT) {
					animation = PlayerAnimations.STOP_DOWN_LEFT;
				}
				else if ( animation == PlayerAnimations.UP_RIGHT) {
					animation = PlayerAnimations.STOP_UP_RIGHT;
				}
				else if ( animation == PlayerAnimations.UP_LEFT) {
					animation = PlayerAnimations.STOP_UP_LEFT;
				}
				break;
			}
		}
		return true;		
	}

}
