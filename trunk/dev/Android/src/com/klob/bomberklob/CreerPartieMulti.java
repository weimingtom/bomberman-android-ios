package com.klob.bomberklob;

import com.klob.bomberklob.R;

import android.app.Activity;
import android.content.Context;
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

public class CreerPartieMulti extends Activity implements View.OnClickListener {

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// plein ecran, à remettre dans chaque onCreate
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.creerpartiemulti);

		retour = (Button) findViewById(R.id.boutonRetour);
		retour.setOnClickListener(this);

		lancer = (Button) findViewById(R.id.boutonLancerPartie);
		lancer.setOnClickListener(this);
		
		typePartieSP = (Spinner) findViewById(R.id.typePartie);
		nbEnnemisSP = (Spinner) findViewById(R.id.nbEnnemis);
		difficulteSP = (Spinner) findViewById(R.id.difficulte);
		
			
		final TextView map = (TextView) findViewById(R.id.nomMap);
		
		// Pour la Gallery
		
		final Gallery gallery = (Gallery) findViewById(R.id.galleryz);
		ImageAdapter adapter = new ImageAdapter(this, MAP);
		gallery.setAdapter(adapter);
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView parent, View v, int position, long id) {
            	ImageView img = new ImageView(getBaseContext());
				
				//redimmensionnement auto
				
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
		Log.i("", "onDestroy ");
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
	public void onClick(View v) {
		if (v == lancer) {
			Toast.makeText(CreerPartieMulti.this, "Lancement partie multi",
					Toast.LENGTH_SHORT).show();

		} else if (v == retour) {
			finish();
		}
	}
	
	// Pour la selection de map
	
	public static class ImageAdapter extends BaseAdapter {
		private int[] m_images;
		private Context m_context;
		private int m_itemBackground;
		
		public ImageAdapter(Context context, int[] images) {
			m_context = context;
			m_images=images;
			TypedArray array = context.obtainStyledAttributes(R.styleable.Gallery);
			m_itemBackground = array.getResourceId(
			R.styleable.Gallery_android_galleryItemBackground, 0);
			array.recycle();
		}
		
		@Override
		public int getCount() {
			return m_images.length;
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