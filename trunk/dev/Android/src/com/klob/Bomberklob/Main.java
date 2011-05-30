package com.klob.Bomberklob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.klob.Bomberklob.editor.EditorMap;
import com.klob.Bomberklob.menus.Home;
import com.klob.Bomberklob.menus.NewAccountOffline;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class Main extends Activity {

	private Thread mainthread;
	private Handler handler;
	private Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		this.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if ( msg.what == 0 ) {
					if ( intent != null ) {
						startActivity(intent);
						finish();	
					}
				}
			}
		};

		this.mainthread = new Thread() {
			@Override
			public void run() {

				Model.setInstance(getApplicationContext());

				setVolumeControlStream(Model.getSystem().getVolume());

				Resources res = getResources();
				Configuration conf = res.getConfiguration();
				conf.locale = Model.getSystem().getLocalLanguage();
				res.updateConfiguration(conf, res.getDisplayMetrics());
				try {
					Context context = createPackageContext(getPackageName(), Context.CONTEXT_INCLUDE_CODE);

					ResourcesManager.setInstance(getApplicationContext());
					
					if ( Model.getSystem().getLastUser() == -1 ) {

						try {

							String mapsDirectory = "maps", mapName;
							File ls = getFilesDir();
							File rep = new File(ls.getAbsolutePath()+"/"+mapsDirectory), rep2, file;
							rep.mkdir();
							EditorMap mapMap;
							Bitmap bitmap;
							byte[] buffer;

							InputStream in = null;
							OutputStream out = null;

							String[] maps = getAssets().list(mapsDirectory), map;

							Log.i("Main", "-------------- Copy  Maps --------------");

							for (int i = 0 ; i < maps.length ; i ++ ) {
								mapName = maps[i].toString();

								Log.i("Main", "Map in progress : "+ mapName);

								rep2 = new File(rep.getAbsolutePath()+"/"+mapName);
								rep2.mkdir();
								map = getAssets().list(mapsDirectory+"/"+mapName);

								for (int j = 0 ; j < map.length ; j++ ) {

									try {
										file = new File(rep2.getAbsolutePath() + "/" + map[j]);
										System.out.println("PATH : " + file.getAbsolutePath());
										in = getAssets().open(mapsDirectory+"/"+mapName+"/"+map[j]);
										out = new FileOutputStream(file);

										buffer = new byte[1024];
										int read;
										while((read = in.read(buffer)) != -1){
											out.write(buffer, 0, read);
										}

										if ( map[j].indexOf(".klob") != -1) {
											/* Si on vient de copier la carte on la redimensionne à la résolution de l'écran sur lequel le jeu tournera */
											mapMap = new EditorMap();
											mapMap.loadMap(mapName);
											mapMap.resize();
											mapMap.saveMap();
										}
										else if ( map[j].indexOf(".png") != -1 ) {
											/* De même pour l'image associée */
											bitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath()+"/"+mapsDirectory+"/"+mapName+"/"+mapName+".png");
											bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((ResourcesManager.getSize()*ResourcesManager.MAP_HEIGHT)/1.5) , (int) ((ResourcesManager.getSize()*ResourcesManager.MAP_WIDTH)/1.5) , true);
											file.delete();
											file.createNewFile();
											out = new FileOutputStream(file);
											bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
										}
										
										in.close();
										in = null;
										out.flush();
										out.close();
										out = null;

										Log.i("Main", "File "+ map[j] +" copied !");

									} catch(Exception e) {
										Log.e("tag", e.getMessage());
									} 

								}
								Model.getSystem().getDatabase().newMap(mapName, "KLOB", 1);

								Log.i("Main", "--------- Map copied --------");
							}

							/* Variables mise à null par sécuritée */
							mapsDirectory = mapName = null;
							ls = null;
							rep = rep2 = file = null;
							mapMap = null;
							bitmap = null;							
							buffer = null;
							
							Log.i("Main", "------------- Maps  Copied -------------");
						} catch (IOException e) {
							e.printStackTrace();
						}

						intent = new Intent(context, NewAccountOffline.class);
					}
					else {
						intent = new Intent(context, Home.class);
					}
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}

				handler.sendMessage(handler.obtainMessage(0));
			};
		};
		this.mainthread.start();
	}

	@Override
	protected void onStop() {
		Log.i("Main", "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy(){
		Log.i("Main", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onResume(){
		Log.i("Main", "onResume");
		super.onResume();
	}

	@Override
	protected void onPause(){
		Log.i("Main", "onPause");
		super.onPause();
	} 

}