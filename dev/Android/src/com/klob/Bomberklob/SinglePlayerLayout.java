package com.klob.Bomberklob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.klob.Bomberklob.engine.ObjectsGallery;
import com.klob.Bomberklob.engine.Point;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class SinglePlayerLayout extends Activity implements View.OnClickListener {

	private ObjectsGallery bombsGallery;
	private LinearLayout singlePlayerControllerLayout;
	private RelativeLayout singlePlayerRelativeLayoutObjectsGallery, singlePlayerRelativeLayoutMenu;
	
	private Bundle bundle;

	private Button menu;
	
	private int menuSize = 50;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.singleplayer);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);	
		
		this.singlePlayerRelativeLayoutMenu = (RelativeLayout) findViewById(R.id.SinglePlayerRelativeLayoutMenu);
		this.singlePlayerRelativeLayoutMenu.setLayoutParams(new LinearLayout.LayoutParams( ResourcesManager.getHeight(), (int) (menuSize*ResourcesManager.getDpiPx())) );
		
		this.singlePlayerRelativeLayoutObjectsGallery = (RelativeLayout) findViewById(R.id.SinglePlayerRelativeLayoutObjectsGallery);
		this.singlePlayerRelativeLayoutObjectsGallery.setLayoutParams(new LinearLayout.LayoutParams( (int) (menuSize*ResourcesManager.getDpiPx()), (int) (ResourcesManager.getWidth()-(menuSize*ResourcesManager.getDpiPx())) ) );
		
		this.singlePlayerControllerLayout = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutEditorController);
		this.singlePlayerControllerLayout.setLayoutParams(new LinearLayout.LayoutParams( (int) (ResourcesManager.getHeight()-(menuSize*ResourcesManager.getDpiPx())), (int) (ResourcesManager.getWidth()-(menuSize*ResourcesManager.getDpiPx())) ) );

		this.bundle = getIntent().getExtras();
//        if(bundle.getString("map")!= null) {
//        	this.editorController.getMapEditor().loadMap(getApplicationContext(), bundle.getString("map"));
//        	this.editorController.update();
//        }
		
		this.bombsGallery = (ObjectsGallery) findViewById(R.id.SinglePlayerBombsGallery);
//		this.bombsGallery.(ResourcesManager.getBombs());
		this.bombsGallery.setSelectedItem("normal");
		this.bombsGallery.setRectangles(new Point(0,0));
		
		this.menu = (Button) findViewById(R.id.SinglePlayerButtonMenu);
		this.menu.setOnClickListener(this);
	}

	@Override
	protected void onStop() {
		Log.i("SinglePlayerLayout", "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy(){
		Log.i("SinglePlayerLayout", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onResume(){
		Log.i("SinglePlayerLayout", "onResume");
		super.onResume();
		bombsGallery.setLevel(1);
	}

	@Override
	protected void onPause(){
		Log.i("SinglePlayerLayout", "onPause");
		super.onPause();
	}  
	

	@Override
	public void onClick(View arg0) {
		if ( this.menu == arg0 ) {
			Intent intent = new Intent(SinglePlayerLayout.this, SinglePlayerMenu.class);
			startActivityForResult(intent, 1000);
		}
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if ( resultCode == 2000) {

		}
		else if ( resultCode == 2001) {

		}
		else if ( resultCode == 2002) {
			
		}
		else if ( resultCode == 2003) {
			Intent intent = new Intent(SinglePlayerLayout.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}
}
