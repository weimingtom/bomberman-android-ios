package com.klob.bomberklob.resourcesmanager;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;

public class ResourcesManager {
	
	private static ResourcesManager resourcesmanager;
	
	private Context context;
	private HashMap<String, Bitmap>	bitmaps = new HashMap<String, Bitmap>();
	
	private ResourcesManager(Context context) {
		this.context = context;
	}
	
	/* Méthodes publiques -------------------------------------------------- */

	public static ResourcesManager getInstance(Context context) throws IOException {
		if (null == resourcesmanager) {
			resourcesmanager = new ResourcesManager(context);
		}
		return resourcesmanager;
	}
}
