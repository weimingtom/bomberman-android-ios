package com.klob.Bomberklob.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
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
import com.klob.Bomberklob.resources.ResourcesManager;

public class SinglePlayerLayout extends Activity implements View.OnClickListener {

	private GameControllerSingle gameControllerSingle;

	private ObjectsGallery bombsGallery;
	private LinearLayout singlePlayerControllerLayout, singlePlayerLinearLayoutMenu, singlePlayerLinearLayoutStart, singlePlayerLinearLayoutBonus1, singlePlayerLinearLayoutBonus2, singlePlayerLinearLayoutBonus3, singlePlayerLinearLayoutBonus4, singlePlayerLinearLayoutEnd, singlePlayerLinearLayoutMultiTouch;
	private RelativeLayout singlePlayerRelativeLayoutObjectsGallery, singlePlayerRelativeLayoutMenu;
	private FrameLayout singlePlayerFrameLayoutGame, singlePlayerFrameLayoutBombsGallery;

	private Bundle bundle;

	private Button menu, options, quit, quit2, restart, restart2, resume;
	private ImageButton bomb;
	private ImageView singlePlayerLinearLayoutStartImage , singlePlayerLinearLayoutEndImageView;

	private int menuSize = 50;

	private ImageView[] imageView = new ImageView[4];

	private int timeS;
	private int timeM = -1;
	private boolean timeBoolean = true;
	private Thread timeThread;	
	private TextView timeTextView, bombpower, bombnumber, playerspeed, playerlife;
	private Handler handler;

	private MediaPlayer mp;

	private Rect menuRect = new Rect(), bombRect = new Rect(), gameRect = new Rect();
	private int pointerId = -1, nbPointer = -1;
	private int[] t = new int[2];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.singleplayerlayout);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);	

		this.singlePlayerLinearLayoutMultiTouch = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutMultiTouch);
		this.singlePlayerLinearLayoutMultiTouch.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				if ( nbPointer != arg1.getPointerCount() ) {
					if ( arg1.findPointerIndex(pointerId) == -1 || !gameRect.contains((int) arg1.getX(arg1.findPointerIndex(pointerId)),(int) arg1.getY(arg1.findPointerIndex(pointerId)))) {
						gameControllerSingle.onTouchEvent(MotionEvent.ACTION_UP, 0, 0);
						pointerId = -1;	
						nbPointer = -1;
					}
					else {
						nbPointer = arg1.getPointerCount();
					}
				}

				if ( arg1.getAction() == MotionEvent.ACTION_UP ) {
					gameControllerSingle.onTouchEvent(MotionEvent.ACTION_UP, 0, 0);
					pointerId = -1;				
				}
				else {
					for (int i = 0 ; i < arg1.getPointerCount() ; i++ ) {
	
						if ( menuRect.contains((int) arg1.getX(i),(int) arg1.getY(i)))	{
							onClick(menu);
						}
						else if ( bombRect.contains((int) arg1.getX(i),(int) arg1.getY(i)))	{
							onClick(bomb);
						}
						else if ( gameRect.contains((int) arg1.getX(i),(int) arg1.getY(i)))	{
							if ( arg1.findPointerIndex(pointerId) == i ) {
								gameControllerSingle.onTouchEvent(MotionEvent.ACTION_MOVE, (int) arg1.getX(i), (int) arg1.getY(i));
							}
							else {
								pointerId = arg1.getPointerId(i);
								nbPointer = arg1.getPointerCount();
								gameControllerSingle.onTouchEvent(MotionEvent.ACTION_DOWN, (int) arg1.getX(i), (int) arg1.getY(i));
							}
						}
					}
				}
				return true;
			}
		});

		this.singlePlayerRelativeLayoutMenu = (RelativeLayout) findViewById(R.id.SinglePlayerRelativeLayoutMenu);
		this.singlePlayerRelativeLayoutMenu.setLayoutParams(new LinearLayout.LayoutParams( ResourcesManager.getWidth(), (int) (menuSize*ResourcesManager.getDpiPx())) );

		this.singlePlayerRelativeLayoutObjectsGallery = (RelativeLayout) findViewById(R.id.SinglePlayerRelativeLayoutObjectsGallery);
		this.singlePlayerRelativeLayoutObjectsGallery.setLayoutParams(new LinearLayout.LayoutParams( (int) (menuSize*ResourcesManager.getDpiPx()), (int) (ResourcesManager.getHeight()-(menuSize*ResourcesManager.getDpiPx())) ) );

		this.singlePlayerFrameLayoutGame = (FrameLayout) findViewById(R.id.SinglePlayerFrameLayoutGame);

		this.singlePlayerControllerLayout = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutEditorController);
		this.singlePlayerControllerLayout.setLayoutParams(new LinearLayout.LayoutParams( (int) (ResourcesManager.getWidth()-(menuSize*ResourcesManager.getDpiPx())), (int) (ResourcesManager.getHeight()-(menuSize*ResourcesManager.getDpiPx())) ) );

		this.singlePlayerFrameLayoutBombsGallery = (FrameLayout) findViewById(R.id.SinglePlayerFrameLayoutBombsGallery);

		this.menu = (Button) findViewById(R.id.SinglePlayerButtonMenu);
		this.bomb = (ImageButton) findViewById(R.id.SinglePlayerButtonBomb);

		this.resume = (Button) findViewById(R.id.SinglePlayerMenuResume);
		this.resume.setOnClickListener(this);

		this.options = (Button) findViewById(R.id.SinglePlayerMenuOptions);
		this.options.setOnClickListener(this);

		this.quit = (Button) findViewById(R.id.SinglePlayerMenuQuit);
		this.quit.setOnClickListener(this);

		this.restart = (Button) findViewById(R.id.SinglePlayerMenuRestart);
		this.restart.setOnClickListener(this);

		this.quit2 = (Button) findViewById(R.id.SinglePlayerLinearLayoutEndButtonLeave);
		this.quit2.setOnClickListener(this);

		this.restart2 = (Button) findViewById(R.id.SinglePlayerLinearLayoutEndButtonRestart);
		this.restart2.setOnClickListener(this);

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
					if ( timeM > 0 ) {
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
					}
					if ( timeM == 0 && timeS == 0 ) {
						setTimeThreadRunning(false);
						draw();
					}
				}
				else if ( msg.what == 1 ) {
					singlePlayerLinearLayoutStartImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ready));
				}
				else if ( msg.what == 2 ) {
					singlePlayerLinearLayoutStartImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.go));
				}
				else if ( msg.what == 3 ) {
					singlePlayerLinearLayoutStartImage.setImageBitmap(null);
					singlePlayerLinearLayoutStart.setVisibility(View.INVISIBLE);
					mp = MediaPlayer.create(getApplicationContext(), R.raw.battle_mode);
					mp.setLooping(true);
					mp.start();
					resumeGame();
					
					menu.getLocationOnScreen(t);
					menuRect = new Rect(t[0], t[1], t[0]+menu.getWidth(), t[1]+menu.getHeight());
					System.out.println("MENU : " + t[0] + " " + t[1] +" "+ (t[0]+menu.getWidth()) + " " + (t[1]+menu.getHeight()));
					bomb.getLocationOnScreen(t);
					bombRect = new Rect(t[0], t[1], t[0]+bomb.getWidth(), t[1]+bomb.getHeight());
					System.out.println("BOMB : " + t[0] + " " + t[1] +" "+ (t[0]+bomb.getWidth()) + " " + (t[1]+bomb.getHeight()));
					singlePlayerControllerLayout.getLocationOnScreen(t);
					gameRect = new Rect(t[0], t[1],  (int) (t[0]+(ResourcesManager.getWidth()-(menuSize*ResourcesManager.getDpiPx()))), (int) (t[1]+(ResourcesManager.getHeight()-(menuSize*ResourcesManager.getDpiPx()))) ) ;		
					System.out.println("Controller : " + t[0] + " " + t[1] + " " +  (int) (t[0]+(ResourcesManager.getWidth()-(menuSize*ResourcesManager.getDpiPx()))) + " " + (int) (t[1]+(ResourcesManager.getHeight()-(menuSize*ResourcesManager.getDpiPx()))));

				
				}
				else if ( msg.what == 4 ) { 
					updatePlayersStats();					
				}
				else if ( msg.what == 5 ) { //Lose
					lose();					
				}
				else if ( msg.what == 6 ) { //Win
					win();				
				}
			}
		};

		this.singlePlayerLinearLayoutEnd  = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutEnd);
		this.singlePlayerLinearLayoutMenu = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutMenu);
		this.singlePlayerLinearLayoutStart = (LinearLayout) findViewById(R.id.SinglePlayerLinearLayoutStart);
		this.singlePlayerLinearLayoutStartImage = (ImageView) findViewById(R.id.SinglePlayerLinearLayoutStartImage);
		this.singlePlayerLinearLayoutEndImageView = (ImageView) findViewById(R.id.SinglePlayerLinearLayoutEndImageView);

		this.singlePlayerLinearLayoutEndImageView.setImageBitmap(null);

		this.singlePlayerLinearLayoutEnd.setVisibility(View.INVISIBLE);
		this.singlePlayerLinearLayoutMenu.setVisibility(View.INVISIBLE);
		this.singlePlayerLinearLayoutMultiTouch.setVisibility(View.INVISIBLE);

		this.initGame();		
	}

	@Override
	protected void onStop() {
		Log.i("SinglePlayerLayout", "onStop");
		super.onStop();
		mp.stop();
	}

	@Override
	protected void onDestroy(){
		Log.i("SinglePlayerLayout", "onDestroy");
		super.onDestroy();
		mp.stop();
	}

	@Override
	protected void onResume(){
		Log.i("SinglePlayerLayout", "onResume");
		super.onResume();
		mp.start();
	}

	@Override
	protected void onPause(){
		Log.i("SinglePlayerLayout", "onPause");
		super.onPause();
		this.pauseGame();
		mp.pause();
		this.singlePlayerLinearLayoutMenu.setVisibility(View.VISIBLE);
	} 

	public void onClick(View arg0) {

		if ( this.bomb == arg0 ) {
			this.gameControllerSingle.pushBomb();
		}
		else if ( this.menu == arg0 ) {
			this.pauseGame();
			mp.pause();
			this.singlePlayerLinearLayoutMenu.setVisibility(View.VISIBLE);
		}
		else if ( arg0 == this.resume) {
			mp.start();
			this.singlePlayerLinearLayoutMenu.setVisibility(View.INVISIBLE);
			this.resumeGame();
		}
		else if ( arg0 == this.options) {

		}
		else if ( arg0 == this.restart ) {
			mp.stop();
			this.initGame();
			this.singlePlayerLinearLayoutMenu.setVisibility(View.INVISIBLE);
		}
		else if ( arg0 == this.quit) {
			mp.stop();
			this.singlePlayerLinearLayoutMenu.setVisibility(View.INVISIBLE);
			Intent intent = new Intent(SinglePlayerLayout.this, Home.class);
			startActivity(intent);
			this.finish();
		}
		else if ( arg0 == this.quit2) {
			mp.stop();
			this.singlePlayerLinearLayoutEnd.setVisibility(View.INVISIBLE);

			Intent intent = new Intent(SinglePlayerLayout.this, Home.class);
			startActivity(intent);
			this.finish();
		}
		else if ( arg0 == this.restart2 ) {
			mp.stop();
			this.initGame();
			this.singlePlayerLinearLayoutEnd.setVisibility(View.INVISIBLE);
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
		this.singlePlayerLinearLayoutStart.setVisibility(View.VISIBLE);

		mp = MediaPlayer.create(getApplicationContext(), R.raw.battle_start);
		mp.start();

		new Thread() {

			@Override
			public void run() {
				Log.i("Start Thread","Thread started");

				try {
					handler.sendMessage(handler.obtainMessage(1));
					sleep(2000);
					handler.sendMessage(handler.obtainMessage(2));
					sleep(2000);

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
		this.setTimeThreadRunning(false);
		this.singlePlayerLinearLayoutMultiTouch.setVisibility(View.INVISIBLE);
	}

	public void resumeGame() {
		this.bombsGallery.setEnabled(true);
		this.gameControllerSingle.setEnabled(true);
		this.setTimeThreadRunning(true);
		this.singlePlayerLinearLayoutMultiTouch.setVisibility(View.VISIBLE);
	}

	public void updatePlayersStats() {
		Player[] p = gameControllerSingle.getEngine().getSingle().getPlayers();
		int j = 0;

		bombpower.setText(String.valueOf(p[0].getPowerExplosion()));
		bombnumber.setText(String.valueOf(p[0].getBombNumber()));
		playerlife.setText(String.valueOf(p[0].getLife()));
		playerspeed.setText(String.valueOf(p[0].getSpeed()));

		/* FIXME noms des images et test pour ne pas avoir a afficher la même image */
		for (int i = 0 ; i < p.length ; i++ ) {
			if (p[i] != null) {
				if (!p[i].isDestructible() || p[i].getCurrentAnimation().equals(PlayerAnimations.TOUCHED.getLabel())) {
					this.imageView[i].setBackgroundDrawable(new BitmapDrawable(ResourcesManager.getBitmaps().get(p[i].getImageName()+"touched")));
				}
				else if (p[i].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel())) {
					this.imageView[i].setBackgroundDrawable(new BitmapDrawable(ResourcesManager.getBitmaps().get(p[i].getImageName()+p[i].getCurrentAnimation())));
					if ( i == 0 ) {
						handler.sendMessage(handler.obtainMessage(5));
					}
					else {
						j++;
					}
				}
				else {
					this.imageView[i].setBackgroundDrawable(new BitmapDrawable(ResourcesManager.getBitmaps().get(p[i].getImageName()+"idle")));
				}
			}
		}

		if ( j == bundle.getInt("enemies") ) {
			handler.sendMessage(handler.obtainMessage(6));
		}
	}

	private void win() {
		pauseGame();
		this.singlePlayerLinearLayoutEndImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.winner));
		this.singlePlayerLinearLayoutEnd.setVisibility(View.VISIBLE);
	}

	private void lose() {
		pauseGame();
		this.singlePlayerLinearLayoutEndImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.loser));
		this.singlePlayerLinearLayoutEnd.setVisibility(View.VISIBLE);
	}

	private void draw() {
		pauseGame();
		this.singlePlayerLinearLayoutEndImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.draw));
		this.singlePlayerLinearLayoutEnd.setVisibility(View.VISIBLE);
	}
}
