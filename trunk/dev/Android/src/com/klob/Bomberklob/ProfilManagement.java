package com.klob.Bomberklob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.klob.Bomberklob.engine.ObjectsGallery;
import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

public class ProfilManagement extends Activity implements View.OnClickListener{

	private Button cancel;
	private Button validate;
	private Button changeAccount;
	private Button edit;
	
	private CheckBox password;
	private CheckBox connectionAuto;
	
	private EditText pseudo;
	private TextView userName;
	
	private ObjectsGallery objectsGallery;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                
        setContentView(R.layout.profilmanagement);
        
        this.pseudo = (EditText) findViewById(R.id.ProfilManagementEditTextPseudo);
        this.pseudo.setText(Model.getUser().getPseudo());
        this.pseudo.setOnKeyListener(new OnKeyListener() {
        	
            @Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	return true;
                }
                return false;
            }
        });
        
        this.userName = (TextView) findViewById(R.id.ProfilManagementUserName);
        this.userName.setText(Model.getUser().getUserName());
        
        this.connectionAuto = (CheckBox) findViewById(R.id.ProfilManagementCheckBoxConnection);
		this.connectionAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked && !Model.getUser().getUserName().equals("") && !Model.getUser().getPassword().equals("")) {
					Model.getUser().setConnectionAuto(true);
				}
				else {
					connectionAuto.setChecked(false);
					Model.getUser().setConnectionAuto(false);
					Toast.makeText(ProfilManagement.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		this.objectsGallery = (ObjectsGallery) findViewById(R.id.ProfilManagementPlayersGallery);
		this.objectsGallery.setLevel(1);
		this.objectsGallery.setItemsDisplayed(4);
		this.objectsGallery.setVerticalPadding(22);
		this.objectsGallery.setVertical(false);
		
		this.objectsGallery.addObjects(new HumanPlayer("white", ResourcesManager.getPlayersAnimations().get("white"), 1, 1, 1, 1, 1, 1));
		this.objectsGallery.addObjects(new HumanPlayer("blue", ResourcesManager.getPlayersAnimations().get("blue"), 1, 1, 1, 1, 1, 1));
		this.objectsGallery.addObjects(new HumanPlayer("black", ResourcesManager.getPlayersAnimations().get("black"), 1, 1, 1, 1, 1, 1));
		this.objectsGallery.addObjects(new HumanPlayer("red", ResourcesManager.getPlayersAnimations().get("red"), 1, 1, 1, 1, 1, 1));
		
		this.password = (CheckBox) findViewById(R.id.ProfilManagementCheckBoxPassword);
		this.password.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked) {
					Model.getUser().setRememberPassword(true);
				}
				else {
					Model.getUser().setRememberPassword(false);
				}
			}
		});
		
		this.edit = (Button) findViewById(R.id.ProfilManagementButtonEdit);
		this.edit.setOnClickListener(this);
		
		this.changeAccount = (Button) findViewById(R.id.ProfilManagementButtonChange);
		this.changeAccount.setOnClickListener(this);
		
		this.cancel = (Button) findViewById(R.id.ProfilManagementButtonCancel);
		this.cancel.setOnClickListener(this);

        this.validate = (Button) findViewById(R.id.ProfilManagementButtonValidate);
        this.validate.setOnClickListener(this);
    }
    
    @Override
	protected void onStop() {
		Log.i("ProfilManagement", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("ProfilManagement", "onDestroy ");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("ProfilManagement", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("ProfilManagement", "onPause ");
		super.onPause();
	}
    
    @Override
	public void onClick(View v) {
		
		Intent intent = null;
		
		String s = this.objectsGallery.getSelectedItem();
		
		if ( s == null ) {
			Model.getUser().setColor("white");
		}
		else {
			Model.getUser().setColor(s);
		}
		
		if( v == this.validate ){
			
			boolean error = false;
			
			if ( null == Model.getSystem().getDatabase().getUser(pseudo.getText().toString()) && !pseudo.getText().toString().equals(Model.getUser().getPseudo() ) ) {
				Model.getSystem().getDatabase().changePseudo(Model.getUser().getPseudo(), pseudo.getText().toString());
				Model.getUser().setPseudo(pseudo.getText().toString());
        	}
        	else if (!pseudo.getText().toString().equals(Model.getUser().getPseudo())) {
        		Toast.makeText(ProfilManagement.this, R.string.ProfilManagementErrorPseudo, Toast.LENGTH_SHORT).show();
        		error = true;
        	}
			
			if (!error) {
				intent = new Intent(ProfilManagement.this, Options.class);
			}
		}    	
		else if(v == this.changeAccount){
			//intent = new Intent(ProfilManagement.this, ChangerCompteMulti.class);
		}
		else if( v == this.edit){
			intent = new Intent(ProfilManagement.this, ChangePasswordMultiplayer.class);
		}
		else if( v == this.cancel){
			intent = new Intent(ProfilManagement.this, Options.class);
		}
		
		Model.getSystem().getDatabase().updateUser(Model.getUser());
		
		if (intent != null) {
			startActivity(intent);
			finish();
		}
    }
}
