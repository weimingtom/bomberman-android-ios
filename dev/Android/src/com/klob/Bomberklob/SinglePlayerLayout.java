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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.klob.Bomberklob.engine.GameControllerSingle;
import com.klob.Bomberklob.engine.ObjectsGallery;
import com.klob.Bomberklob.engine.Point;
import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class SinglePlayerLayout extends Activity implements View.OnClickListener {
	
	private GameControllerSingle gameControllerSingle;

	private ObjectsGallery bombsGallery;
	private LinearLayout singlePlayerControllerLayout;
	private RelativeLayout singlePlayerRelativeLayoutObjectsGallery, singlePlayerRelativeLayoutMenu;
	private FrameLayout singlePlayerFrameLayoutGame, singlePlayerFrameLayoutBombsGallery;
	
	private Bundle bundle;

	private Button menu, bomb;
	
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
        if( bundle.getString("map") != null) { //FIXME tout vérifier ?
        	this.gameControllerSingle = new GameControllerSingle(getApplicationContext(), bundle.getString("map"), bundle.getInt("enemies"), bundle.getString("gametype"), bundle.getBoolean("random"), bundle.getInt("difficulty"));
        }
        
        this.singlePlayerFrameLayoutGame = (FrameLayout) findViewById(R.id.SinglePlayerFrameLayoutGame);
        this.singlePlayerFrameLayoutGame.addView(this.gameControllerSingle);
		
		this.bombsGallery = new ObjectsGallery(getApplicationContext());
		this.bombsGallery.setItemsDisplayed(1);
		this.bombsGallery.setLevel(1);
		this.bombsGallery.addObjects(new Bomb("normal", false, 1, true, 1, 1, 0, ResourcesManager.getBombsAnimations().get("normal"), "idle"));
		this.bombsGallery.setSelectedItem("normal");
		this.bombsGallery.setRectangles(new Point(0,0));
		this.bombsGallery.update();
		
		this.singlePlayerFrameLayoutBombsGallery = (FrameLayout) findViewById(R.id.SinglePlayerFrameLayoutBombsGallery);
		this.singlePlayerFrameLayoutBombsGallery.addView(this.bombsGallery);
		
		this.menu = (Button) findViewById(R.id.SinglePlayerButtonMenu);
		this.menu.setOnClickListener(this);
		
		this.bomb = (Button) findViewById(R.id.SinglePlayerButtonBomb);
		this.bomb.setOnClickListener(this);
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
		else if ( this.bomb == arg0 ) {
			this.gameControllerSingle.pushBomb();
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
