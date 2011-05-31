package com.klob.Bomberklob.editor;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

/**
 * Class representing the controller of the map editor
 */
public class EditorController extends SurfaceView implements SurfaceHolder.Callback {

	private EditorView editorView;

	private int objectsSize;

	private MapEditor mapEditor;
	
	/* Constructeurs  ------------------------------------------------------ */

	/**
	 * Built EditorController according to the parameters passed
	 * @param context The context of the application
	 * @param mapName The name of the map to create
	 */
	
	public EditorController(Context context, String mapName) {		
		super(context);

		this.editorView = new EditorView(getHolder(), this);		
		getHolder().addCallback(this);
		
		this.mapEditor = new MapEditor(mapName);
		this.objectsSize = ResourcesManager.getSize();
		this.setLayoutParams(new FrameLayout.LayoutParams(this.mapEditor.getMap().getBlocks().length*this.objectsSize, this.mapEditor.getMap().getBlocks()[0].length*this.objectsSize));
	}

	/* Getters ------------------------------------------------------------- */

	/**
	 * Returns the current instance of EditorView
	 * @return the current instance of EditorView
	 */
	public EditorView getEditorView() {
		return this.editorView;
	}

	/**
	 * Returns the current instance of mapeditor
	 * @return The current instance of mapeditor
	 */
	public MapEditor getMapEditor() {
		return mapEditor;
	}

	/* Méthodes publiques -------------------------------------------------- */
	
	/**
	 * Adds an object in the editorMap
	 * @param object The desired object
	 * @param point The tile's position of the object
	 */
	public void addObjects(Objects object, Point point) {
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

	/**
	 * Update the current instance of EditorView
	 */
	public void update() {
		this.editorView.update();
	}
}
