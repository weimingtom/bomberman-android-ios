package com.klob.Bomberklob.menus;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TabHost.TabSpec;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resources.ObjectsGallery;
import com.klob.Bomberklob.resources.ResourcesManager;

public class ProfileManager extends Activity implements View.OnClickListener{

	private Button cancel;
	private Button validate;
	private Button changeAccount;
	private Button edit;
	
	private CheckBox password;
	private CheckBox connectionAuto;
	
	private EditText pseudo;
	private TextView userName;
	
	private Spinner type;
	
	private ObjectsGallery objectsGallery;
	
    /** Called when the activity is first created. **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                
        setContentView(R.layout.profilemanager);
        
        TabHost tabs = (TabHost) findViewById(R.id.ProfileManagerTabHost);
        tabs.setup();

        Resources r = getResources();
        /** 
         * premier onglet etc..
         **/
		TabSpec tspec1 = tabs.newTabSpec(String.format(r.getString(R.string.HomeButtonSinglePlayer)));
		tspec1.setIndicator(String.format(r.getString(R.string.HomeButtonSinglePlayer)));
		tspec1.setContent(R.id.ProfilLayoutSolo);
		tabs.addTab(tspec1);
		
		TabSpec tspec2 = tabs.newTabSpec((String.format(r.getString(R.string.HomeButtonMultiPlayer))));
		tspec2.setIndicator((String.format(r.getString(R.string.HomeButtonMultiPlayer))));
		tspec2.setContent(R.id.ProfilMultiLayout);
		tabs.addTab(tspec2);
		
		TabSpec tspec3 = tabs.newTabSpec((String.format(r.getString(R.string.ProfileManagerMenuGlobal))));
		tspec3.setIndicator((String.format(r.getString(R.string.ProfileManagerMenuGlobal))));
		tspec3.setContent(R.id.ProfilGlobalLayout);
		tabs.addTab(tspec3);
		
		for (int i = 0; i < tabs.getTabWidget().getChildCount() ; i++) {
			tabs.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (30*ResourcesManager.getDpiPx());
		}
		
        this.pseudo = (EditText) findViewById(R.id.ProfileManagerEditTextPseudo);
        this.pseudo.setText(Model.getUser().getPseudo());
        this.pseudo.setOnKeyListener(new OnKeyListener() {
        	
			public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	return true;
                }
                return false;
            }
        });
        
		this.objectsGallery = new ObjectsGallery(getApplicationContext(), 4, null, 45, 22, 1, false);
		for(Entry<String, Hashtable<String, AnimationSequence>> entry : ResourcesManager.getPlayersAnimations().entrySet()) {
			this.objectsGallery.addObjects(new HumanPlayer(entry.getKey(), ResourcesManager.getPlayersAnimations().get(entry.getKey()), PlayerAnimations.IDLE, true, 1, false, 1, 1, 1, 1, 1, 1, 1, 0));
		}
		this.objectsGallery.setBackgroundColor(Color.GRAY);
		this.objectsGallery.setSelectedItem(Model.getUser().getColor());
		this.objectsGallery.update();
		
        FrameLayout f = (FrameLayout) findViewById(R.id.ProfileManagerFrameLayoutObjectsGallery);
        f.addView(this.objectsGallery);
        
        this.userName = (TextView) findViewById(R.id.ProfileManagerUserName);
        this.userName.setText(Model.getUser().getUserName());
        
        this.connectionAuto = (CheckBox) findViewById(R.id.ProfileManagerCheckBoxConnection);
        connectionAuto.setOnClickListener(this);
		if(Model.getUser().getConnectionAuto()){
			connectionAuto.setChecked(true);
		}
//		this.connectionAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
//
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
//				if (isChecked && !Model.getUser().getUserName().equals("") && !Model.getUser().getPassword().equals("")) {
//					Model.getUser().setConnectionAuto(true);
//				}
//				else {
//					connectionAuto.setChecked(false);
//					Model.getUser().setConnectionAuto(false);
//					Toast.makeText(ProfileManager.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
		
		
		this.password = (CheckBox) findViewById(R.id.ProfileManagerCheckBoxPassword);
		password.setOnClickListener(this);
		if(Model.getUser().getRememberPassword()){
			password.setChecked(true);
		}
//		this.password.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
//
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
//				if (isChecked) {
//					Model.getUser().setRememberPassword(true);
//				}
//				else {
//					Model.getUser().setRememberPassword(false);
//				}
//			}
//		});
		
		this.edit = (Button) findViewById(R.id.ProfileManagerButtonEdit);
		this.edit.setOnClickListener(this);
		
		this.changeAccount = (Button) findViewById(R.id.ProfileManagerButtonChange);
		this.changeAccount.setOnClickListener(this);
		
		type = (Spinner) findViewById(R.id.spinnerType);
		int menuPosition = Model.getUser().getMenuPosition();
		type.setSelection(menuPosition);
		
		this.cancel = (Button) findViewById(R.id.ProfileManagerButtonCancel);
		this.cancel.setOnClickListener(this);

        this.validate = (Button) findViewById(R.id.ProfileManagerButtonValidate);
        this.validate.setOnClickListener(this);
    }
    
    @Override
	protected void onStop() {
		Log.i("ProfileManager", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("ProfileManager", "onDestroy ");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("ProfileManager", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("ProfileManager", "onPause ");
		super.onPause();
	}
    
	public void onClick(View v) {
		
		Intent intent = null;
		
//		String s = this.objectsGallery.getSelectedItem();
//		
//		if ( s == null ) {
//			Model.getUser().setColor("white");
//		}
//		else {
//			Model.getUser().setColor(s);
//		}
		
		if( v == this.validate ){
			
			boolean error = false;
	
			/** 
			 * pseudo rentré avec ses vérifications 
			 **/
			if ( null == Model.getSystem().getDatabase().getUser(pseudo.getText().toString()) && !pseudo.getText().toString().equals(Model.getUser().getPseudo() ) ) {
				Model.getSystem().getDatabase().changePseudo(Model.getUser().getPseudo(), pseudo.getText().toString());
				Model.getUser().setPseudo(pseudo.getText().toString());
        	}
        	else if (!pseudo.getText().toString().equals(Model.getUser().getPseudo())) {
        		Toast.makeText(ProfileManager.this, R.string.ProfileManagerErrorPseudo, Toast.LENGTH_SHORT).show();
        		error = true;
        	}
			
			int userId = Model.getSystem().getDatabase().getLastUserId();
			String pwd = Model.getUser().getPassword();
			String userName = Model.getUser().getUserName();
			
			/** 
			 * password selected et mot de passe et userName non vides
			 **/
			if (password.isChecked() && (pwd != null)
					&& (userName != null)) {
				Model.getUser().setRememberPassword(true);
				Model.getSystem().getDatabase().updateSavePwdUser(userId, 1);
			} else if (!password.isChecked()) {
				Model.getUser().setRememberPassword(false);
				Model.getSystem().getDatabase().updateSavePwdUser(userId, 0);

			} else {
				Log.i("", "tu selectionne rememberPassword alors que password et username sont nulls !");
				Toast.makeText(ProfileManager.this,R.string.ProfilManagementErrorUserMissing,Toast.LENGTH_SHORT).show();
				error=true;
			}

			if (connectionAuto.isChecked() && (pwd != null)
					&& (userName != null)) {
				try {
					if (Model.getSystem().getDatabase()
							.isGoodMultiUser(userId, userName, pwd)) {
						Model.getUser().setConnectionAuto(true);
						Model.getUser().setRememberPassword(true);
						Model.getSystem().getDatabase()
								.updateAutoConnectUser(userId, 1);
						Model.getSystem().getDatabase()
								.updateSavePwdUser(userId, 1);
					} else {
						Toast.makeText(ProfileManager.this,R.string.ProfilManagementAuthError,Toast.LENGTH_SHORT).show();
						error=true;
						
					}
				} catch (SQLException e) {
					Toast.makeText(ProfileManager.this,R.string.ProfilManagementAuthError,Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			} else if (!connectionAuto.isChecked()) {
				Model.getUser().setConnectionAuto(false);
				Model.getSystem().getDatabase()
						.updateAutoConnectUser(userId, 0);
			} else {
				Log.i("", "tu selectionne connectionAuto alors que password et username sont nulls !");
				Toast.makeText(ProfileManager.this,R.string.ProfilManagementErrorUserMissing,Toast.LENGTH_SHORT).show();
				error=true;
			}

			/** sauvegarde de la couleur **/
			Model.getUser().setColor(this.objectsGallery.getSelectedItem());
			Model.getSystem().getDatabase().updateColorUser(
						Model.getSystem().getDatabase().getLastUserId(), this.objectsGallery.getSelectedItem());
			
			/** ainsi que de la position du menu **/
			Model.getUser().setMenuPosition(type.getSelectedItemPosition());
			Model.getSystem().getDatabase().updateMenuUser(
							Model.getSystem().getDatabase().getLastUserId(),type.getSelectedItemPosition());
			
			if (!error) {
				intent = new Intent(ProfileManager.this, Options.class);
			}
		}    	
		else if(v == this.changeAccount){
			intent = new Intent(ProfileManager.this, ChangerCompteMulti.class);
		}
		else if( v == this.edit){
			intent = new Intent(ProfileManager.this, ChangePasswordMultiplayer.class);
		}
		else if( v == this.cancel){
			intent = new Intent(ProfileManager.this, Options.class);
		}
		
		Model.getSystem().getDatabase().updateUser(Model.getUser());
		
		if (intent != null) {
			startActivity(intent);
			finish();
		}
    }
}
