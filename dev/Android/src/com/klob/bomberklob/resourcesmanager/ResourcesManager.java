package com.klob.bomberklob.resourcesmanager;

import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import com.klob.bomberklob.R;
import com.klob.bomberklob.engine.Point;
import com.klob.bomberklob.objects.AnimatedObjects;
import com.klob.bomberklob.objects.AnimationSequence;
import com.klob.bomberklob.objects.Destructible;
import com.klob.bomberklob.objects.FrameInfo;
import com.klob.bomberklob.objects.Inanimate;
import com.klob.bomberklob.objects.Objects;
import com.klob.bomberklob.objects.Undestructible;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ResourcesManager {

	private static ResourcesManager resourcesmanager;

	private Context context;
	private int tileSize;
	private HashMap<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	private HashMap<String, Objects> objects = new HashMap<String, Objects>();

	/* Constructeur -------------------------------------------------------- */

	private ResourcesManager(Context context,int tileSize) {
		this.context = context;
		this.tileSize = tileSize;
	}

	/* Getters ------------------------------------------------------------- */
	
	public static ResourcesManager getInstance() {
		return getInstance(null,0);
	}

	public static ResourcesManager getInstance(Context context, int tileSize) {
		if (null == resourcesmanager) {
			resourcesmanager = new ResourcesManager(context,tileSize);
		}
		return resourcesmanager;
	}

	public HashMap<String, Bitmap> getBitmaps() {
		return bitmaps;
	}

	public HashMap<String, Objects> getObjects() {
		return objects;
	}

	public Context getContext() {
		return context;
	}

	public int getTileSize() {
		return tileSize;
	}

	/* Méthodes publiques -------------------------------------------------- */

	public void bitmapsInitialisation() {

		XmlResourceParser xpp = this.context.getResources().getXml(R.xml.bitmaps);

		Log.i("ResourcesManager","---------- Loading Bitmaps ----------");
		Bitmap p = null;

		try {
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {

					if(xpp.getName().toLowerCase().equals("png")) {

						if ( xpp.getAttributeValue(null, "name").equals("inanimate")) {
							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.inanimate);
							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)*this.tileSize, xpp.getAttributeIntValue(null, "yTile", 0)*this.tileSize, true);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("player")) {
							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.player);
							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)*this.tileSize, xpp.getAttributeIntValue(null, "yTile", 0)*this.tileSize+(xpp.getAttributeIntValue(null, "yTile", 0)*(this.tileSize/2)), true);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("animate")) {
							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.animate);
							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)*this.tileSize, xpp.getAttributeIntValue(null, "yTile", 0)*this.tileSize, true);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("bombs")) {
							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bombs);
							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)*this.tileSize, xpp.getAttributeIntValue(null, "yTile", 0)*this.tileSize, true);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("explosions")) {
							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bombs);
							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)*this.tileSize, xpp.getAttributeIntValue(null, "yTile", 0)*this.tileSize, true);
						}

						if ( p != null ) {
							Log.i("ResourcesManager","Added Bitmap : " + xpp.getAttributeValue(null, "name"));
							this.bitmaps.put(xpp.getAttributeValue(null, "name"), p);
							p = null;
						}
					}
				}
				eventType = xpp.next();
			}
		}
		catch (Exception e) {
			Log.e("ERROR", "ERROR IN SPRITE TILE  CODE:"+e.toString());
		}
		Log.i("ResourcesManager","----------      Bitmaps loaded      ----------");
	}

	public void animatedObjectsInitialisation() {

		XmlResourceParser xpp = this.context.getResources().getXml(R.xml.animates);

		Log.i("ResourcesManager","---------- Loading animated objects ----------");
		AnimatedObjects animatedObjects = null;

		try {
			int eventType = xpp.getEventType();
			String animationname="";
			AnimationSequence animationsequence = new AnimationSequence();

			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {

					if(xpp.getName().toLowerCase().equals("destructible")) {
						animatedObjects = new Destructible(xpp.getAttributeValue(null, "name"), (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true), xpp.getAttributeIntValue(null, "level", 0), (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true), null, (xpp.getAttributeIntValue(null, "life", 0)));
					}
					else if(xpp.getName().toLowerCase().equals("undestructible")) {
						animatedObjects = new Undestructible(xpp.getAttributeValue(null, "name"), (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true), xpp.getAttributeIntValue(null, "level", 0), (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true), null); 
					}
					else if(xpp.getName().toLowerCase().equals("animation")) {			
						animationname=xpp.getAttributeValue(null, "name");	            	 
						animationsequence = new AnimationSequence();
						animationsequence.name=animationname;
						animationsequence.sequence=new ArrayList<FrameInfo>();
						animationsequence.canLoop = xpp.getAttributeBooleanValue(null,"canLoop", false);	
					}
					else if(xpp.getName().toLowerCase().equals("png")) {
						FrameInfo frameinfo = new FrameInfo();
						Point point = new Point();
						point.x = xpp.getAttributeIntValue(null, "x", 0);
						point.y = xpp.getAttributeIntValue(null, "y", 0);
						frameinfo.point = point;
						frameinfo.nextFrameDelay = xpp.getAttributeIntValue(null,"delayNextFrame", 0);
						animationsequence.sequence.add(frameinfo);
					}
				}
				else if(eventType == XmlPullParser.END_TAG) {
					if(xpp.getName().toLowerCase().equals("animation")) {
						animatedObjects.getAnimations().put(animationname, animationsequence);
					}
					else if ((xpp.getName().toLowerCase().equals("destructible")) || (xpp.getName().toLowerCase().equals("undestructible"))) {
						if ( animatedObjects != null ) {
							animatedObjects.resize();
							this.objects.put(animatedObjects.getImageName(), animatedObjects);
							Log.i("ResourcesManager","Added AnimatedObject : " + animatedObjects.getImageName());
							animatedObjects = null;
						}
					}
				}
				eventType = xpp.next();
			}
		}
		catch (Exception e) {
			Log.e("ERROR", "ERROR IN SPRITE TILE  CODE:"+e.toString());
		}
		Log.i("ResourcesManager","---------- Animated objects loaded  ----------");
	}
	
	public void inanimatedObjectsInitialisation(){
		
		XmlResourceParser xpp = getContext().getResources().getXml(R.xml.inanimates);
		
		Log.i("ResourcesManager","--------- Loading inanimated objects ---------");
		try {
			int eventType = xpp.getEventType();
			Inanimate inanimate = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if(eventType == XmlPullParser.START_TAG) {
					
					if(xpp.getName().toLowerCase().equals("png")) {
						inanimate = new Inanimate(xpp.getAttributeValue(null, "name"), (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true), xpp.getAttributeIntValue(null, "level", 0), (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true), new Point(xpp.getAttributeIntValue(null, "x", 0),xpp.getAttributeIntValue(null, "y", 0)));
						inanimate.resize();
						this.objects.put(inanimate.getImageName(), inanimate);
						Log.i("ResourcesManager","Added InanimatedObject : " + inanimate.getImageName());
						inanimate = null;
					}
					
				}
				eventType = xpp.next();
			}
		}
		catch (Exception e) {
			Log.e("ERROR", "ERROR IN SPRITE TILE  CODE:"+ e.toString());
		}
		Log.i("ResourcesManager","--------- Inanimated objects loaded  ---------");
	}

	/**
	 * Calculates the tile of the coordinate
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return the tile position
	 */
	public Point coToTile(int x, int y) {

		if ( x < 0 || y < 0) {
			return null;
		}
		else {
			return new Point(x/this.tileSize, y/this.tileSize);
		}
	}

	/**
	 * Calculates the coordinate of the tile
	 * 
	 * @param x x coordinate 
	 * @param y y coordinate
	 * @return coordinate of the tile
	 */
	public Point tileToCo(int x, int y) {

		if ( x < 0 || y < 0) {
			return null;
		}
		else {
			return new Point(x*this.tileSize, y*this.tileSize);
		}
	}
}

