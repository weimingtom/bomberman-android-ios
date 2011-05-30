package com.klob.Bomberklob.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import com.klob.Bomberklob.objects.PlayerAnimations;

public class GameControllerSingle extends GameController {
	
	private Engine engine;
	private boolean isRestart = false;
	
	/* Constructeurs  ------------------------------------------------------ */
	
	public GameControllerSingle(Context context, String mapName, int enemies, String gametype, boolean random, int difficulty) {
		super(context);
		this.engine = new Engine(mapName, enemies, gametype, random, difficulty);
	}
	
	/* Getteurs ------------------------------------------------------------ */
	
	public Engine getEngine() {
		return engine;
	}
	
	/* Méthodes surchargées ------------------------------------------------ */
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		this.setLayoutParams(new FrameLayout.LayoutParams(this.engine.getSingle().getMap().getWidth()*this.objectsSize, this.engine.getSingle().getMap().getHeight()*this.objectsSize));
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
	}

	@Override
	public void onDraw(Canvas canvas, int size) {
		if ( !isRestart ) {
			this.engine.onDraw(canvas,size);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		return true;
	}

	@Override
	public void update() {		
		if ( this.isEnabled() ) {
			if ( !engine.getSingle().getPlayers()[0].getCurrentAnimation().equals(PlayerAnimations.TOUCHED.getLabel()) && !engine.getSingle().getPlayers()[0].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel()) ) {
				engine.getSingle().getPlayers()[0].setCurrentAnimation(animation);
			}
			this.engine.update();
		}
	}

	@Override
	public void pushBomb() {
		if ( this.engine.getSingle().getPlayers()[0].getPosition() != null ) {
			this.engine.pushBomb(this.engine.getSingle().getPlayers()[0]);	
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.engine.getSingle().setBombThreadRunning(enabled);
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	public void restartGame() {
		this.isRestart = true;
		
		/* Animation du joueur par defaut */
		this.animation = PlayerAnimations.IDLE;		
		this.engine.restartGame();
		
		this.isRestart = false;
	}
}