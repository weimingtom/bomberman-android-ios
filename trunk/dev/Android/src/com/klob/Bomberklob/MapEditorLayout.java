package com.klob.Bomberklob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

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
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.klob.Bomberklob.engine.EditorController;
import com.klob.Bomberklob.engine.ObjectsGallery;
import com.klob.Bomberklob.engine.Point;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class MapEditorLayout extends Activity implements View.OnClickListener {

	private EditorController editorController;

	private ObjectsGallery objectsGallery, objectsGallery2;
	private LinearLayout editorControllerLayout;
	private RelativeLayout editorRelativeLayoutObjectsGallery, editorRelativeLayoutMenu;
	private FrameLayout mapEditorFrameLayoutEditorController, mapEditorFrameLayoutObjectsGallery, mapEditorFrameLayoutPlayersGallery;
	
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
		
		
		this.bundle = getIntent().getExtras();
        if(bundle.getString("map")!= null) {
    		this.editorController = new EditorController(getApplicationContext(), bundle.getString("map"));
     		this.editorController.setOnTouchListener(new OnTouchListener() {
    			
    			@Override
    			public boolean onTouch(View arg0, MotionEvent arg1) {
    				Objects object = ResourcesManager.getObjects().get(objectsGallery.getSelectedItem());
    				
    				if ( object == null ) {
    					Hashtable<String, AnimationSequence> animations = ResourcesManager.getPlayersAnimations().get(objectsGallery2.getSelectedItem());
    					if ( animations != null ) {
    						object = new HumanPlayer(objectsGallery2.getSelectedItem(), animations, "idle", 1, 1, 1, 1, 1, 1, 0);
    					}
    				}
    				
    				if ( object != null ) {
    					editorController.addObjects(object.copy(), (int) arg1.getX(), (int) arg1.getY());
    				}
    				return false;
    			}
    		});
        	this.editorController.update();
        }        
		this.mapEditorFrameLayoutEditorController =(FrameLayout) findViewById(R.id.MapEditorFrameLayoutEditorController);
		this.mapEditorFrameLayoutEditorController.addView(this.editorController);
		
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
				
				objectsGallery.setSelectedItem(null);
				objectsGallery.setRectangles(new Point(-10,-10));
				objectsGallery.update();
				
				objectsGallery2.setSelectedItem(null);
				objectsGallery2.setRectangles(new Point(-10,-10));
				objectsGallery2.update();
				
				editorController.update();
			}
		});
		
		this.objectsGallery = new ObjectsGallery(getApplicationContext());
		this.objectsGallery.loadObjects(ResourcesManager.getObjects());
		this.objectsGallery.setItemsDisplayed(3);
		this.objectsGallery.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {	
				objectsGallery2.setSelectedItem(null);
				objectsGallery2.setRectangles(new Point(-1,-1));
				objectsGallery2.update();
				return false;
			}
		});
		this.objectsGallery.update();
		
		this.mapEditorFrameLayoutObjectsGallery = (FrameLayout) findViewById(R.id.MapEditorFrameLayoutObjectsGallery);
		this.mapEditorFrameLayoutObjectsGallery.addView(this.objectsGallery);
		
		this.objectsGallery2 = new ObjectsGallery(getApplicationContext(), 4, null, 30, 15, 1, false);
		this.objectsGallery2.addObjects(new HumanPlayer("white", ResourcesManager.getPlayersAnimations().get("white"), "idle",1, 1, 1, 1, 1, 1, 0));
		this.objectsGallery2.addObjects(new HumanPlayer("blue", ResourcesManager.getPlayersAnimations().get("blue"), "idle",1, 1, 1, 1, 1, 1, 0));
		this.objectsGallery2.addObjects(new HumanPlayer("black", ResourcesManager.getPlayersAnimations().get("black"), "idle",1, 1, 1, 1, 1, 1, 0));
		this.objectsGallery2.addObjects(new HumanPlayer("red", ResourcesManager.getPlayersAnimations().get("red"), "idle",1, 1, 1, 1, 1, 1, 0));
		this.objectsGallery2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				objectsGallery.setSelectedItem(null);
				objectsGallery.setRectangles(new Point(-1,-1));
				objectsGallery.update();
				return false;
			}
		});
		this.objectsGallery2.update();
		
		this.mapEditorFrameLayoutPlayersGallery = (FrameLayout) findViewById(R.id.MapEditorFrameLayoutPlayersGallery);
		this.mapEditorFrameLayoutPlayersGallery.addView(this.objectsGallery2);
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
		
		checkBox.setChecked(true);
		
		editorController.getEditorView().setLevel(true);
		editorController.update();
		
		objectsGallery.setLevel(1);
		objectsGallery.setSelectedItem(null);
		objectsGallery.setRectangles(new Point(-1,-1));
		objectsGallery.update();
		
		objectsGallery2.setLevel(1);
		objectsGallery2.setSelectedItem(null);
		objectsGallery2.setRectangles(new Point(-1,-1));
		objectsGallery2.update();
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

		}
		else if ( resultCode == 2001) {

		    Bitmap bm = Bitmap.createBitmap(this.editorController.getWidth(), this.editorController.getHeight(), Bitmap.Config.ARGB_8888);
		    
		    if ( this.editorController.getMapEditor().getMap().saveMap() ) {
			    Canvas pictureCanvas = new Canvas(bm);
			    this.editorController.getMapEditor().getMap().groundsOnDraw(pictureCanvas, ResourcesManager.getSize());
			    this.editorController.getMapEditor().onDraw(pictureCanvas, true);
	
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
			this.editorController.getMapEditor().loadMap(bundle.getString("map"));
		}
		else if ( resultCode == 2003) {
			Intent intent = new Intent(MapEditorLayout.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}
}
