package com.klob.Bomberklob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.klob.Bomberklob.model.Map;
import com.klob.Bomberklob.model.Model;

public class MapEditorLoader extends Activity {

	private ListView mapEditorLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.mapeditorloader);

		Vector<Map> maps = Model.getSystem().getDatabase().getMaps();

		if ( maps.size() > 0 ) {

			mapEditorLoader = (ListView) findViewById(R.id.MapEditorLoaderXML);
			ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			for (int i = 0 ; i < maps.size() ; i++) {
				map = new HashMap<String, String>();
				map.put("mapName", maps.get(i).getName());
				map.put("mapOwner", maps.get(i).getOwner());
				map.put("mapBitmap", this.getDir("maps", i).getAbsolutePath()+"/"+maps.get(i).getName()+".png");
				listItem.add(map);
			}

			
			map = new HashMap<String, String>();
			map.put("mapName", this.getBaseContext().getString(R.string.ButtonCancel));
			map.put("mapOwner", "");
			map.put("mapBitmap", String.valueOf(R.drawable.space));
			listItem.add(map);

			SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.mapeditorloaderitems, new String[] {"mapName", "mapOwner", "mapBitmap"}, new int[] {R.id.mapName, R.id.mapOwner, R.id.mapBitmap});
			mapEditorLoader.setAdapter(mSchedule);

			mapEditorLoader.setOnItemClickListener(new OnItemClickListener() {
				@Override
				@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> a, View v, int position, long id) {
					HashMap<String, String> map = (HashMap<String, String>) mapEditorLoader.getItemAtPosition(position);
					if ( !map.get("mapName").equals(getBaseContext().getString(R.string.ButtonCancel))) {
						setResult(2000, getIntent().putExtra("map", map.get("mapName")));
					}
					finish();
				}
			});

		}
		else {
			this.setResult(2001);
			this.finish();			
		}
	}
}