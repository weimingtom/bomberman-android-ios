package com.klob.Bomberklob.editor;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class EditorView extends Thread {

	private SurfaceHolder surfaceHolder;
    private EditorController editorController;
    private boolean run = false;
    
    private boolean level = true, sleep = true;
    
    /* Constructeur -------------------------------------------------------- */

	public EditorView(SurfaceHolder surfaceHolder, EditorController editorView) {
        this.surfaceHolder = surfaceHolder;
        this.editorController = editorView;
    }

    /* Getteurs ------------------------------------------------------------ */
    
    public SurfaceHolder getSurfaceHolder() {
		return surfaceHolder;
	}

	public EditorController getEditorController() {
		return editorController;
	}
	
	public boolean getLevel() {
		return this.level;
	}

	public boolean isRun() {
		return run;
	}
	
	public void update() {
		this.sleep = false;
	}
    
    /* Setteurs ------------------------------------------------------------ */

	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
	}

	public void setEditorController(EditorController editorController) {
		this.editorController = editorController;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
	
	public void setLevel(boolean level) {
		this.level = level;
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
               		this.editorController.getMapEditor().onDraw(c, level);
                }
            } finally {
                if (c != null) {
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            sleep = true;
            while (sleep) {
            	try {
            		sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
    }
}
