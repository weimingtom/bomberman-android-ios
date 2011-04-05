package com.klob.Bomberklob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
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
	private LinearLayout editorLayout, editorControllerLayout;
	private RelativeLayout editorRelativeLayoutObjectsGallery;
	
	private Bundle bundle;

	private Button menu;
	private CheckBox checkBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.mapeditor);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);		
		
		this.editorRelativeLayoutObjectsGallery = (RelativeLayout) findViewById(R.id.MapEditorRelativeLayoutObjectsGallery);
		this.editorRelativeLayoutObjectsGallery.setLayoutParams(new LinearLayout.LayoutParams( (int) (50*ResourcesManager.getDpiPx()), (int) (dm.heightPixels-(50*ResourcesManager.getDpiPx())) ) );

		this.editorControllerLayout = (LinearLayout) findViewById(R.id.MapEditorLinearLayoutEditorController);
		this.editorControllerLayout.setLayoutParams(new LinearLayout.LayoutParams( (int) (dm.widthPixels-(50*ResourcesManager.getDpiPx())), (int) (dm.heightPixels-(50*ResourcesManager.getDpiPx())) ) );
		
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
        
		
		this.editorLayout = (LinearLayout) findViewById(R.id.MapEditorLayout);
		this.editorLayout.setOnClickListener(this);

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
			Intent intent = new Intent(MapEditor.this, MapEditorMenu.class);
			startActivityForResult(intent, 1000);
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if ( resultCode == 2000) {
			checkBox.setChecked(false);
			objectsGallery.setLevel(0);
			editorController.getEditorView().setLevel(0);
			objectsGallery.SetSelectedItem(null);
		}
		else if ( resultCode == 2001) {

		    Bitmap bm = Bitmap.createBitmap(this.editorController.getWidth(), this.editorController.getHeight(), Bitmap.Config.ARGB_8888);
		    Canvas pictureCanvas = new Canvas(bm);
		    this.editorController.getMapEditor().getMap().groundsOnDraw(pictureCanvas);
		    this.editorController.getMapEditor().getMap().onDraw(pictureCanvas);

			File dir = this.getDir("maps", Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
			File f = new File (dir.getAbsolutePath()+"/"+bundle.getString("map")+".png");
			
			try {
				f.createNewFile();
				FileOutputStream fos = new FileOutputStream(f);
				bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
				fos.flush();
				fos.close();

			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
			this.editorController.getMapEditor().getMap().saveMap(getApplicationContext());
			Model.getSystem().getDatabase().newMap(bundle.getString("map"), Model.getUser().getPseudo(), 1);
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
