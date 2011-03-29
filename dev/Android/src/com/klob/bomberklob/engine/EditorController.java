package com.klob.bomberklob.engine;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


public class EditorController extends Thread {

	private SurfaceHolder surfaceHolder;
    private EditorView editorView;
    private MapEditor mapEditor;
    private boolean run = false;
    
    public EditorController(SurfaceHolder surfaceHolder, EditorView editorView) {
        this.surfaceHolder = surfaceHolder;
        this.editorView = editorView;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

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
