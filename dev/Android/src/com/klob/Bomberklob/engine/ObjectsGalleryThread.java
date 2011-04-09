package com.klob.Bomberklob.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ObjectsGalleryThread extends Thread {
	
	private SurfaceHolder surfaceHolder;
    private ObjectsGallery panel;
    private boolean run = false, sleep = true;

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
	
	public void update() {
		this.sleep = false;
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
