package com.klob.Bomberklob.resourcesmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.engine.Point;
import com.klob.Bomberklob.objects.AnimatedObjects;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.Destructible;
import com.klob.Bomberklob.objects.FrameInfo;
import com.klob.Bomberklob.objects.Inanimate;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.Undestructible;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;

public class ResourcesManager {

	private static ResourcesManager resourcesmanager;

	private static Context context;
	private static int tileSize;
	private static float dpiPx;
	private static int size;
	private static int height;
	private static int width;
	private static HashMap<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	private static HashMap<String, Objects> objects = new HashMap<String, Objects>();
	private static HashMap<String, Hashtable<String, AnimationSequence>> playersAnimation = new HashMap<String, Hashtable<String, AnimationSequence>>();
	private static HashMap<String, Bomb> bombs = new HashMap<String, Bomb>();

	/* Constructeur -------------------------------------------------------- */

	private ResourcesManager(Context context) {
		ResourcesManager.context = context;
		ResourcesManager.dpiPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());

		height = context.getResources().getDisplayMetrics().heightPixels;
		width = context.getResources().getDisplayMetrics().widthPixels;
		
		if ( ((height-(50*dpiPx))/15) < ((width-(50*dpiPx))/14) ) {
			size = (int) ((height-(50*dpiPx))/15);
		}
		else {
			size = (int) ((width-(50*dpiPx))/14);
		}
		bitmapsInitialisation();
	}

	/* Getters ------------------------------------------------------------- */

	public static ResourcesManager setInstance(Context context) {
		if (null == resourcesmanager) {
			resourcesmanager = new ResourcesManager(context);
		}
		return resourcesmanager;
	}

	public static HashMap<String, Bitmap> getBitmaps() {
		return bitmaps;
	}

	public static HashMap<String, Objects> getObjects() {
		return objects;
	}
	
	public static HashMap<String, Hashtable<String, AnimationSequence>> getPlayersAnimations() {
		return playersAnimation;
	}
	
	public static HashMap<String, Bomb> getBombs() {
		return bombs;
	}
	
	public static float getDpiPx() {
		return dpiPx;
	}

	public static Context getContext() {
		return context;
	}

	public static int getTileSize() {
		return tileSize;
	}

	public static int getSize() {
		return size;
	}
	
	public static int getHeight() {
		return height;
	}

	public static int getWidth() {
		return width;
	}
	
	/* Setters ------------------------------------------------------------- */

	public static void setDpiPx(float dpiPx) {
		ResourcesManager.dpiPx =  dpiPx;
	}

	/* Méthodes publiques -------------------------------------------------- */

	public static void bitmapsInitialisation() {

		XmlResourceParser xpp = context.getResources().getXml(R.xml.bitmaps);

		Log.i("ResourcesManager","---------- Loading Bitmaps ----------");
		Bitmap p = null;

		try {
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {
					
					if(xpp.getName().toLowerCase().equals("bitmaps")) {
						tileSize = xpp.getAttributeIntValue(null, "size", 0);
					}
					else if(xpp.getName().toLowerCase().equals("png")) {

						if ( xpp.getAttributeValue(null, "name").equals("inanimate")) {
							p = BitmapFactory.decodeResource(ResourcesManager.context.getResources(), R.drawable.inanimate);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("players")) {
							p = BitmapFactory.decodeResource(ResourcesManager.context.getResources(), R.drawable.players);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("animate")) {
							p = BitmapFactory.decodeResource(ResourcesManager.context.getResources(), R.drawable.animate);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("bombs")) {
							p = BitmapFactory.decodeResource(ResourcesManager.context.getResources(), R.drawable.bombs);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("explosions")) {
							p = BitmapFactory.decodeResource(ResourcesManager.context.getResources(), R.drawable.bombs);
						}

						if ( p != null ) {
							Log.i("ResourcesManager","Added Bitmap : " + xpp.getAttributeValue(null, "name"));
							ResourcesManager.bitmaps.put(xpp.getAttributeValue(null, "name"), p);
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

	public static void animatedObjectsInitialisation() {

		XmlResourceParser xpp = context.getResources().getXml(R.xml.animates);

		Log.i("ResourcesManager","---------- Loading animated objects ----------");
		AnimatedObjects animatedObjects = null;

		try {
			int eventType = xpp.getEventType();
			String animationname="";
			AnimationSequence animationsequence = new AnimationSequence();

			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {

					if(xpp.getName().toLowerCase().equals("destructible")) {
						animatedObjects = new Destructible(xpp.getAttributeValue(null, "name"), (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true), xpp.getAttributeIntValue(null, "level", 0), (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true), (xpp.getAttributeIntValue(null, "life", 0)));
					}
					else if(xpp.getName().toLowerCase().equals("undestructible")) {
						animatedObjects = new Undestructible(xpp.getAttributeValue(null, "name"), (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true), xpp.getAttributeIntValue(null, "level", 0), (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true)); 
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
							objects.put(animatedObjects.getImageName(), animatedObjects);
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
	
	public static void inanimatedObjectsInitialisation(){
		
		XmlResourceParser xpp = context.getResources().getXml(R.xml.inanimates);
		
		Log.i("ResourcesManager","--------- Loading inanimated objects ---------");
		try {
			int eventType = xpp.getEventType();
			Inanimate inanimate = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if(eventType == XmlPullParser.START_TAG) {
					
					if(xpp.getName().toLowerCase().equals("png")) {
						inanimate = new Inanimate(xpp.getAttributeValue(null, "name"), (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true), xpp.getAttributeIntValue(null, "level", 0), (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true), new Point(xpp.getAttributeIntValue(null, "x", 0),xpp.getAttributeIntValue(null, "y", 0)));
						objects.put(inanimate.getImageName(), inanimate);
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
	
	public static void playersInitialisation() {

		XmlResourceParser xpp = context.getResources().getXml(R.xml.players);
		
		Log.i("ResourcesManager","--------------- Loading player ---------------");
		Hashtable<String, AnimationSequence> playerAnimation = null;
		String name = null;

		try {
			int eventType = xpp.getEventType();
			String animationname="";
			AnimationSequence animationsequence = new AnimationSequence();;
			
			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {
					
					if(xpp.getName().toLowerCase().equals("player")) {
						playerAnimation = new Hashtable<String, AnimationSequence>();
						name = xpp.getAttributeValue(null, "name");
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
				}else if(eventType == XmlPullParser.END_TAG) {
					if(xpp.getName().toLowerCase().equals("animation")) {
						playerAnimation.put(animationname, animationsequence);
					}
					else if (xpp.getName().toLowerCase().equals("player")) {
						if ( playerAnimation != null ) {
							playersAnimation.put(name, playerAnimation);
							Log.i("ResourcesManager","Added Animation : " + name);
							playerAnimation = null;
							name = null;
						}
					}
				} else if(eventType == XmlPullParser.TEXT) {
					System.out.println("Text "+xpp.getText());
				}
				eventType = xpp.next();
			}
		}
		catch (Exception e) {
			Log.e("ERROR", "ERROR IN SPRITE TILE  CODE:"+e.toString());
		}
		System.out.println("--------------- Player Loaded ----------------");
	}
	
	public static void bombsInitialisation() {

		XmlResourceParser xpp = context.getResources().getXml(R.xml.bombs);

		Log.i("ResourcesManager","---------------- Loading bombs ---------------");
		Bomb bomb = null;

		try {
			int eventType = xpp.getEventType();
			String animationname="";
			AnimationSequence animationsequence = new AnimationSequence();;

			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {

					if(xpp.getName().toLowerCase().equals("bomb")) {
						bomb = new Bomb(xpp.getAttributeValue(null, "name"), (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true), xpp.getAttributeIntValue(null, "level", 0), (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true), (xpp.getAttributeIntValue(null, "power", 0)), (xpp.getAttributeIntValue(null, "time", 0)));
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
						bomb.getAnimations().put(animationname, animationsequence);
					}
					else if ((xpp.getName().toLowerCase().equals("bomb"))) {
						if ( bomb != null ) {
							bombs.put(bomb.getImageName(), bomb);
							Log.i("ResourcesManager","Added bombs : " + bomb.getImageName());
							bomb = null;
						}
					}
				}
				eventType = xpp.next();
			}
		}
		catch (Exception e) {
			Log.e("ERROR", "ERROR IN SPRITE TILE  CODE:"+e.toString());
		}
		Log.i("ResourcesManager","---------------- Bombs loaded  ---------------");
	}
}