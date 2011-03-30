package com.klob.bomberklob.engine;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


public class EditorController extends Thread {

	private SurfaceHolder surfaceHolder;
    private EditorView editorView;
    private MapEditor mapEditor;
    private boolean run = false;
    
    /* Constructeur -------------------------------------------------------- */
    
    public EditorController(SurfaceHolder surfaceHolder, EditorView editorView) {
        this.surfaceHolder = surfaceHolder;
        this.editorView = editorView;
    }

    /* Getteurs ------------------------------------------------------------ */
    
    public SurfaceHolder getSurfaceHolder() {
		return surfaceHolder;
	}

	public EditorView getEditorView() {
		return editorView;
	}

	public MapEditor getMapEditor() {
		return mapEditor;
	}

	public boolean isRun() {
		return run;
	}
    
    /* Setteurs ------------------------------------------------------------ */

	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
	}

	public void setEditorView(EditorView editorView) {
		this.editorView = editorView;
	}

	public void setMapEditor(MapEditor mapEditor) {
		this.mapEditor = mapEditor;
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
                    this.editorView.onDraw(c);
                }
            } finally {
                if (c != null) {
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
    
	public boolean onTouchEvent(MotionEvent event){
		return false;		
	}
}
