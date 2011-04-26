package com.klob.Bomberklob.editor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.menus.Home;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resources.ObjectsGallery;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class EditorLayout extends Activity implements View.OnClickListener {

	private EditorController editorController;

	private ObjectsGallery objectsGallery, objectsGallery2;
	private LinearLayout editorControllerLayout, mapEditorLinearLayoutMenu;
	private RelativeLayout editorRelativeLayoutObjectsGallery, editorRelativeLayoutMenu, mapEditorRelativeLayoutGlobal;
	private FrameLayout mapEditorFrameLayoutEditorController, mapEditorFrameLayoutObjectsGallery, mapEditorFrameLayoutPlayersGallery;

	private Bundle bundle;

	private Button menu, reset, quit, save, resume;
	private ToggleButton mapEditorToggleButton;

	private int menuSize = 50;
	
	private Point point;
	private Objects object;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.editorlayout);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);	

		this.editorRelativeLayoutMenu = (RelativeLayout) findViewById(R.id.MapEditorRelativeLayoutToolsBar);
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
										
					switch (arg1.getAction()) {		
					case MotionEvent.ACTION_DOWN:
						object = ResourcesManager.getObjects().get(objectsGallery.getSelectedItem());

						if ( object == null ) {
							Hashtable<String, AnimationSequence> animations = ResourcesManager.getPlayersAnimations().get(objectsGallery2.getSelectedItem());
							if ( animations != null ) {
								object = new HumanPlayer(objectsGallery2.getSelectedItem(), ResourcesManager.getPlayersAnimations().get(objectsGallery2.getSelectedItem()), PlayerAnimations.IDLE, true, 1, false, 1, 1, 1, 1, 1, 1, 1, 0);
							}
						}

						if ( object != null ) {
							point = ResourcesManager.coToTile((int) arg1.getX(), (int) arg1.getY());
							editorController.addObjects(object.copy(), point);
						}
						break;
					case MotionEvent.ACTION_MOVE:
						Point point2 = ResourcesManager.coToTile((int) arg1.getX(), (int) arg1.getY());
						if ( point.x != point2.x || point.y != point2.y) {
							point = point2;
							editorController.addObjects(object.copy(), point);
						}
						break;
					case MotionEvent.ACTION_UP:
						point = null;
						object = null;
						break;
					}
					
					return true;
				}
			});
			this.editorController.update();
		}        
		this.mapEditorFrameLayoutEditorController =(FrameLayout) findViewById(R.id.MapEditorFrameLayoutEditorController);
		this.mapEditorFrameLayoutEditorController.addView(this.editorController);

		this.menu = (Button) findViewById(R.id.MapEditorButtonMenu);
		this.menu.setOnClickListener(this);

		this.mapEditorToggleButton = (ToggleButton) findViewById(R.id.MapEditorToggleButton);
		this.mapEditorToggleButton.setChecked(true);
		this.mapEditorToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
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
		this.objectsGallery.setLevel(1);
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
		this.objectsGallery2.addObjects(new HumanPlayer("white", ResourcesManager.getPlayersAnimations().get("white"), PlayerAnimations.IDLE, true, 1, false, 1, 1, 1, 1, 1, 1, 1, 0));
		this.objectsGallery2.addObjects(new HumanPlayer("blue", ResourcesManager.getPlayersAnimations().get("blue"), PlayerAnimations.IDLE, true, 1, false, 1, 1, 1, 1, 1, 1, 1, 0));
		this.objectsGallery2.addObjects(new HumanPlayer("black", ResourcesManager.getPlayersAnimations().get("black"), PlayerAnimations.IDLE, true, 1, false, 1, 1, 1, 1, 1, 1, 1, 0));
		this.objectsGallery2.addObjects(new HumanPlayer("red", ResourcesManager.getPlayersAnimations().get("red"), PlayerAnimations.IDLE, true, 1, false, 1, 1, 1, 1, 1, 1, 1, 0));
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

		this.resume = (Button) findViewById(R.id.MapEditorMenuResume);
		this.resume.setOnClickListener(this);

		this.reset = (Button) findViewById(R.id.MapEditorMenuReset);
		this.reset.setOnClickListener(this);

		this.quit = (Button) findViewById(R.id.MapEditorMenuQuit);
		this.quit.setOnClickListener(this);

		this.save = (Button) findViewById(R.id.MapEditorMenuSave);
		this.save.setOnClickListener(this);

		this.mapEditorLinearLayoutMenu = (LinearLayout) findViewById(R.id.MapEditorLinearLayoutMenu);

		this.mapEditorRelativeLayoutGlobal = (RelativeLayout) findViewById(R.id.MapEditorRelativeLayoutGlobal);
		this.mapEditorRelativeLayoutGlobal.removeView(findViewById(R.id.MapEditorLinearLayoutMenu));
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
			this.menu.setClickable(false);
			this.mapEditorToggleButton.setClickable(false);		
			this.editorController.setEnabled(false);
			this.objectsGallery.setEnabled(false);
			this.objectsGallery2.setEnabled(false);
			this.mapEditorRelativeLayoutGlobal.addView(this.mapEditorLinearLayoutMenu);
		}
		else if ( this.resume == arg0 ) {

			this.menu.setClickable(true);
			this.mapEditorToggleButton.setClickable(true);		
			this.editorController.setEnabled(true);
			this.objectsGallery.setEnabled(true);
			this.objectsGallery2.setEnabled(true);
			this.mapEditorRelativeLayoutGlobal.removeView(this.mapEditorLinearLayoutMenu);
		}
		else if ( this.save == arg0 ) {
			Bitmap bm = Bitmap.createBitmap(this.editorController.getWidth(), this.editorController.getHeight(), Bitmap.Config.ARGB_8888);

			if ( this.editorController.getMapEditor().getMap().saveMap() ) {
				Canvas pictureCanvas = new Canvas(bm);
				this.editorController.getMapEditor().getMap().groundsOnDraw(pictureCanvas, ResourcesManager.getSize());
				this.editorController.getMapEditor().onDraw(pictureCanvas, true);
				
				File f =  new File (getFilesDir().getAbsolutePath()+"/maps/"+bundle.getString("map")+"/"+bundle.getString("map")+".png");

				try {
					f.createNewFile();
					FileOutputStream fos = new FileOutputStream(f);
					bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();

				} catch(IOException ioe) {
					ioe.printStackTrace();
				}

				// FIXME Map non officielles 1 => 0
				Model.getSystem().getDatabase().newMap(bundle.getString("map"), Model.getUser().getPseudo(), 1);
				Intent intent = new Intent(EditorLayout.this, Home.class);
				startActivity(intent);
				this.finish();
			}
			else {
				Toast.makeText(EditorLayout.this, R.string.SaveMapPlayerError, Toast.LENGTH_SHORT).show();
			}
		}
		else if ( this.reset == arg0 ) {
			this.menu.setClickable(true);
			this.mapEditorToggleButton.setClickable(true);		
			this.editorController.setEnabled(true);
			this.mapEditorRelativeLayoutGlobal.removeView(this.mapEditorLinearLayoutMenu);
			this.editorController.getMapEditor().loadMap(bundle.getString("map"));
			this.editorController.update();
		}
		else if ( this.quit == arg0 ) {
			Intent intent = new Intent(EditorLayout.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}	
}
