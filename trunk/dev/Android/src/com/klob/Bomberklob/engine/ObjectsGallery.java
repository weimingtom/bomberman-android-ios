package com.klob.Bomberklob.engine;

import java.util.ArrayList;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class ObjectsGallery extends SurfaceView implements SurfaceHolder.Callback {

	private ObjectsGalleryThread thread;

	private int itemsDisplayed = 3;

	private String selectedItem;
	
	private int x;
	private int y;
	
	private int objectsSize = (int) (45*ResourcesManager.getDpiPx());
	
	private int level = 0;

	private ArrayList<Objects> grounds = new ArrayList<Objects>();
	private int currentGroundsItem = 0;
	
	private ArrayList<Objects> blocks = new ArrayList<Objects>();
	private int currentBlocksItem = 0;

	/* Constructeurs ------------------------------------------------------- */
	
	public ObjectsGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.thread = new ObjectsGalleryThread(getHolder(), this);
		getHolder().addCallback(this);
		loadObjects();
	}

	public ObjectsGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.thread = new ObjectsGalleryThread(getHolder(), this);
		getHolder().addCallback(this);
		loadObjects();
	}
	
	/* Getters ------------------------------------------------------------- */
	
	public String getSelectedItem() {
		return this.selectedItem;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getObjectSize() {
		return this.objectsSize;
	}
	
	public int getItemsDisplayed() {
		return this.itemsDisplayed;
	}
	
	/* Setters ------------------------------------------------------------- */
	
	public void SetSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	/* Méthodes publiques -------------------------------------------------- */

	public void loadObjects() {
		for(Entry<String, Objects> entry : ResourcesManager.getObjects().entrySet()) {
			Objects valeur = ResourcesManager.getObject(entry.getKey());
			if ( valeur.getLevel() == 0 ) {
				this.grounds.add(valeur);
			}
			else {
				this.blocks.add(valeur);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (this.thread.getState() == Thread.State.TERMINATED) {
			this.thread = new ObjectsGalleryThread(getHolder(), this);
		}
		this.thread.setRun(true);
		this.thread.start();
		this.setLayoutParams(new FrameLayout.LayoutParams(objectsSize, objectsSize*itemsDisplayed));
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		this.thread.setRun(false);
		while (retry) {
			try {
				this.thread.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		int i,j;
		
		if ( this.level == 0 ) {
			for (i = this.currentGroundsItem, j = 0 ; j < this.itemsDisplayed && j < this.grounds.size() ; i++, j++ ) {
				this.grounds.get(i).setPosition(new Point(0,j));
				this.grounds.get(i).onDraw(canvas, objectsSize);
			}
		}
		else if ( this.level == 1 ) {
			for (i = this.currentBlocksItem, j = 0 ; j < this.itemsDisplayed && j < this.blocks.size() ; i++, j++ ) {
				this.blocks.get(i).setPosition(new Point(0,j));
				this.blocks.get(i).onDraw(canvas, objectsSize);
			}
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event){

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.x = (int) event.getX();
			this.y = (int) event.getY();
			
			Point point = new Point(this.x/objectsSize, this.y/objectsSize);
			if ( this.level == 0 ) {
				this.selectedItem = this.grounds.get(point.y+currentGroundsItem).getImageName();
			}
			else if ( this.level == 1 ) {
				this.selectedItem = this.blocks.get(point.y+currentBlocksItem).getImageName();
			}
			System.out.println("Objects selected : " + this.selectedItem);
			break;
		case MotionEvent.ACTION_MOVE:
		/*	
			if ( (int) event.getX() > this.x + (this.rm.getTileSize()/2)) {
				System.out.println("ON AUGMENTE");
				this.x = (int) event.getX();
				if ( this.level == 1 ) {
					this.level = 0;
				}
				else {
					this.level++;
				}
			}
			else if ( (int) event.getX() < this.x - (this.rm.getTileSize()/2)) {
				System.out.println("ON DECREMENTE");
				this.x = (int) event.getX();
				if ( this.level == 0 ) {
					this.level = 1;
				}
				else {
					this.level--;
				}
			}
			else*/ if ( (int) event.getY() < this.y - (ResourcesManager.getTileSize()/2)) {
				
				this.y = (int) event.getY();
				if ( this.level == 0 ) {
					if ( this.currentGroundsItem < (this.grounds.size() - this.itemsDisplayed) ) {
						this.currentGroundsItem++;
					}
				}
				else {
					if ( this.currentBlocksItem < this.blocks.size() - this.itemsDisplayed ) {
						this.currentBlocksItem++;
					}
				}
			}
			else if ( (int) event.getY() > this.y + (ResourcesManager.getTileSize()/2)) {
				this.y = (int) event.getY();
				if ( this.level == 0 ) {
					if ( this.currentGroundsItem >= 1 ) {
						this.currentGroundsItem--;
					}
				}
				else {
					if ( this.currentBlocksItem >= 1 ) {
						this.currentBlocksItem--;
					}
				}
			}
		}
		return true;
	}

}

