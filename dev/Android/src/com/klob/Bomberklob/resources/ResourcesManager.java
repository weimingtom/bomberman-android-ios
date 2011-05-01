package com.klob.Bomberklob.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.util.TypedValue;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.Destructible;
import com.klob.Bomberklob.objects.FrameInfo;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.ObjectsAnimations;
import com.klob.Bomberklob.objects.Undestructible;

public class ResourcesManager {

	private static ResourcesManager resourcesmanager;


	public final static int MAP_HEIGHT = 15;
	public final static int MAP_WIDTH = 21;

	private static Context context;
	private static float dpiPx;
	private static int size;
	private static int tileSize;
	private static int height;
	private static int width;
	private static HashMap<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	private static HashMap<String, Objects> objects = new HashMap<String, Objects>();
	private static HashMap<String, Hashtable<String, AnimationSequence>> playersAnimation = new HashMap<String, Hashtable<String, AnimationSequence>>();
	private static HashMap<String, Hashtable<String, AnimationSequence>> bombsAnimation = new HashMap<String, Hashtable<String, AnimationSequence>>();
	
	/* Sons */
	private static HashMap<String, Integer> sounds = new HashMap<String, Integer>();
	private static AudioManager soundManager;
	private static SoundPool soundPool;

	
	/* Constructeur -------------------------------------------------------- */

	private ResourcesManager(Context context) {
		ResourcesManager.context = context;
		
		/* Ecran */
		ResourcesManager.dpiPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
		height = context.getResources().getDisplayMetrics().heightPixels;
		width = context.getResources().getDisplayMetrics().widthPixels;

		if ( ((height-(50*dpiPx))/MAP_WIDTH) < ((width-(50*dpiPx))/MAP_HEIGHT) ) {
			size = (int) ((height-(50*dpiPx))/MAP_WIDTH);
		}
		else {
			size = (int) ((width-(50*dpiPx))/MAP_HEIGHT);
		}
		
		/* Son */
		soundsInitialisation();

		/* Images */
		bitmapsInitialisation();
		objectsInitialisation();
		playersInitialisation();
		bombsInitialisation();

		Log.i("ResourcesManager","dpiPx : " + dpiPx);
		Log.i("ResourcesManager","tileSize : " + tileSize);
		Log.i("ResourcesManager","size : " + size);
		Log.i("ResourcesManager","height : " + height);
		Log.i("ResourcesManager","width : " + width);
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
						if ( xpp.getAttributeValue(null, "name").equals("players")) {							
							p = BitmapFactory.decodeResource(ResourcesManager.context.getResources(), R.drawable.players);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("objects")) {
							p = BitmapFactory.decodeResource(ResourcesManager.context.getResources(), R.drawable.objects);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("bombs")) {
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

	public static void objectsInitialisation() {

		XmlResourceParser xpp = context.getResources().getXml(R.xml.objects);

		Log.i("ResourcesManager","-------------- Loading objects  --------------");
		Hashtable<String, AnimationSequence> animations = null;
		String imageName = null;
		int level = 0, life = 0, damages = 0;
		boolean hit = false, fireWall = false;

		try {
			int eventType = xpp.getEventType();
			String animationname="";
			AnimationSequence animationsequence = new AnimationSequence();

			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {

					if (xpp.getName().toLowerCase().equals("destructible") || xpp.getName().toLowerCase().equals("undestructible") ) {
						animations = new Hashtable<String, AnimationSequence>();
						imageName = xpp.getAttributeValue(null, "name");
						hit = (xpp.getAttributeIntValue(null, "hit", 0) == 0 ? false : true);
						level = xpp.getAttributeIntValue(null, "level", 0);
						fireWall = (xpp.getAttributeIntValue(null, "fireWall", 0) == 0 ? false : true);
						if ( xpp.getName().toLowerCase().equals("destructible") ) {
							life = (xpp.getAttributeIntValue(null, "life", 0));
						}
						damages = xpp.getAttributeIntValue(null, "damages", 0);
					}
					else if(xpp.getName().toLowerCase().equals("animation")) {			
						animationname=xpp.getAttributeValue(null, "name");	    
						animationsequence = new AnimationSequence();
						animationsequence.name=animationname;
						animationsequence.sequence=new ArrayList<FrameInfo>();
						animationsequence.canLoop = xpp.getAttributeBooleanValue(null,"canLoop", false);
						animationsequence.sound = xpp.getAttributeValue(null, "sound");	
					}
					else if(xpp.getName().toLowerCase().equals("framerect")) {
						FrameInfo frameinfo = new FrameInfo();
						Rect frame = new Rect();
						frame.top = xpp.getAttributeIntValue(null, "top", 0);
						frame.bottom = xpp.getAttributeIntValue(null, "bottom", 0);
						frame.left = xpp.getAttributeIntValue(null, "left", 0);
						frame.right = xpp.getAttributeIntValue(null, "right", 0);
						frameinfo.rect = frame;	
						frameinfo.nextFrameDelay = xpp.getAttributeIntValue(null,"delayNextFrame", 0);
						animationsequence.sequence.add(frameinfo);
					}
				}
				else if(eventType == XmlPullParser.END_TAG) {

					if(xpp.getName().toLowerCase().equals("animation")) {
						animations.put(animationname, animationsequence);
					}
					else if(xpp.getName().toLowerCase().equals("destructible")) {
						objects.put(imageName, new Destructible(imageName, animations, ObjectsAnimations.IDLE, hit, level, fireWall, damages, life));
						Log.i("ResourcesManager","Added Destructible : " + imageName);
					}
					else if(xpp.getName().toLowerCase().equals("undestructible")) {
						if ( animations.get(ObjectsAnimations.IDLE.getLabel()) != null ) {
							objects.put(imageName, new Undestructible(imageName, animations, ObjectsAnimations.IDLE, hit, level, fireWall, damages));
							Log.i("ResourcesManager","Added Undestructible : " + imageName);
						}
						else if ( animations.get(ObjectsAnimations.ANIMATE.getLabel()) != null ) {
							objects.put(imageName, new Undestructible(imageName, animations, ObjectsAnimations.ANIMATE, hit, level, fireWall, damages));
							Log.i("ResourcesManager","Added Undestructible : " + imageName);
						}
						else if ( animations.get(ObjectsAnimations.DESTROY.getLabel()) != null ) {
							objects.put(imageName, new Undestructible(imageName, animations, ObjectsAnimations.DESTROY, hit, level, fireWall, damages));
							Log.i("ResourcesManager","Added Undestructible : " + imageName);
						}
					}
				}
				eventType = xpp.next();
			}
		}
		catch (Exception e) {
			Log.e("ERROR", "ERROR IN SPRITE TILE  CODE:"+e.toString());
		}
		Log.i("ResourcesManager","--------------- Objects loaded ---------------");
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
					else if(xpp.getName().toLowerCase().equals("png")) {

						android.graphics.Rect frame = new android.graphics.Rect();
						frame.top = xpp.getAttributeIntValue(null, "top", 0);
						frame.bottom = xpp.getAttributeIntValue(null, "bottom", 0);
						frame.left = xpp.getAttributeIntValue(null, "left", 0);
						frame.right = xpp.getAttributeIntValue(null, "right", 0);

						Bitmap bm = Bitmap.createBitmap(frame.right-frame.left, frame.bottom-frame.top, Bitmap.Config.ARGB_8888);
						Canvas c = new Canvas(bm);
						c.drawBitmap(bitmaps.get("players"), frame, new android.graphics.Rect(0 , 0 , frame.right-frame.left, frame.bottom-frame.top), new Paint());					

						bitmaps.put(name+xpp.getAttributeValue(null, "name"), bm);
						Log.i("ResourcesManager","Added Bitmap : " + name+xpp.getAttributeValue(null, "name"));
					}
					else if(xpp.getName().toLowerCase().equals("animation")) {
						animationname=xpp.getAttributeValue(null, "name");	            	 
						animationsequence = new AnimationSequence();
						animationsequence.name=animationname;
						animationsequence.sequence=new ArrayList<FrameInfo>();
						animationsequence.canLoop = xpp.getAttributeBooleanValue(null,"canLoop", false);	
						animationsequence.sound = xpp.getAttributeValue(null, "sound");	
					}
					else if(xpp.getName().toLowerCase().equals("framerect")) {
						FrameInfo frameinfo = new FrameInfo();
						Rect frame = new Rect();
						frame.top = xpp.getAttributeIntValue(null, "top", 0);
						frame.bottom = xpp.getAttributeIntValue(null, "bottom", 0);
						frame.left = xpp.getAttributeIntValue(null, "left", 0);
						frame.right = xpp.getAttributeIntValue(null, "right", 0);
						frameinfo.rect = frame;				
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
						animationsequence.sound = xpp.getAttributeValue(null, "sound");	
					}
					else if(xpp.getName().toLowerCase().equals("framerect")) {
						FrameInfo frameinfo = new FrameInfo();
						Rect frame = new Rect();
						frame.top = xpp.getAttributeIntValue(null, "top", 0);
						frame.bottom = xpp.getAttributeIntValue(null, "bottom", 0);
						frame.left = xpp.getAttributeIntValue(null, "left", 0);
						frame.right = xpp.getAttributeIntValue(null, "right", 0);
						frameinfo.rect = frame;	
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

	public static void soundsInitialisation() {
		
		soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
		soundManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);		

		sounds.put("destroy1", soundPool.load(context, R.raw.destroy1,1));
		sounds.put("bombplanted", soundPool.load(context, R.raw.bombplanted,1));
		sounds.put("explosion1", soundPool.load(context, R.raw.explosion1,1));

	}
	
	public static int playSoundPool(String s) {
		return soundPool.play(sounds.get(s), Model.getSystem().getVolume(), Model.getSystem().getVolume(), 1, 0, 1f);
	}
	
	public static void stopSoundPool(String s) {
		soundPool.stop(sounds.get(s));
	}
	
	/**
	 * Calculates the tile of the coordinate
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return the tile position
	 */
	public static Point coToTile(int x, int y) {

		if ( x < 0 || y < 0) {
			return null;
		}
		else {
			return new Point(x/size, y/size);
		}
	}

	/**
	 * Calculates the coordinate of the tile
	 * 
	 * @param x x coordinate 
	 * @param y y coordinate
	 * @return coordinate of the tile
	 */
	public static Point tileToCo(int x, int y) {

		if ( x < 0 || y < 0) {
			return null;
		}
		else {
			return new Point(x*size, y*size);
		}
	}
}