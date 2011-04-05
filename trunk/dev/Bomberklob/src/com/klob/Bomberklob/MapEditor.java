package com.klob.Bomberklob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.klob.Bomberklob.engine.EditorController;
import com.klob.Bomberklob.engine.ObjectsGallery;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class MapEditor extends Activity implements View.OnClickListener {

	private EditorController editorController;

	private ObjectsGallery objectsGallery;
	private LinearLayout layout, l1;
	private RelativeLayout r1;
	
	private Bundle bundle;

	private Button menu;
	private CheckBox checkBox;
	
	private Bitmap bm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.mapeditor);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);		
		
		this.r1 = (RelativeLayout) findViewById(R.id.MapEditorRelativeLayoutGallery);
		this.r1.setLayoutParams(new LinearLayout.LayoutParams( (int) (50*ResourcesManager.getDpiPx()), (int) (dm.heightPixels-(50*ResourcesManager.getDpiPx())) ) );

		this.l1 = (LinearLayout) findViewById(R.id.MapEditorLinearLayoutEditorController);
		this.l1.setLayoutParams(new LinearLayout.LayoutParams( (int) (dm.widthPixels-(50*ResourcesManager.getDpiPx())), (int) (dm.heightPixels-(50*ResourcesManager.getDpiPx())) ) );
		
		this.objectsGallery = (ObjectsGallery) findViewById(R.id.MapEditorObjectsGallery);

		this.menu = (Button) findViewById(R.id.MapEditorButtonMenu);
		this.menu.setOnClickListener(this);
		
		this.editorController = (EditorController) findViewById(R.id.MapEditorFrameLayout);
		this.editorController.setObjectsGallery(this.objectsGallery);
		
		this.checkBox = (CheckBox) findViewById(R.id.MapEditorCheckBox);
		this.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked) {
					objectsGallery.setLevel(1);
					editorController.getEditorView().setLevel(1);
				}
				else {
					objectsGallery.setLevel(0);
					editorController.getEditorView().setLevel(0);
				}
				objectsGallery.SetSelectedItem(null);
			}
		});
		
		this.bundle = getIntent().getExtras();
        if(bundle.getString("map")!= null) {
        	this.editorController.getMapEditor().loadMap(getApplicationContext(), bundle.getString("map"));
        }
        
		
		this.layout = (LinearLayout) findViewById(R.id.MapEditorLayout);
		this.layout.setOnClickListener(this);

	}

	@Override
	protected void onStop() {
		Log.i("MapEditor", "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy(){
		Log.i("MapEditor", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onResume(){
		Log.i("MapEditor", "onResume");
		super.onResume();
	}

	@Override
	protected void onPause(){
		Log.i("MapEditor", "onPause");
		super.onPause();
	}  

	@Override
	public void onClick(View arg0) {
		
		if ( this.menu == arg0 ) {
			
			this.layout.setDrawingCacheEnabled(true);
			this.layout.post(new Runnable() {
		        public void run() {
		        	layout.setDrawingCacheEnabled(true);
		    	    // this is the important code :)
		    	    // Without it the view will have a
		    	    // dimension of 0,0 and the bitmap will
		    	    // be null
		        	layout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
		    	            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		    	    //v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
		        	layout.layout(0, 0, layout.getWidth(), layout.getHeight());
		        	layout.buildDrawingCache(true);
		    	    bm = Bitmap.createBitmap(layout.getDrawingCache());
		    	    layout.setDrawingCacheEnabled(false); //
		        }
		    });
			
			Intent intent = new Intent(MapEditor.this, MapEditorMenu.class);
			startActivityForResult(intent, 1000);
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if ( resultCode == 2000) {
			
		}
		else if ( resultCode == 2001) {
			// TODO Auto-generated method stub
		
			
			// On crée le répertoire quoi qu'il arrive
			File dir = this.getDir("maps", Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
			
			// On dit où l'on voudra créer notre fichier
			File f = new File (dir.getAbsolutePath()+"/"+bundle.getString("map")+".png");
			
			this.editorController.getMapEditor().getMap().saveMap(getApplicationContext());
			
			try {
				
				// on crée le nouveau fichier;
				f.createNewFile();
				
				// ouverture d'un flux de sortie vers le fichier "map.getName()+".klob""
				FileOutputStream fos = new FileOutputStream(f);
			
				bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
				fos.flush();
				fos.close();
			
				Model.getSystem().getDatabase().newMap(bundle.getString("map"), Model.getUser().getPseudo(), 1, dir.getAbsolutePath()+"/"+bundle.getString("map")+".png");

			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			Intent intent = new Intent(MapEditor.this, Home.class);
			startActivity(intent);
			this.finish();
		}
		else if ( resultCode == 2002) {
			this.editorController.getMapEditor().loadMap(getApplicationContext(), bundle.getString("map"));
		}
		else if ( resultCode == 2003) {
			Intent intent = new Intent(MapEditor.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}
}
