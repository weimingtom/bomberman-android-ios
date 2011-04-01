package com.klob.bomberklob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
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
import android.widget.Toast;

import com.klob.bomberklob.model.Model;


public class SinglePlayerGame extends Activity implements View.OnClickListener{

	private Model model;

	private Button cancel;
	private Button create;
	private Gallery gallery;
	private TextView mapName;

	private Spinner typePartieSP, nbEnnemisSP, difficulteSP;

	private int[] mapBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.singleplayergame);

		this.model = Model.getInstance(this);

		this.mapBitmap = new int[this.model.getMap().size()];

		if ( this.mapBitmap.length > 0 ) {
			
			for (int i = 0; i < this.mapBitmap.length ; i++) {
				this.mapBitmap[i] = this.model.getMap().get(i).getId();
			}

			this.cancel = (Button)findViewById(R.id.SinglePlayerGameButtonCancel);
			this.cancel.setOnClickListener(this);

			this.create = (Button)findViewById(R.id.SinglePlayerGameButtonGo);
			this.create.setOnClickListener(this);

			this.typePartieSP = (Spinner) findViewById(R.id.SinglePlayerGameSpinnerType);
			this.nbEnnemisSP  = (Spinner) findViewById(R.id.SinglePlayerGameSpinnerEnemiesNumber);
			this.difficulteSP = (Spinner) findViewById(R.id.SinglePlayerGameSpinnerEnemiesDifficulty);

			this.mapName = (TextView) findViewById(R.id.SinglePlayerGameMapName);
			
			this.gallery = (Gallery) findViewById(R.id.galleryz);
			this.gallery.setAdapter(new ImageAdapter(this, this.mapBitmap));
			this.gallery.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					mapName.setText(mapBitmap[position]);
				}
			});
		}
		else {
			Intent intent = new Intent(SinglePlayerGame.this, Home.class);
			startActivity(intent);
			Toast.makeText(SinglePlayerGame.this, R.string.SinglePlayerGameErrorLoadingMap, Toast.LENGTH_SHORT).show();
			this.finish();			
		}
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
		private int[] m_images;
		private Context m_context;
		private int m_itemBackground;

		public ImageAdapter(Context context, int[] images) {
			this.m_context = context;
			this.m_images=images;
			TypedArray array = context.obtainStyledAttributes(R.styleable.Gallery);
			this.m_itemBackground = array.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground, 0);
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
			img.setImageResource(m_images[position]);

			//redimmensionnement auto FIXME Correct pour tous les écrans ?
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			img.setLayoutParams(new Gallery.LayoutParams(115, 115));
			img.setBackgroundResource(m_itemBackground);
			return img;
		}
	}

	@Override
	public void onClick(View view) {

		Intent intent = null;

		if( view == create ){
			String typeP = typePartieSP.getSelectedItem().toString();
			String ennemis = nbEnnemisSP.getSelectedItem().toString();
			String difficulte = difficulteSP.getSelectedItem().toString();


			Toast.makeText(SinglePlayerGame.this, "Lancement de la partie Solo sur "+
					this.gallery.getSelectedItem().toString()+" en mode "+typeP+" avec "+ennemis+
					" ennemis, ca va être "+difficulte+" !", Toast.LENGTH_SHORT).show();
		}
		else if(view == cancel){
			intent = new Intent(SinglePlayerGame.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}
}



