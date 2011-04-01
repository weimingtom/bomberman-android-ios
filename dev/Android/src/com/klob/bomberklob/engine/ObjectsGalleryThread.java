package com.klob.bomberklob.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ObjectsGalleryThread extends Thread {
	
	private SurfaceHolder surfaceHolder;
    private ObjectsGallery panel;
    private boolean run = false;

    public ObjectsGalleryThread(SurfaceHolder surfaceHolder, ObjectsGallery panel) {
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
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
