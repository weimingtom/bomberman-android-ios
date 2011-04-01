package com.klob.bomberklob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.klob.bomberklob.model.Model;

public class ChangePasswordMultiplayer extends Activity implements View.OnClickListener {
	
	private Model model;
	
	private Button cancel;
	private Button validate;
	
	private EditText userName;
	private EditText oldPass;
	private EditText newPass;
	private EditText confirmPass;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                
        setContentView(R.layout.changepasswordmultiplayer);

		this.model = Model.getInstance();
		
	    this.userName = (EditText) findViewById(R.id.ChangePasswordMultiplayerEditTextUserName);
	    this.userName.setText(this.model.getUser().getUserName());
        
		this.cancel = (Button) findViewById(R.id.ChangePasswordMultiplayerButtonCancel);
		this.cancel.setOnClickListener(this);

        this.validate = (Button) findViewById(R.id.ChangePasswordMultiplayerButtonValidate);
        this.validate.setOnClickListener(this);
    }
    
    @Override
	protected void onStop() {
		Log.i("ChangePasswordMultiplayer", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("ChangePasswordMultiplayer", "onDestroy ");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("ChangePasswordMultiplayer", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("ChangePasswordMultiplayer", "onPause ");
		super.onPause();
	}
    
    @Override
	public void onClick(View v) {
		
		Intent intent = null;
		
		if( v == this.validate ){
			// FIXME Requete au serveur
		}    	
		else if( v == this.cancel){
			intent = new Intent(ChangePasswordMultiplayer.this, ProfilManagement.class);
		}
		
		if (intent != null) {
			startActivity(intent);
			finish();
		}
    }

}
