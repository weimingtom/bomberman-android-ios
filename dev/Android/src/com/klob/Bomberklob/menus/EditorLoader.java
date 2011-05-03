package com.klob.Bomberklob.menus;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

		setContentView(R.layout.editorloader);

		this.maps = Model.getSystem().getDatabase().getMaps();
		
		Vector<String> mapBitmap = new Vector<String>();
		for (int i = 0; i < this.maps.size() ; i++) {
			if ( this.maps.get(i).isOfficial() ) {
				this.maps.remove(i);
				i--;
			}
			else {
				mapBitmap.add(this.getFilesDir().getAbsolutePath()+"/maps/"+this.maps.get(i).getName()+"/"+this.maps.get(i).getName()+".png");
			}
		}

		if ( mapBitmap.size() > 0 ) {

			this.mapName = (TextView) findViewById(R.id.EditorLoaderTextViewMapName);
			this.mapName.setText(maps.get(0).getName());
			
			this.mapOwner = (TextView) findViewById(R.id.EditorLoaderTextViewMapOwner);
			this.mapOwner.setText(maps.get(0).getOwner());
			
			this.gallery = (Gallery) findViewById(R.id.galleryz);
			this.gallery.setAdapter(new ImageAdapter(getApplicationContext(), mapBitmap));
			this.gallery.setOnItemClickListener(new OnItemClickListener() {

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
		private Vector<String> m_images;
		private Context m_context;
		private int m_itemBackground;

		public ImageAdapter(Context context, Vector<String> images) {
			this.m_context = context;
			this.m_images=images;
			TypedArray array = context.obtainStyledAttributes(R.styleable.Gallery);
			this.m_itemBackground = array.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
			array.recycle();
		}


		public int getCount() {
			return this.m_images.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView img = new ImageView(m_context);
			img.setImageBitmap(BitmapFactory.decodeFile(m_images.get(position)));
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			img.setLayoutParams(new Gallery.LayoutParams( (int) ((ResourcesManager.getSize()*ResourcesManager.MAP_WIDTH)/1.5) , (int) ((ResourcesManager.getSize()*ResourcesManager.MAP_HEIGHT)/1.5)) );
			img.setBackgroundResource(m_itemBackground);
			return img;
		}
	}

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
