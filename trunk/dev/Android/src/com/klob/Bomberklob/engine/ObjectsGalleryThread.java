package com.klob.Bomberklob.engine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class ObjectsGalleryThread extends Thread {
	
	private SurfaceHolder surfaceHolder;
    private ObjectsGallery panel;
    private boolean run = false;

    public ObjectsGalleryThread(SurfaceHolder surfaceHolder, ObjectsGallery panel) {
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
    }
    
	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
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
                    this.panel.onDraw(c);
                }
            } finally {
                if (c != null) {
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
