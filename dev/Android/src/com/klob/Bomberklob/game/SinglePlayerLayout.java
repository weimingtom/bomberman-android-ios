package com.klob.Bomberklob.game;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.menus.Home;
import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.ObjectsAnimations;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resources.ObjectsGallery;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class SinglePlayerLayout extends Activity implements View.OnClickListener {

	private GameControllerSingle gameControllerSingle;

	private ObjectsGallery bombsGallery;
	private LinearLayout singlePlayerControllerLayout, singlePlayerLinearLayoutMenu, singlePlayerLinearLayoutStart, singlePlayerLinearLayoutBonus1, singlePlayerLinearLayoutBonus2, singlePlayerLinearLayoutBonus3, singlePlayerLinearLayoutBonus4;
	private RelativeLayout singlePlayerRelativeLayoutObjectsGallery, singlePlayerRelativeLayoutMenu, singlePlayerRelativeLayoutGlobal;
	private FrameLayout singlePlayerFrameLayoutGame, singlePlayerFrameLayoutBombsGallery;

	private Bundle bundle;

	private Button menu, options, quit, restart, resume;
	private ImageButton bomb;
	private ImageView singlePlayerLinearLayoutStartImage;

	private int menuSize = 50;
	
	private ImageView[] imageView = new ImageView[4];

	private int timeS;
	private int timeM;
	private boolean timeBoolean = true;
	private Thread timeThread;	
	private TextView timeTextView, bombpower, bombnumber, playerspeed, playerlife;
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

		this.singlePlayerFrameLayoutGame = (FrameLayout) findViewById(R.id.SinglePlayerFrameLayoutGame);

		this.singlePlayerControllerLayout = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutEditorController);
		this.singlePlayerControllerLayout.setLayoutParams(new LinearLayout.LayoutParams( (int) (ResourcesManager.getHeight()-(menuSize*ResourcesManager.getDpiPx())), (int) (ResourcesManager.getWidth()-(menuSize*ResourcesManager.getDpiPx())) ) );
		this.singlePlayerControllerLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				gameControllerSingle.onTouchEvent(arg1);
				return true;
			}
		});	

		this.singlePlayerFrameLayoutBombsGallery = (FrameLayout) findViewById(R.id.SinglePlayerFrameLayoutBombsGallery);

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
		
		
		this.singlePlayerLinearLayoutBonus1 = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutBonus1);
		this.singlePlayerLinearLayoutBonus1.setLayoutParams(new LinearLayout.LayoutParams( (int) ((menuSize-20)*ResourcesManager.getDpiPx()) , (int) ((menuSize-20)*ResourcesManager.getDpiPx()) ) );
		
		this.singlePlayerLinearLayoutBonus2 = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutBonus2);
		this.singlePlayerLinearLayoutBonus2.setLayoutParams(new LinearLayout.LayoutParams( (int) ((menuSize-20)*ResourcesManager.getDpiPx()) , (int) ((menuSize-20)*ResourcesManager.getDpiPx()) ) );
		
		this.singlePlayerLinearLayoutBonus3 = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutBonus3);
		this.singlePlayerLinearLayoutBonus3.setLayoutParams(new LinearLayout.LayoutParams( (int) ((menuSize-20)*ResourcesManager.getDpiPx()) , (int) ((menuSize-20)*ResourcesManager.getDpiPx()) ) );
		
		this.singlePlayerLinearLayoutBonus4 = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutBonus4);
		this.singlePlayerLinearLayoutBonus4.setLayoutParams(new LinearLayout.LayoutParams( (int) ((menuSize-20)*ResourcesManager.getDpiPx()) , (int) ((menuSize-20)*ResourcesManager.getDpiPx()) ) );
		
		
		this.imageView[0] = (ImageView) findViewById(R.id.SinglePlayerLayoutImageViewPlayer0);
		this.imageView[0].setLayoutParams(new LinearLayout.LayoutParams( (int) ((menuSize-20)*ResourcesManager.getDpiPx()) , (int) ((menuSize-20)*ResourcesManager.getDpiPx()) ) );
		
		this.imageView[1] = (ImageView) findViewById(R.id.SinglePlayerLayoutImageViewPlayer1);
		this.imageView[1].setLayoutParams(new LinearLayout.LayoutParams( (int) ((menuSize-20)*ResourcesManager.getDpiPx()) , (int) ((menuSize-20)*ResourcesManager.getDpiPx()) ) );
		
		this.imageView[2] = (ImageView) findViewById(R.id.SinglePlayerLayoutImageViewPlayer2);
		this.imageView[2].setLayoutParams(new LinearLayout.LayoutParams( (int) ((menuSize-20)*ResourcesManager.getDpiPx()) , (int) ((menuSize-20)*ResourcesManager.getDpiPx()) ) );
		
		this.imageView[3] = (ImageView) findViewById(R.id.SinglePlayerLayoutImageViewPlayer3);
		this.imageView[3].setLayoutParams(new LinearLayout.LayoutParams( (int) ((menuSize-20)*ResourcesManager.getDpiPx()) , (int) ((menuSize-20)*ResourcesManager.getDpiPx()) ) );

		this.bombpower = (TextView) findViewById(R.id.SinglePlayerTextViewBombPower);
		this.bombpower.setTextColor(Color.WHITE);
		this.bombnumber = (TextView) findViewById(R.id.SinglePlayerTextViewBombNumber);
		this.bombnumber.setTextColor(Color.WHITE);
		this.playerlife = (TextView) findViewById(R.id.SinglePlayerTextViewPlayerLife);
		this.playerlife.setTextColor(Color.WHITE);
		this.playerspeed = (TextView) findViewById(R.id.SinglePlayerTextViewPlayerSpeed);
		this.playerspeed.setTextColor(Color.WHITE);


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
				else if ( msg.what == 1 ) {
					singlePlayerLinearLayoutStartImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ready));
				}
				else if ( msg.what == 2 ) {
					singlePlayerLinearLayoutStartImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.go));
				}
				else if ( msg.what == 3 ) {
					singlePlayerRelativeLayoutGlobal.removeView(singlePlayerLinearLayoutStart);
					resumeGame();
				}
				else if ( msg.what == 4 ) {
					//FIXME BEUUUUUUUUUUUUUUUUUUUURK =D
					updatePlayersStats();					
				}
			}
		};

		this.singlePlayerLinearLayoutMenu = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutMenu);
		this.singlePlayerLinearLayoutStart = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutStart);
		this.singlePlayerLinearLayoutStartImage = (ImageView) findViewById(R.id.SinglePlayerLinearLayoutStartImage);

		this.singlePlayerRelativeLayoutGlobal = (RelativeLayout) findViewById(R.id.SinglePlayerRelativeLayoutGlobal);
		this.singlePlayerRelativeLayoutGlobal.removeView(findViewById(R.id.SinglePlayerLinearLayoutMenu));
		this.singlePlayerRelativeLayoutGlobal.removeView(findViewById(R.id.SinglePlayerLinearLayoutStart));

		this.initGame();
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
		this.pauseGame();
		this.singlePlayerRelativeLayoutGlobal.addView(this.singlePlayerLinearLayoutMenu);
	} 

	@Override
	public void onClick(View arg0) {

		if ( this.bomb == arg0 ) {
			this.gameControllerSingle.pushBomb();
		}
		else if ( this.menu == arg0 ) {
			this.pauseGame();
			this.singlePlayerRelativeLayoutGlobal.addView(this.singlePlayerLinearLayoutMenu);
		}
		else if ( arg0 == this.resume) {
			this.singlePlayerRelativeLayoutGlobal.removeView(this.singlePlayerLinearLayoutMenu);
			this.resumeGame();
		}
		else if ( arg0 == this.options) {

		}
		else if ( arg0 == this.restart) {
			this.initGame();
			this.singlePlayerRelativeLayoutGlobal.removeView(this.singlePlayerLinearLayoutMenu);
		}
		else if ( arg0 == this.quit) {
			this.singlePlayerRelativeLayoutGlobal.removeView(this.singlePlayerLinearLayoutMenu);
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
							sleep(500);
							handler.sendMessage(handler.obtainMessage(4));
							sleep(500);
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

	public void initGame() {

		this.bundle = getIntent().getExtras();

		if( bundle.getString("map") != null) {
			int i = bundle.getInt("time");

			if ( i == 0 ) {
				this.timeM = 1;
			}
			else if ( i == 1 ) {
				this.timeM = 2;
			}
			else if ( i == 2 ) {
				this.timeM = 3;
			}
			else if ( i == 3 ) {
				this.timeM = 5;
			}
			this.timeS = 0;

			if ( this.bombsGallery != null ) {
				this.singlePlayerFrameLayoutBombsGallery.removeView(this.bombsGallery);
				this.bombsGallery = null;
			}
			this.bombsGallery = new ObjectsGallery(getApplicationContext());
			this.bombsGallery.setItemsDisplayed(1);
			this.bombsGallery.setLevel(1);
			this.bombsGallery.addObjects(new Bomb("normal", ResourcesManager.getBombsAnimations().get("normal"), ObjectsAnimations.IDLE, true, 1, false, 1, 1, null));
			this.bombsGallery.setSelectedItem("normal");
			this.bombsGallery.setRectangles(new Point(0,0));
			this.bombsGallery.update();
			this.singlePlayerFrameLayoutBombsGallery.addView(this.bombsGallery);

			if ( this.gameControllerSingle == null ) {
				this.gameControllerSingle = new GameControllerSingle(getApplicationContext(), bundle.getString("map"), bundle.getInt("enemies"), bundle.getString("gametype"), bundle.getBoolean("random"), bundle.getInt("difficulty"));
				this.singlePlayerFrameLayoutGame.addView(this.gameControllerSingle);
			}
			else {
				this.gameControllerSingle.restartGame();
			}

			this.timeTextView = (TextView) findViewById(R.id.GameTextTime);
			this.timeTextView.setText(timeM+":00");
			
			updatePlayersStats();

			startGame();
		}
		else {
			// FIXME MapNotFoundException
		}
	}

	public void startGame() {		
		
		pauseGame();
		this.singlePlayerRelativeLayoutGlobal.addView(this.singlePlayerLinearLayoutStart);

		new Thread() {

			@Override
			public void run() {
				Log.i("Start Thread","Thread started");
				
				try {
					sleep(2000);
					handler.sendMessage(handler.obtainMessage(2));
					sleep(2000);
					handler.sendMessage(handler.obtainMessage(1));
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendMessage(handler.obtainMessage(3));
				Log.i("Start Thread","Thread done");
			};
		}.start();
	}

	public void pauseGame() {
		this.bombsGallery.setEnabled(false);
		this.gameControllerSingle.setEnabled(false);
		this.bomb.setClickable(false);
		this.menu.setClickable(false);
		this.setTimeThreadRunning(false);
		//FIXME Stopper IA
	}

	public void resumeGame() {
		this.bombsGallery.setEnabled(true);
		this.gameControllerSingle.setEnabled(true);
		this.bomb.setClickable(true);
		this.menu.setClickable(true);
		this.setTimeThreadRunning(true);
		//FIXME Lancer IA
	}
	
	public void updatePlayersStats() {
		Player[] p = gameControllerSingle.getEngine().getSingle().getPlayers();
		
		bombpower.setText(String.valueOf(p[0].getPowerExplosion()));
		bombnumber.setText(String.valueOf(p[0].getBombNumber()));
		playerlife.setText(String.valueOf(p[0].getLife()));
		playerspeed.setText(String.valueOf(p[0].getSpeed()));
		
		for (int i = 0 ; i < p.length ; i++ ) {
			if (p[i] != null) {
				//FIXME Récupérer le score
				if (!p[i].isDestructible() || p[i].getCurrentAnimation().equals(PlayerAnimations.TOUCHED.getLabel())) {
					this.imageView[i].setBackgroundDrawable(new BitmapDrawable(ResourcesManager.getBitmaps().get(p[i].getImageName()+"touched")));
				}
				else if (p[i].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel())) {
					this.imageView[i].setBackgroundDrawable(new BitmapDrawable(ResourcesManager.getBitmaps().get(p[i].getImageName()+p[i].getCurrentAnimation())));
				}
				else {
					this.imageView[i].setBackgroundDrawable(new BitmapDrawable(ResourcesManager.getBitmaps().get(p[i].getImageName()+"idle")));
				}
			}
		}
	}
}
