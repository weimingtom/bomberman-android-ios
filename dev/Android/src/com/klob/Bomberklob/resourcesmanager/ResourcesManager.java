package com.klob.Bomberklob.resourcesmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.engine.Point;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.Destructible;
import com.klob.Bomberklob.objects.FrameInfo;
import com.klob.Bomberklob.objects.Inanimate;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.Undestructible;

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
	private static HashMap<String, Hashtable<String, AnimationSequence>> bombsAnimation = new HashMap<String, Hashtable<String, AnimationSequence>>();

	/* Constructeur -------------------------------------------------------- */

	private ResourcesManager(Context context) {
		ResourcesManager.context = context;
		ResourcesManager.dpiPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());

		height = context.getResources().getDisplayMetrics().heightPixels;
		width = context.getResources().getDisplayMetrics().widthPixels;
		
		if ( ((height-(50*dpiPx))/21) < ((width-(50*dpiPx))/15) ) {
			size = (int) ((height-(50*dpiPx))/21);
		}
		else {
			size = (int) ((width-(50*dpiPx))/15);
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
	
	public static HashMap<String, Hashtable<String, AnimationSequence>> getBombsAnimations() {
		return bombsAnimation;
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
		Hashtable<String, AnimationSequence> objectAnimation = null;
		String imageName = null;
		int level = 0, life = 0;
		boolean hit = false, fireWall = false;

		try {
			int eventType = xpp.getEventType();
			String animationname="";
			AnimationSequence animationsequence = new AnimationSequence();

			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {
					
					if (xpp.getName().toLowerCase().equals("destructible") || xpp.getName().toLowerCase().equals("undestructible") ) {
						objectAnimation = new Hashtable<String, AnimationSequence>();
						imageName = xpp.getAttributeValue(null, "name");
						hit = (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true);
						level = xpp.getAttributeIntValue(null, "level", 0);
						fireWall = (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true);
						if ( xpp.getName().toLowerCase().equals("destructible") ) {
							life = (xpp.getAttributeIntValue(null, "life", 0));
						}
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
						objectAnimation.put(animationname, animationsequence);
					}
					else if(xpp.getName().toLowerCase().equals("destructible")) {
						objects.put(imageName, new Destructible(imageName, hit, level, fireWall, life, objectAnimation, "idle"));
						Log.i("ResourcesManager","Added Destructible : " + imageName);
					}
					else if(xpp.getName().toLowerCase().equals("undestructible")) {
						objects.put(imageName, new Undestructible(imageName, hit, level, fireWall, objectAnimation, "idle"));
						Log.i("ResourcesManager","Added Undestructible : " + imageName);
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
						System.out.println("<animation name="+xpp.getAttributeValue(null, "name")+" canLoop="+xpp.getAttributeBooleanValue(null,"canLoop", false)+">");
						animationname=xpp.getAttributeValue(null, "name");	            	 
						animationsequence = new AnimationSequence();
						animationsequence.name=animationname;
						animationsequence.sequence=new ArrayList<FrameInfo>();
						animationsequence.canLoop = xpp.getAttributeBooleanValue(null,"canLoop", false);	
					}
					else if(xpp.getName().toLowerCase().equals("png")) {
						System.out.println("<png x="+xpp.getAttributeIntValue(null, "x", 0)+" y="+xpp.getAttributeIntValue(null, "y", 0)+" delayNextFrame="+xpp.getAttributeIntValue(null,"delayNextFrame", 0)+" />");
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
		Hashtable<String, AnimationSequence> bombAnimation = null;
		String name = null;

		try {
			int eventType = xpp.getEventType();
			String animationname="";
			AnimationSequence animationsequence = new AnimationSequence();;
			
			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {
					
					if(xpp.getName().toLowerCase().equals("bomb")) {
						bombAnimation = new Hashtable<String, AnimationSequence>();
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
						bombAnimation.put(animationname, animationsequence);
					}
					else if (xpp.getName().toLowerCase().equals("bomb")) {
						if ( bombAnimation != null ) {
							bombsAnimation.put(name, bombAnimation);
							Log.i("ResourcesManager","Added Bomb : " + name);
							bombAnimation = null;
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
		Log.i("ResourcesManager","---------------- Bombs loaded  ---------------");
	}
}