package com.klob.Bomberklob.engine;

import java.util.ArrayList;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.klob.Bomberklob.objects.HashMapObjects;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class ObjectsGallery extends SurfaceView implements SurfaceHolder.Callback {

	private ObjectsGalleryThread thread;

	private int itemsDisplayed = 3;

	private String selectedItem;

	private int x;
	private int y;

	private int objectsSize = (int) (45*ResourcesManager.getDpiPx());

	private int verticalPadding = 0;

	private int level = 0;

	private ArrayList<Objects> grounds = new ArrayList<Objects>();
	private int currentGroundsItem = 0;

	private ArrayList<Objects> blocks = new ArrayList<Objects>();
	private int currentBlocksItem = 0;

	private Rect[] rects = new Rect[4];
	private Paint paint = new Paint();
	Point point = new Point(-1,-1);

	private boolean vertical = true;

	/* Constructeurs ------------------------------------------------------- */

	public ObjectsGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.thread = new ObjectsGalleryThread(getHolder(), this);
		getHolder().addCallback(this);
	}

	public ObjectsGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.thread = new ObjectsGalleryThread(getHolder(), this);
		getHolder().addCallback(this);
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
	}

	public void setItemsDisplayed(int items) {
		if ( items > 0 ) {
			this.itemsDisplayed = items;
		}
	}

	public void setVerticalPadding(int padding) {
		this.verticalPadding = (int) (padding*ResourcesManager.getDpiPx());

		if ( vertical ) {
			this.setLayoutParams(new FrameLayout.LayoutParams(objectsSize+verticalPadding, objectsSize*itemsDisplayed));
		}
		else {
			this.setLayoutParams(new FrameLayout.LayoutParams(objectsSize*itemsDisplayed, objectsSize+verticalPadding));
		}	
	}

	public void setObjectsSize(int objectsSize) {
		this.objectsSize = (int) (objectsSize*ResourcesManager.getDpiPx());
	}


	public void setRectangles(Point point) {
		rects[0] = new Rect(point.x*objectsSize, point.y*objectsSize, point.x*objectsSize+objectsSize, point.y*objectsSize+objectsSize/15); //HAUT
		rects[1] = new Rect(point.x*objectsSize, point.y*objectsSize, point.x*objectsSize+objectsSize/15, point.y*objectsSize+objectsSize+verticalPadding); // GAUCHE
		rects[2] = new Rect(point.x*objectsSize+objectsSize-objectsSize/15, point.y*objectsSize, point.x*objectsSize+objectsSize, point.y*objectsSize+objectsSize+verticalPadding); // DROITE
		rects[3] = new Rect(point.x*objectsSize, point.y*objectsSize+objectsSize-objectsSize/15+verticalPadding, point.x*objectsSize+objectsSize, point.y*objectsSize+objectsSize+verticalPadding); //BAS
	}

	/* Méthodes publiques -------------------------------------------------- */

	public void loadObjects(HashMapObjects hmo) {

		setRectangles(point);
		paint.setColor(Color.RED);

		for(Entry<String, Objects> entry : hmo.entrySet()) {
			Objects valeur = hmo.get(entry.getKey());
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

		if ( vertical ) {
			this.setLayoutParams(new FrameLayout.LayoutParams(objectsSize+verticalPadding, objectsSize*itemsDisplayed));
		}
		else {
			this.setLayoutParams(new FrameLayout.LayoutParams(objectsSize*itemsDisplayed, objectsSize+verticalPadding));
		}		
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

		Paint p = new Paint();
		p.setColor(Color.WHITE);

		canvas.drawRect(new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), p);

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

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				this.x = (int) event.getX();
				this.y = (int) event.getY();
	
				point = new Point(this.x/objectsSize, (this.y-verticalPadding)/objectsSize);
				setRectangles(point);
	
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
				System.out.println("Objects selected : " + this.selectedItem);
				this.thread.update();
				break;
			case MotionEvent.ACTION_MOVE:
				if (!vertical) {
					if ( (int) event.getX() > this.x + (ResourcesManager.getTileSize()/2)) {
	
						this.x = (int) event.getX();
	
						if ( this.level == 0 ) {
							if ( this.currentGroundsItem >= 1 ) {
								this.currentGroundsItem--;
								point = new Point(point.x+1, point.y);
								setRectangles(point);
							}
						}
						else {
							if ( this.currentBlocksItem >= 1 ) {
								this.currentBlocksItem--;
								point = new Point(point.x+1, point.y);
								setRectangles(point);
							}
						}
					}
					else if ( (int) event.getX() < this.x - (ResourcesManager.getTileSize()/2)) {
	
						this.x = (int) event.getX();
	
						if ( this.level == 0 ) {
							if ( this.currentGroundsItem < (this.grounds.size() - this.itemsDisplayed) ) {
								this.currentGroundsItem++;
								point = new Point(point.x-1, point.y);
								setRectangles(point);
							}
						}
						else {
							if ( this.currentBlocksItem < (this.blocks.size() - this.itemsDisplayed) ) {
								this.currentBlocksItem++;
								point = new Point(point.x-1, point.y);
								setRectangles(point);
							}
						}
					}
				}
				else {			
					if ( (int) event.getY() < this.y - (ResourcesManager.getTileSize()/2)) {
	
						this.y = (int) event.getY();
	
						if ( this.level == 0 ) {
							if ( this.currentGroundsItem < (this.grounds.size() - this.itemsDisplayed) ) {
								this.currentGroundsItem++;
								point = new Point(point.x, point.y-1);
								setRectangles(point);
							}
						}
						else {
							if ( this.currentBlocksItem < (this.blocks.size() - this.itemsDisplayed) ) {
								this.currentBlocksItem++;
								point = new Point(point.x, point.y-1);
								setRectangles(point);
							}
						}
					}
					else if ( (int) event.getY() > this.y + (ResourcesManager.getTileSize()/2)) {
	
						this.y = (int) event.getY();
	
						if ( this.level == 0 ) {
							if ( this.currentGroundsItem >= 1 ) {
								this.currentGroundsItem--;
								point = new Point(point.x, point.y+1);
								setRectangles(point);
							}
						}
						else {
							if ( this.currentBlocksItem >= 1 ) {
								this.currentBlocksItem--;
								point = new Point(point.x, point.y+1);
								setRectangles(point);
							}
						}
					}
				}
				break;
		}
		this.thread.update();
		return true;
	}
	
	public void update() {
		this.thread.update();
	}
}

