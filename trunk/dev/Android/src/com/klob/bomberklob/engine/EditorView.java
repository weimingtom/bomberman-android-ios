package com.klob.bomberklob.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class EditorView extends SurfaceView implements SurfaceHolder.Callback {
	
	private EditorController editorController;

	public EditorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.editorController = new EditorController(getHolder(), this);
		getHolder().addCallback(this);
	}
	
	public EditorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.editorController = new EditorController(getHolder(), this);
		getHolder().addCallback(this);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		return false;		
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
	}
}
