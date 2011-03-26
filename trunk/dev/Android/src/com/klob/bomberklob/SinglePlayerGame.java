package com.klob.bomberklob;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
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


public class SinglePlayerGame extends Activity implements View.OnClickListener{
	
	private Button cancel;
	private Button lancer;
	private Gallery gallery;
	
	private Spinner typePartieSP, nbEnnemisSP, difficulteSP;
	
	// FIXME à changer
	private final static int[] MAP = { 
		R.drawable.m1,
		R.drawable.m2,
		R.drawable.m3,
		R.drawable.m4
	};

 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // plein ecran, à remettre dans chaque onCreate
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.singleplayergame);
        
        cancel = (Button)findViewById(R.id.SinglePlayerGameButtonCancel);
		cancel.setOnClickListener(this);
		
		lancer = (Button)findViewById(R.id.SinglePlayerGameButtonGo);
		lancer.setOnClickListener(this);
		
		typePartieSP = (Spinner) findViewById(R.id.typePartie);
		nbEnnemisSP  = (Spinner) findViewById(R.id.nbEnnemis);
		difficulteSP = (Spinner) findViewById(R.id.difficulte);
		
		getMap();
			
		final TextView map = (TextView) findViewById(R.id.nomMap);
		
		// Pour la Gallery
		
		gallery = (Gallery) findViewById(R.id.galleryz);
		gallery.setAdapter(new ImageAdapter(this, MAP));
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	map.setText(gallery.getSelectedItem().toString());
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
		Log.i("SinglePlayerGame", "onDestroy Solo");
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
 
	
	@Override
	public void onClick(View view) {
		
		if(view == lancer){
//			Intent intent = new Intent(CreerPartieSolo.this, LancerPartieSolo.class);
//			startActivity(intent);
			
			String typeP = typePartieSP.getSelectedItem().toString();
			String ennemis = nbEnnemisSP.getSelectedItem().toString();
			String difficulte = difficulteSP.getSelectedItem().toString();


			Toast.makeText(SinglePlayerGame.this, "Lancement de la partie Solo sur "+
					this.gallery.getSelectedItem().toString()+" en mode "+typeP+" avec "+ennemis+
					" ennemis, ca va être "+difficulte+" !", Toast.LENGTH_SHORT).show();
		}
		else if(view == cancel){
			Intent intent = new Intent(SinglePlayerGame.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}
	
	public int[] getMap() {
		
		int[] tabMaps = null;
		File map = this.getFilesDir();
		map = new File(map.getAbsolutePath()+"/maps");
		
		System.out.println("MAPS :" + map.getPath());
		
		File[] maps = map.listFiles();
		
		if ( null != maps ) {
		
			tabMaps = new int[maps.length];
			
			for (int i = 0 ; i < tabMaps.length ; i++ ) {
				String mapString = maps[i].getName() ;
				mapString = TextUtils.split(mapString, ".")[0];
				//FIXME une fois le nom de la map récupérée comment trouver le bitmap associé ...
			}
		}
		else {
			Toast.makeText(SinglePlayerGame.this, "Maps directory missing", Toast.LENGTH_SHORT).show();
		}
		
		return tabMaps;
	}
	
	// Pour la selection de map
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
}



