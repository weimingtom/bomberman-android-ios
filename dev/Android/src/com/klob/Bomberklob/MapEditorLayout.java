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
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.klob.Bomberklob.engine.EditorController;
import com.klob.Bomberklob.engine.ObjectsGallery;
import com.klob.Bomberklob.engine.Point;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class MapEditorLayout extends Activity implements View.OnClickListener {

	private EditorController editorController;

	private ObjectsGallery objectsGallery, objectsGallery2;
	private LinearLayout editorControllerLayout;
	private RelativeLayout editorRelativeLayoutObjectsGallery, editorRelativeLayoutMenu;
	
	private Bundle bundle;

	private Button menu;
	private CheckBox checkBox;
	
	private int menuSize = 50;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.mapeditor);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);	
		
		this.editorRelativeLayoutMenu = (RelativeLayout) findViewById(R.id.MapEditorRelativeLayoutMenu);
		this.editorRelativeLayoutMenu.setLayoutParams(new LinearLayout.LayoutParams( ResourcesManager.getHeight(), (int) (menuSize*ResourcesManager.getDpiPx())) );
		
		this.editorRelativeLayoutObjectsGallery = (RelativeLayout) findViewById(R.id.MapEditorRelativeLayoutObjectsGallery);
		this.editorRelativeLayoutObjectsGallery.setLayoutParams(new LinearLayout.LayoutParams( (int) (menuSize*ResourcesManager.getDpiPx()), (int) (ResourcesManager.getWidth()-(menuSize*ResourcesManager.getDpiPx())) ) );
		
		this.editorControllerLayout = (LinearLayout) findViewById(R.id.MapEditorLinearLayoutEditorController);
		this.editorControllerLayout.setLayoutParams(new LinearLayout.LayoutParams( (int) (ResourcesManager.getHeight()-(menuSize*ResourcesManager.getDpiPx())), (int) (ResourcesManager.getWidth()-(menuSize*ResourcesManager.getDpiPx())) ) );
		
		this.objectsGallery = (ObjectsGallery) findViewById(R.id.MapEditorObjectsGallery);
		this.objectsGallery.setLevel(1);
		this.objectsGallery.loadObjects(ResourcesManager.getObjects());
		this.objectsGallery.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {	
				objectsGallery2.setSelectedItem(null);
				objectsGallery2.setRectangles(new Point(-1,-1));
				return false;
			}
		});
		
		this.objectsGallery2 = (ObjectsGallery) findViewById(R.id.MapEditorPlayersGallery);
		this.objectsGallery2.setLevel(1);
		this.objectsGallery2.setItemsDisplayed(4);
		this.objectsGallery2.setVertical(false);
		this.objectsGallery2.loadObjects(ResourcesManager.getPlayers());
		this.objectsGallery2.setObjectsSize(30);
		this.objectsGallery2.setVerticalPadding(15);
		this.objectsGallery2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				objectsGallery.setSelectedItem(null);
				objectsGallery.setRectangles(new Point(-1,-1));
				return false;
			}
		});
		
		this.editorController = (EditorController) findViewById(R.id.MapEditorFrameLayout);
		this.editorController.getEditorView().setLevel(true);
		this.editorController.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				String object = objectsGallery.getSelectedItem();
				
				if ( object == null ) {
					object = objectsGallery2.getSelectedItem();
				}
				editorController.addObjects(object, (int) arg1.getX(), (int) arg1.getY());
				return false;
			}
		});
		
		this.menu = (Button) findViewById(R.id.MapEditorButtonMenu);
		this.menu.setOnClickListener(this);
		
		this.checkBox = (CheckBox) findViewById(R.id.MapEditorCheckBox);
		this.checkBox.setChecked(true);
		this.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked) {
					objectsGallery.setLevel(1);
					editorController.getEditorView().setLevel(true);
				}
				else {
					objectsGallery.setLevel(0);
					editorController.getEditorView().setLevel(false);
				}
				objectsGallery2.setSelectedItem(null);
				objectsGallery.setSelectedItem(null);
				objectsGallery.setRectangles(new Point(-1,-1));
			}
		});
		
		this.bundle = getIntent().getExtras();
        if(bundle.getString("map")!= null) {
        	this.editorController.getMapEditor().loadMap(getApplicationContext(), bundle.getString("map"));
        }
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
			Intent intent = new Intent(MapEditorLayout.this, MapEditorMenu.class);
			startActivityForResult(intent, 1000);
		}
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if ( resultCode == 2000) {
			checkBox.setChecked(false);
			objectsGallery.setLevel(0);
			editorController.getEditorView().setLevel(false);
			objectsGallery.setSelectedItem(null);
			objectsGallery.setRectangles(new Point(-1,-1));
		}
		else if ( resultCode == 2001) {

		    Bitmap bm = Bitmap.createBitmap(this.editorController.getWidth(), this.editorController.getHeight(), Bitmap.Config.ARGB_8888);
		    
		    if ( this.editorController.getMapEditor().getMap().saveMap(getApplicationContext()) ) {
			    Canvas pictureCanvas = new Canvas(bm);
			    this.editorController.getMapEditor().getMap().groundsOnDraw(pictureCanvas, ResourcesManager.getSize());
			    this.editorController.getMapEditor().getMap().onDraw(pictureCanvas, ResourcesManager.getSize());
	
				File dir = this.getDir("maps", Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
				File f = new File (dir.getAbsolutePath()+"/"+bundle.getString("map")+".png");
				
				try {
					f.createNewFile();
					FileOutputStream fos = new FileOutputStream(f);
					bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
	
				} catch(IOException ioe) {
					ioe.printStackTrace();
				}
				
				Model.getSystem().getDatabase().newMap(bundle.getString("map"), Model.getUser().getPseudo(), 1);
				Intent intent = new Intent(MapEditorLayout.this, Home.class);
				startActivity(intent);
				this.finish();
		    }
		    else {
		    	Toast.makeText(MapEditorLayout.this, R.string.SaveMapPlayerError, Toast.LENGTH_SHORT).show();
		    }
		}
		else if ( resultCode == 2002) {
			this.editorController.getMapEditor().loadMap(getApplicationContext(), bundle.getString("map"));
		}
		else if ( resultCode == 2003) {
			Intent intent = new Intent(MapEditorLayout.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}
}
