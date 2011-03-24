package com.android.Bomberman;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class CreerPartieSolo extends Activity implements View.OnClickListener{
	
	private Button retour;
	private Button lancer;
	private String nomMap;
	
	private Spinner typePartieSP, nbEnnemisSP, difficulteSP;
	
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
        
        setContentView(R.layout.creerpartiesolo);
        
        retour = (Button)findViewById(R.id.boutonRetour);
		retour.setOnClickListener(this);
		
		lancer = (Button)findViewById(R.id.boutonLancer);
		lancer.setOnClickListener(this);
		
		typePartieSP = (Spinner) findViewById(R.id.typePartie);
		nbEnnemisSP = (Spinner) findViewById(R.id.nbEnnemis);
		difficulteSP = (Spinner) findViewById(R.id.difficulte);
		
			
		final TextView map = (TextView) findViewById(R.id.nomMap);
		
		// Pour la Gallery
		
		final Gallery gallery = (Gallery) findViewById(R.id.galleryz);
		gallery.setAdapter(new ImageAdapter(this, MAP));
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
            	ImageView img = new ImageView(getBaseContext());
				
				// FIXME redimmensionnement auto
				img.setLayoutParams(new Gallery.LayoutParams(235, 235));
				
            	nomMap = gallery.getSelectedItem().toString();
            	map.setText(nomMap);
//              Toast.makeText(CreerPartieSolo.this, "" + gallery.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	@Override
	protected void onStop() {
		Log.i("", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("", "onDestroy Solo");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("", "onPause ");
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


			Toast.makeText(CreerPartieSolo.this, "Lancement de la partie Solo sur "+
					this.nomMap+" en mode "+typeP+" avec "+ennemis+
					" ennemis, ca va être "+difficulte+" !", Toast.LENGTH_SHORT).show();
		}
		else if(view == retour){
			Intent intent = new Intent(CreerPartieSolo.this, Bomberman.class);
			startActivity(intent);
			this.onDestroy();
		}
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
				
				//redimmensionnement auto
				img.setScaleType(ImageView.ScaleType.FIT_XY);
				img.setLayoutParams(new Gallery.LayoutParams(115, 115));
				img.setBackgroundResource(m_itemBackground);
				return img;
		}
	}
}



