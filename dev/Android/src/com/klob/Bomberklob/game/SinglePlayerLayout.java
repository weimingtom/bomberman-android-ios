package com.klob.Bomberklob.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.menus.Home;
import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.resources.ObjectsGallery;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class SinglePlayerLayout extends Activity implements View.OnClickListener {

	private GameControllerSingle gameControllerSingle;

	private ObjectsGallery bombsGallery;
	private LinearLayout singlePlayerControllerLayout, singlePlayerLinearLayoutMenu;
	private RelativeLayout singlePlayerRelativeLayoutObjectsGallery, singlePlayerRelativeLayoutMenu, singlePlayerRelativeLayoutGlobal;
	private FrameLayout singlePlayerFrameLayoutGame, singlePlayerFrameLayoutBombsGallery;

	private Bundle bundle;

	private Button menu, options, quit, restart, resume;
	private ImageButton bomb;

	private int menuSize = 50;

	private int timeS;
	private int timeM = 3; //FIXME
	private boolean timeBoolean = true;
	private Thread timeThread;	
	private TextView timeTextView;
	private Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.singleplayerlayout);

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

		this.bomb = (ImageButton) findViewById(R.id.SinglePlayerButtonBomb);
		this.bomb.setOnClickListener(this);
		
        this.resume = (Button) findViewById(R.id.SinglePlayerMenuResume);
        this.resume.setOnClickListener(this);
        
        this.options = (Button) findViewById(R.id.SinglePlayerMenuOptions);
        this.options.setOnClickListener(this);
        
        this.quit = (Button) findViewById(R.id.SinglePlayerMenuQuit);
        this.quit.setOnClickListener(this);
        
        this.restart = (Button) findViewById(R.id.SinglePlayerMenuRestart);
        this.restart.setOnClickListener(this);

		this.timeTextView = (TextView) findViewById(R.id.GameTextTime);
		this.timeTextView.setText(timeM+":00");
		this.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if ( msg.what == 0 ) {
					timeS--;
					if ( timeS == -1 ) {
						timeM--;
						timeS = 59;
					}
					if ( timeS > 9 ) {
						timeTextView.setText(timeM+":"+timeS);
					}
					else {
						timeTextView.setText(timeM+":0"+timeS);
					}
					if ( timeM == 0 ) {
						//FIXME FIN DU JEU
					}
				}
			}
		};
		
		this.singlePlayerLinearLayoutMenu = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutMenu);

		this.singlePlayerRelativeLayoutGlobal = (RelativeLayout) findViewById(R.id.SinglePlayerRelativeLayoutGlobal);
		this.singlePlayerRelativeLayoutGlobal.removeView(findViewById(R.id.SinglePlayerLinearLayoutMenu));
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
		this.setTimeThreadRunning(true);

	}

	@Override
	protected void onPause(){
		Log.i("SinglePlayerLayout", "onPause");
		super.onPause();
		this.setTimeThreadRunning(false);
	}  


	@Override
	public void onClick(View arg0) {
		
		if ( this.bomb == arg0 ) {
			this.gameControllerSingle.pushBomb();
		}
		else if ( this.menu == arg0 ) {
			this.bombsGallery.setEnabled(false);
			this.gameControllerSingle.setEnabled(false);
			this.bomb.setClickable(false);
			this.menu.setClickable(false);
			this.singlePlayerRelativeLayoutGlobal.addView(this.singlePlayerLinearLayoutMenu);
			this.setTimeThreadRunning(false);
			this.gameControllerSingle.getEngine().setBombThreadRunning(false);
		}
		else if ( arg0 == this.resume) {
			this.bombsGallery.setEnabled(true);
			this.gameControllerSingle.setEnabled(true);
			this.bomb.setClickable(true);
			this.menu.setClickable(true);
			this.singlePlayerRelativeLayoutGlobal.removeView(this.singlePlayerLinearLayoutMenu);
			this.setTimeThreadRunning(true);
			this.gameControllerSingle.getEngine().setBombThreadRunning(true);
		}
		else if ( arg0 == this.options) {

		}
		else if ( arg0 == this.restart) {

		}
		else if ( arg0 == this.quit) {
			Intent intent = new Intent(SinglePlayerLayout.this, Home.class);
			startActivity(intent);
			this.finish();
		}
	}

	public void setTimeThreadRunning(boolean bombBoolean2) {
		this.timeBoolean = bombBoolean2;
		if ( this.timeBoolean && (this.timeThread == null || this.timeThread.getState() == Thread.State.TERMINATED)) {
			this.timeThread = new Thread() {
				@Override
				public void run() {
					Log.i("Time Thread","Thread started");
					while (timeBoolean) {
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}		
						handler.sendMessage(handler.obtainMessage(0));
					}
					Log.i("Time Thread","Thread done");
				};
			};
			this.timeThread.start();
		}
	}
}