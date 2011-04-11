package com.klob.Bomberklob.engine;

import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

public class EditorController extends SurfaceView implements SurfaceHolder.Callback {

	private EditorView editorView;

	private int objectsSize;

	private MapEditor mapEditor;
	
	/* Constructeurs  ------------------------------------------------------ */

	public EditorController(Context context, AttributeSet attrs) {		
		super(context, attrs);
		this.mapEditor = new MapEditor();
		this.editorView = new EditorView(getHolder(), this);
		getHolder().addCallback(this);
	}

	public EditorController(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mapEditor = new MapEditor();
		this.editorView = new EditorView(getHolder(), this);
		getHolder().addCallback(this);
	}

	/* Getters ------------------------------------------------------------- */

	public EditorView getEditorView() {
		return this.editorView;
	}

	public MapEditor getMapEditor() {
		return mapEditor;
	}

	/* Setters ------------------------------------------------------------- */

	public void setMapEditor(MapEditor mapEditor) {
		this.mapEditor = mapEditor;
		this.editorView.update();
	}

	/* Méthodes publiques -------------------------------------------------- */
	
	public void addObjects(Objects object, int x, int y) {
		Point point = new Point(x/objectsSize, y/objectsSize);

		if ( object != null && point.x > 0 && point.x < this.mapEditor.getMap().getBlocks().length-1 && point.y > 0 && point.y < this.mapEditor.getMap().getBlocks()[0].length-1) {
			this.mapEditor.addObject(object, point);			
		}
		this.editorView.update();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		this.editorView.update();
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (this.editorView.getState() == Thread.State.TERMINATED) {
			this.editorView = new EditorView(getHolder(), this);
		}
		this.editorView.setRun(true);
		this.editorView.start();
		this.objectsSize = ResourcesManager.getSize();
		this.setLayoutParams(new FrameLayout.LayoutParams(this.mapEditor.getMap().getBlocks().length*this.objectsSize, this.mapEditor.getMap().getBlocks()[0].length*this.objectsSize));
		this.editorView.update();
		Log.i("EditorController", "Thread started");
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		this.editorView.setRun(false);
		this.editorView.update();
		while (retry) {
			try {
				this.editorView.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}
		Log.i("EditorController", "Thread done");
	}
	
	public void update() {
		this.editorView.update();
	}
}
