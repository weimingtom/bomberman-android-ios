package com.klob.bomberklob;

import java.io.IOException;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.klob.bomberklob.model.Model;

public class ProfilManagement extends Activity implements View.OnClickListener{
	
	private Model model;
	
	private Button cancel;
	private Button validate;
	private Button changeAccount;
	private Button edit;
	
	private EditText pseudo;
	private TextView userName;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                
        setContentView(R.layout.profilmanagement);
        
		try {
			this.model = Model.getInstance(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        this.pseudo = (EditText) findViewById(R.id.ProfilManagementEditTextPseudo);
        this.pseudo.setText(this.model.getUser().getPseudo());
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
        this.userName.setText(this.model.getUser().getUserName());

        this.validate = (Button) findViewById(R.id.ProfilManagementButtonValidate);
        this.validate.setOnClickListener(this);
    }
    
    @Override
	protected void onStop() {
		Log.i("", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("", "onDestroy ");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("", "onPause ");
		super.onPause();
	}
    
    @Override
	public void onClick(View v) {
		
		Intent intent = null;
		
		if( v == this.validate ){
			
			boolean error = false;
			
			if ( null == this.model.getSystem().getDatabase().getUser(pseudo.getText().toString()) && !pseudo.getText().toString().equals(model.getUser().getPseudo() ) ) {
				this.model.getSystem().getDatabase().changePseudo(model.getUser().getPseudo(), pseudo.getText().toString());
				this.model.getUser().setPseudo(pseudo.getText().toString());
        	}
        	else if (!pseudo.getText().toString().equals(model.getUser().getPseudo())) {
        		Toast.makeText(ProfilManagement.this, R.string.ProfilManagementErrorPseudo, Toast.LENGTH_SHORT).show();
        		error = true;
        	}
			
			if (!error) {
				intent = new Intent(ProfilManagement.this, Options.class);
			}
		}    	
		else if(v == this.changeAccount){
			intent = new Intent(ProfilManagement.this, ChangerCompteMulti.class);
		}
		else if( v == this.edit){
			intent = new Intent(ProfilManagement.this, ChangerPassMulti.class);
		}
		else if( v == this.cancel){
			intent = new Intent(ProfilManagement.this, Options.class);
		}
		
		if (intent != null) {
			startActivity(intent);
			finish();
		}
    }
}
