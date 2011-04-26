package com.klob.Bomberklob.menus;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.game.SinglePlayerLayout;
import com.klob.Bomberklob.model.Map;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.resources.ResourcesManager;

public class SinglePlayer extends Activity implements View.OnClickListener{

	private Button cancel;
	private Button create;
	private Gallery gallery;
	private TextView mapName;

	private Spinner gameType, enemiesNumber, enemiesDifficulty, time;

	private Vector<Map> maps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.singleplayermenu);

		this.maps = Model.getSystem().getDatabase().getMaps();

		this.cancel = (Button)findViewById(R.id.SinglePlayerGameButtonCancel);
		this.cancel.setOnClickListener(this);

		this.create = (Button)findViewById(R.id.SinglePlayerGameButtonGo);
		this.create.setOnClickListener(this);

		this.gameType = (Spinner) findViewById(R.id.SinglePlayerGameSpinnerType);
		this.enemiesNumber  = (Spinner) findViewById(R.id.SinglePlayerGameSpinnerEnemiesNumber);
		this.enemiesDifficulty = (Spinner) findViewById(R.id.SinglePlayerGameSpinnerEnemiesDifficulty);
		this.time = (Spinner) findViewById(R.id.SinglePlayerGameSpinnerTime);
		this.time.setSelection(2);

		this.mapName = (TextView) findViewById(R.id.SinglePlayerGameMapName);
		this.mapName.setText(maps.get(0).getName());

		
		String[] mapBitmap = new String[this.maps.size()];
		for (int i = 0; i < mapBitmap.length ; i++) {
			mapBitmap[i] = this.getFilesDir().getAbsolutePath()+"/maps/"+this.maps.get(i).getName()+"/"+this.maps.get(i).getName()+".png";
		}

		this.gallery = (Gallery) findViewById(R.id.galleryz);
		this.gallery.setAdapter(new ImageAdapter(getApplicationContext(), mapBitmap));
		this.gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				mapName.setText(maps.get(position).getName());
			}
		});

	}

	@Override
	protected void onStop() {
		Log.i("SinglePlayerGame", "onStop ");
		super.onStop();
	}

	@Override
	protected void onDestroy(){
		Log.i("SinglePlayerGame", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onResume(){
		Log.i("SinglePlayerGame", "onResume ");
		super.onResume();
	}

	@Override
	protected void onPause(){
		Log.i("SinglePlayerGame", "onPause ");
		super.onPause();
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
	public void onClick(View view) {

		Intent intent = null;

		if( view == create ){
			intent = new Intent(SinglePlayer.this, SinglePlayerLayout.class);
			intent.putExtra("map", this.mapName.getText().toString());
			intent.putExtra("enemies", Integer.parseInt(enemiesNumber.getSelectedItem().toString()));
			intent.putExtra("gametype", gameType.getSelectedItem().toString());
			intent.putExtra("random", false);
			intent.putExtra("time", time.getSelectedItemPosition());
			intent.putExtra("difficulty", enemiesDifficulty.getSelectedItemPosition());
			startActivity(intent);
			this.finish();
		}
		else if(view == cancel){
			intent = new Intent(SinglePlayer.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}
}



