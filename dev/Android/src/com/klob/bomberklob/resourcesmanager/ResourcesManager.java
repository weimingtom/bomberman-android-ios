package com.klob.bomberklob.resourcesmanager;

import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ResourcesManager {

	private static ResourcesManager resourcesmanager;

	private Context context;
	private HashMap<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();

	private ResourcesManager(Context context) {
		this.context = context;
		bitmapsInitialisation();
	}

	/* Méthodes publiques -------------------------------------------------- */

	public static ResourcesManager getInstance(Context context) {
		if (null == resourcesmanager) {
			resourcesmanager = new ResourcesManager(context);
		}
		return resourcesmanager;
	}

	private void bitmapsInitialisation() {

		XmlResourceParser xpp = this.context.getResources().getXml(R.xml.bitmaps);

		Log.i("ResourcesManager","Loading Bitmaps");
		Bitmap p = null;

		try {
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT){

				if(eventType == XmlPullParser.START_TAG) {

					if(xpp.getName().toLowerCase().equals("png")) {

						if ( xpp.getAttributeValue(null, "name").equals("inanimate")) {
							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.inanimate);
							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)/*this.tileSize*/, xpp.getAttributeIntValue(null, "yTile", 0)/*this.tileSize*/, true);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("player")) {
							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.player);
							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)/*this.tileSize*/, xpp.getAttributeIntValue(null, "yTile", 0)/*this.tileSize*/+(xpp.getAttributeIntValue(null, "yTile", 0)/*(this.tileSize/2)*/), true);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("animate")) {
//							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.animate);
//							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)/*this.tileSize*/, xpp.getAttributeIntValue(null, "yTile", 0)*this.tileSize+(xpp.getAttributeIntValue(null, "yTile", 0)*(this.tileSize/2)), true);
						}
						else if ( xpp.getAttributeValue(null, "name").equals("bombs")) {
							p = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bombs);
							p = Bitmap.createScaledBitmap(p, xpp.getAttributeIntValue(null, "xTile", 0)/*this.tileSize*/, xpp.getAttributeIntValue(null, "yTile", 0)/*this.tileSize*/, true);
						}

						if ( p != null ) {
							Log.i("ResourcesManager","Added Bitmap : " + xpp.getAttributeValue(null, "name"));
							this.bitmaps.put(xpp.getAttributeValue(null, "name"), p);
						}
					}
				}
				p = null;
				eventType = xpp.next();
			}
		}
		catch (Exception e) {
			Log.e("ERROR", "ERROR IN SPRITE TILE  CODE:"+e.toString());
		}
		Log.i("ResourcesManager","Bitmaps Loaded ");
	}
}
