package com.klob.Bomberklob.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class EditorView extends Thread {

	private SurfaceHolder surfaceHolder;
    private EditorController editorView;
    private boolean run = false;
    
    private boolean level = true;
    
    /* Constructeur -------------------------------------------------------- */

	public EditorView(SurfaceHolder surfaceHolder, EditorController editorView) {
        this.surfaceHolder = surfaceHolder;
        this.editorView = editorView;
    }

    /* Getteurs ------------------------------------------------------------ */
    
    public SurfaceHolder getSurfaceHolder() {
		return surfaceHolder;
	}

	public EditorController getEditorView() {
		return editorView;
	}
	
	public boolean getLevel() {
		return this.level;
	}

	public boolean isRun() {
		return run;
	}
    
    /* Setteurs ------------------------------------------------------------ */

	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
	}

	public void setEditorView(EditorController editorView) {
		this.editorView = editorView;
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
               		this.editorView.getMapEditor().onDraw(c, level);
                }
            } finally {
                if (c != null) {
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
