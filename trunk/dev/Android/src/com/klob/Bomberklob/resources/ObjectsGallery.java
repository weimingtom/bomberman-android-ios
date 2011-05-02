package com.klob.Bomberklob.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.klob.Bomberklob.objects.Objects;

public class ObjectsGallery extends SurfaceView implements SurfaceHolder.Callback {

	private ObjectsGalleryThread thread;

	private int itemsDisplayed;

	private String selectedItem;

	private int x;
	private int y;

	private int objectsSize = (int) (45*ResourcesManager.getDpiPx());

	private int verticalPadding;

	private int level;

	private ArrayList<Objects> grounds = new ArrayList<Objects>();
	private int currentGroundsItem = 0;

	private ArrayList<Objects> blocks = new ArrayList<Objects>();
	private int currentBlocksItem = 0;

	private Rect[] rects = new Rect[4];
	private Paint paint = new Paint();
	Point point = new Point(-1,-1);

	private boolean vertical = true;
	private int color;

	/* Constructeurs ------------------------------------------------------- */
	
	public ObjectsGallery(Context context) {
		super(context);
		this.itemsDisplayed = 3;
		this.verticalPadding = 0;
		this.selectedItem = null;
		this.level = 0;
		this.color = Color.WHITE;
		this.initialisation();
	}
	
	public ObjectsGallery(Context context, int itemsDisplayed,	String selectedItem, int objectsSize, int verticalPadding,	int level, boolean vertical) {
		super(context);
		this.itemsDisplayed = itemsDisplayed;
		this.selectedItem = selectedItem;
		this.objectsSize = (int) (objectsSize*ResourcesManager.getDpiPx());
		this.verticalPadding = (int) (verticalPadding*ResourcesManager.getDpiPx());
		this.level = level;
		this.color = Color.WHITE;
		this.vertical = vertical;
		this.initialisation();
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

	public int getVerticalPadding() {
		return verticalPadding;
	}

	/* Setters ------------------------------------------------------------- */

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setVertical(boolean bool) {
		this.vertical = bool;
		this.resize();
	}

	public void setItemsDisplayed(int items) {
		if ( items >= 0 ) {
			this.itemsDisplayed = items;
		}
		this.resize();
	}

	public void setVerticalPadding(int padding) {
		this.verticalPadding = (int) (padding*ResourcesManager.getDpiPx());
		this.resize();
	}

	public void setObjectsSize(int objectsSize) {
		this.objectsSize = (int) (objectsSize*ResourcesManager.getDpiPx());
	}

	public void setBackgroundColor(int color) {
		this.color = color;
	}

	public void setRectangles(Point point) {
		rects[0] = new Rect(point.x*objectsSize, point.y*objectsSize, point.x*objectsSize+objectsSize, point.y*objectsSize+objectsSize/15); //HAUT
		rects[1] = new Rect(point.x*objectsSize, point.y*objectsSize, point.x*objectsSize+objectsSize/15, point.y*objectsSize+objectsSize+verticalPadding); // GAUCHE
		rects[2] = new Rect(point.x*objectsSize+objectsSize-objectsSize/15, point.y*objectsSize, point.x*objectsSize+objectsSize, point.y*objectsSize+objectsSize+verticalPadding); // DROITE
		rects[3] = new Rect(point.x*objectsSize, point.y*objectsSize+objectsSize-objectsSize/15+verticalPadding, point.x*objectsSize+objectsSize, point.y*objectsSize+objectsSize+verticalPadding); //BAS
	}

	/* Méthodes publiques -------------------------------------------------- */
	
	private void initialisation() {
		this.thread = new ObjectsGalleryThread(getHolder(), this);
		this.getHolder().addCallback(this);
		this.paint.setColor(Color.RED);
		this.setRectangles(point);
		resize();
	}
	
	private void resize() {
		if ( vertical ) {
			this.setLayoutParams(new FrameLayout.LayoutParams(objectsSize+verticalPadding, objectsSize*itemsDisplayed));
		}
		else {
			this.setLayoutParams(new FrameLayout.LayoutParams(objectsSize*itemsDisplayed, objectsSize+verticalPadding));
		}
	}

	public void loadObjects(HashMap<String, Objects> objects) {

		int i = 0;

		for(Entry<String, Objects> entry : objects.entrySet()) {
			Objects valeur = objects.get(entry.getKey());
			if ( valeur.getAnimations().get("idle") != null ) {
				if ( valeur.getLevel() == 0 ) {
					this.grounds.add(valeur.copy());
				}
				else {
					this.blocks.add(valeur.copy());
				}
			}
			i++;
		}
		
		if ( i < itemsDisplayed ) {
			setItemsDisplayed(i);
		}
		
		Log.i("ObjectsGallery", "Objects Loaded");
	}
	
	public void addObjects(Objects object) {
		if ( object.getLevel() == 0 ) {
			this.grounds.add(object);
		}
		else {
			this.blocks.add(object);
		}
		this.thread.update();
	}
	
	public void removeObjects(Objects object) {
		if ( object.getLevel() == 0 ) {
			this.grounds.remove(object);
		}
		else {
			this.blocks.remove(object);
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		System.out.println("surfaceChanged : SurfaceHolder " + arg0.toString() + "| int " + arg1+ "| int " +arg2+ "| int " +arg3);
		this.thread.update();
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		if (this.thread.getState() == Thread.State.TERMINATED) {
			this.thread = new ObjectsGalleryThread(getHolder(), this);
		}
		this.thread.setRun(true);
		this.thread.start();
		Log.i("ObjectsGallery", "Thread started");
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		this.thread.setRun(false);
		this.thread.update();
		while (retry) {
			try {
				this.thread.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}
		Log.i("ObjectsGallery", "Thread done");
	}

	@Override
	protected void onDraw(Canvas canvas) {

		int i,j;

		canvas.drawColor(this.color);

		if ( this.level == 0 ) {
			for (i = this.currentGroundsItem, j = 0 ; j < this.itemsDisplayed && j < this.grounds.size() ; i++, j++ ) {
				if ( vertical ) {
					this.grounds.get(i).setPosition(new Point(0+verticalPadding,j*objectsSize));
				}
				else {
					this.grounds.get(i).setPosition(new Point(j*objectsSize,0+verticalPadding));
				}
				this.grounds.get(i).onDraw(canvas, objectsSize);
			}
		}
		else if ( this.level == 1 ) {
			for (i = this.currentBlocksItem, j = 0 ; j < this.itemsDisplayed && j < this.blocks.size() ; i++, j++ ) {
				if ( vertical ) {
					this.blocks.get(i).setPosition(new Point(0+verticalPadding,j*objectsSize));
				}
				else {
					this.blocks.get(i).setPosition(new Point(j*objectsSize,0+verticalPadding));
				}
				this.blocks.get(i).onDraw(canvas, objectsSize);
			}
		}

		for (i = 0 ; i < rects.length ; i++ ) {
			canvas.drawRect(rects[i], paint);
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (this.isEnabled() ) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					this.x = (int) event.getX();
					this.y = (int) event.getY();
		
					point = new Point(this.x/objectsSize, (this.y-verticalPadding)/objectsSize);
					setRectangles(point);
					this.thread.update();
		
					if ( this.level == 0 ) {
						if (!vertical) {
							this.selectedItem = this.grounds.get(point.x+currentGroundsItem).getImageName();
						}
						else {
							this.selectedItem = this.grounds.get(point.y+currentGroundsItem).getImageName();
						}				
					}
					else if ( this.level == 1 ) {
						if (!vertical) {
							this.selectedItem = this.blocks.get(point.x+currentBlocksItem).getImageName();
						}
						else {
							this.selectedItem = this.blocks.get(point.y+currentBlocksItem).getImageName();
						}
					}
					System.out.println("Object selected : " + this.selectedItem);
					break;
				case MotionEvent.ACTION_MOVE:
					if (!vertical) {
						if ( (int) event.getX() > this.x + (ResourcesManager.getSize()/2)) {
		
							this.x = (int) event.getX();
		
							if ( this.level == 0 ) {
								if ( this.currentGroundsItem >= 1 ) {
									this.currentGroundsItem--;
									point = new Point(point.x+1, point.y);
									setRectangles(point);
									this.thread.update();
								}
							}
							else {
								if ( this.currentBlocksItem >= 1 ) {
									this.currentBlocksItem--;
									point = new Point(point.x+1, point.y);
									setRectangles(point);
									this.thread.update();
								}
							}
						}
						else if ( (int) event.getX() < this.x - (ResourcesManager.getSize()/2)) {
		
							this.x = (int) event.getX();
		
							if ( this.level == 0 ) {
								if ( this.currentGroundsItem < (this.grounds.size() - this.itemsDisplayed) ) {
									this.currentGroundsItem++;
									point = new Point(point.x-1, point.y);
									setRectangles(point);
									this.thread.update();
								}
							}
							else {
								if ( this.currentBlocksItem < (this.blocks.size() - this.itemsDisplayed) ) {
									this.currentBlocksItem++;
									point = new Point(point.x-1, point.y);
									setRectangles(point);
									this.thread.update();
								}
							}
						}
					}
					else {			
						if ( (int) event.getY() < this.y - (ResourcesManager.getSize()/2)) {
		
							this.y = (int) event.getY();
		
							if ( this.level == 0 ) {
								if ( this.currentGroundsItem < (this.grounds.size() - this.itemsDisplayed) ) {
									this.currentGroundsItem++;
									point = new Point(point.x, point.y-1);
									setRectangles(point);
									this.thread.update();
								}
							}
							else {
								if ( this.currentBlocksItem < (this.blocks.size() - this.itemsDisplayed) ) {
									this.currentBlocksItem++;
									point = new Point(point.x, point.y-1);
									setRectangles(point);
									this.thread.update();
								}
							}
						}
						else if ( (int) event.getY() > this.y + (ResourcesManager.getSize()/2)) {
		
							this.y = (int) event.getY();
		
							if ( this.level == 0 ) {
								if ( this.currentGroundsItem >= 1 ) {
									this.currentGroundsItem--;
									point = new Point(point.x, point.y+1);
									setRectangles(point);
									this.thread.update();
								}
							}
							else {
								if ( this.currentBlocksItem >= 1 ) {
									this.currentBlocksItem--;
									point = new Point(point.x, point.y+1);
									setRectangles(point);
									this.thread.update();
								}
							}
						}
					}
					break;
			}
		}
		return true;
	}
	
	public void update() {
		this.thread.update();
	}
}

