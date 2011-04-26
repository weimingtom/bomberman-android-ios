package com.klob.Bomberklob.menus;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Map;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.resources.ResourcesManager;

public class EditorLoader extends Activity implements View.OnClickListener {

	private TextView mapName, mapOwner;
	private Vector<Map> maps;
	private Gallery gallery;
	
	private Button cancel, load;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.editorloader);

		this.maps = Model.getSystem().getDatabase().getMaps();

		if ( maps.size() > 0 ) {

			//FIXME Seulement les maps officielles
			
			String[] mapBitmap = new String[this.maps.size()];
			for (int i = 0; i < mapBitmap.length ; i++) {
				mapBitmap[i] = this.getFilesDir().getAbsolutePath()+"/maps/"+this.maps.get(i).getName()+"/"+this.maps.get(i).getName()+".png";
			}
			
			this.mapName = (TextView) findViewById(R.id.EditorLoaderTextViewMapName);
			this.mapName.setText(maps.get(0).getName());
			
			this.mapOwner = (TextView) findViewById(R.id.EditorLoaderTextViewMapOwner);
			this.mapOwner.setText(maps.get(0).getOwner());
			
			this.gallery = (Gallery) findViewById(R.id.galleryz);
			this.gallery.setAdapter(new ImageAdapter(getApplicationContext(), mapBitmap));
			this.gallery.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					mapName.setText(maps.get(position).getName());
					mapOwner.setText(maps.get(position).getOwner());
				}
			});
			
			this.cancel = (Button) findViewById(R.id.EditorLoaderButtonCancel);
			this.cancel.setOnClickListener(this);

			this.load = (Button) findViewById(R.id.EditorLoaderButtonLoad);
			this.load.setOnClickListener(this);

		}
		else {
			this.setResult(2001);
			this.finish();			
		}
	}
	
	public static class ImageAdapter extends BaseAdapter {
		private String[] m_images;
		private Context m_context;
		private int m_itemBackground;

		public ImageAdapter(Context context, String[] images) {
			this.m_context = context;
			this.m_images=images;
			TypedArray array = context.obtainStyledAttributes(R.styleable.Gallery);
			this.m_itemBackground = array.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
			array.recycle();
		}

		@Override
		public int getCount() {
			return this.m_images.length;
		}
		@Override
		public Object getItem(int position) {
			return position;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView img = new ImageView(m_context);
			img.setImageBitmap(BitmapFactory.decodeFile(m_images[position]));
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			img.setLayoutParams(new Gallery.LayoutParams( (int) ((ResourcesManager.getSize()*15)/1.75) , (int) ((ResourcesManager.getSize()*13)/1.75) ) );
			img.setBackgroundResource(m_itemBackground);
			return img;
		}
	}

	@Override
	public void onClick(View v) {
		if ( this.load == v) {
			setResult(2000, getIntent().putExtra("map", mapName.getText().toString()));
			this.finish();
		}
		else if ( this.cancel == v) {
			this.finish();
		}
	}
}
